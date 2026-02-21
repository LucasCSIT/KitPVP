package io.papermc.kitPVP;

import io.papermc.kitPVP.commands.ToggleKitPVPCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitPVP extends JavaPlugin implements Listener {
  public static boolean isPluginEnabled = false;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    registerCommands();

    Bukkit.getScheduler().runTaskTimer(this, () -> {
      if (!isPluginEnabled) return;

      for (Player player : Bukkit.getOnlinePlayers()) {
        if (isFullDiamondArmor(player)) {
          setTankStats(player);
        } else {
        }
      }
    }, 0L, 20L);
  }

  private void setTankStats(Player player) {
    player.sendMessage("Diamond armor stuff set");
    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 999999, 1));
    player.setSprinting(false);
  }

  private void registerCommands() {
    registerCommand("kitpvp", new ToggleKitPVPCommand());
  }

  private boolean isFullDiamondArmor(Player player) {
    ItemStack helmet = player.getInventory().getHelmet();
    ItemStack chestplate = player.getInventory().getChestplate();
    ItemStack leggings = player.getInventory().getLeggings();
    ItemStack boots = player.getInventory().getBoots();
    int diamondArmorCount = 0;

    if (null == helmet || null == chestplate || null == leggings || null == boots) {
      player.sendMessage("Not full diamond. Returning");
      return false;
    }

    if (helmet.getType() == Material.DIAMOND_HELMET) {
      diamondArmorCount++;
    }
    if (chestplate.getType() == Material.DIAMOND_CHESTPLATE) {
      diamondArmorCount++;
    }
    if (leggings.getType() == Material.DIAMOND_LEGGINGS) {
      diamondArmorCount++;
    }
    if (boots.getType() == Material.DIAMOND_BOOTS) {
      diamondArmorCount++;
    }

    return diamondArmorCount == 4;
  }
}