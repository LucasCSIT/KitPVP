package io.papermc.kitPVP;

import io.papermc.kitPVP.commands.ToggleKitPVPCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jspecify.annotations.NonNull;

public class KitPVP extends JavaPlugin implements Listener {
  public static boolean isPluginEnabled = false;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    registerCommands();

    Bukkit.getScheduler().runTaskTimer(this, () -> {
      if (!isPluginEnabled) return;

      getKitType();
    }, 0L, 20L);
  }

  private void getKitType() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (hasTankArmor(player)) {
        setTankStats(player);
      } else {
        removeTankStats(player);
      }

      if (hasArcherArmor(player)) {
        setArcherStats(player);
      } else {
        removeArcherStats(player);
      }
    }
  }

  private void setTankStats(Player player) {
    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 999999, 1));
    player.setSprinting(false);
  }

  private void removeTankStats(Player player) {
    player.removePotionEffect(PotionEffectType.SLOWNESS);
    player.setSprinting(true);
  }

  private boolean hasTankArmor(Player player) {
    ItemStack helmet = player.getInventory().getHelmet();
    ItemStack chestplate = player.getInventory().getChestplate();
    ItemStack leggings = player.getInventory().getLeggings();
    ItemStack boots = player.getInventory().getBoots();
    int diamondArmorCount = 0;

    if (isNotWearingProperArmor(helmet, chestplate, leggings, boots)) {
      return false;
    }

    assertArmorNotNull(helmet, chestplate, leggings, boots);

    if (doesArmorMatchMaterial(helmet, Material.DIAMOND_HELMET)) {
      diamondArmorCount++;
    }
    if (doesArmorMatchMaterial(chestplate, Material.DIAMOND_CHESTPLATE)) {
      diamondArmorCount++;
    }
    if (doesArmorMatchMaterial(leggings, Material.DIAMOND_LEGGINGS)) {
      diamondArmorCount++;
    }
    if (doesArmorMatchMaterial(boots, Material.DIAMOND_BOOTS)) {
      diamondArmorCount++;
    }

    return diamondArmorCount == 4;
  }

  private void setArcherStats(Player player) {
    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 999999, 2));
  }

  private void removeArcherStats(Player player) {
    player.removePotionEffect(PotionEffectType.JUMP_BOOST);
  }

  private boolean hasArcherArmor(@NonNull Player player) {
    ItemStack helmet = player.getInventory().getHelmet();
    ItemStack chestplate = player.getInventory().getChestplate();
    ItemStack leggings = player.getInventory().getLeggings();
    ItemStack boots = player.getInventory().getBoots();

    int archerArmorCount = 0;

    if (isNotWearingProperArmor(helmet, chestplate, leggings, boots)) {
      return false;
    }

    assertArmorNotNull(helmet, chestplate, leggings, boots);

    if (doesArmorMatchMaterial(helmet, Material.CHAINMAIL_HELMET)) {
      archerArmorCount++;
    }
    if (doesArmorMatchMaterial(chestplate, Material.CHAINMAIL_CHESTPLATE)) {
      archerArmorCount++;
    }
    if (doesArmorMatchMaterial(leggings, Material.CHAINMAIL_LEGGINGS)) {
      archerArmorCount++;
    }
    if (doesArmorMatchMaterial(boots, Material.CHAINMAIL_BOOTS)) {
      archerArmorCount++;
    }

    return archerArmorCount == 4;
  }

  /**
   * Returns true if the armor being worn by the player does not match any of the available kit's armor sets.
   * @return {@code True} if an invalid armor set is worn.a
   */
  private boolean isNotWearingProperArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings,
    ItemStack boots) {
    return (null == helmet && null == chestplate && null == leggings && null == boots);
  }

  private void assertArmorNotNull(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
    assert null != helmet;
    assert null != chestplate;
    assert null != leggings;
    assert null != boots;
  }

  private boolean doesArmorMatchMaterial(ItemStack armor, Material armorType) {
    return armor.getType() == armorType;
  }

  private void registerCommands() {
    registerCommand("kitpvp", new ToggleKitPVPCommand());
  }
}