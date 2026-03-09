package io.papermc.kitPVP.common;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Kit {
  protected void equipKit(Player player, Material[] armor, Material[] weapons, boolean canSprint) {
    clearInventory(player);
    removeStats(player);
    for (Material m : armor) {
      giveArmor(player, m);
    }
    for (Material m : weapons) {
      if (m == Material.ARROW) {
        // TODO: This is hardcoded right now. Figure out how to make this not hard-coded. Likely need a map for the material and the desired quantity.
        giveWeaponry(player, m, 64);
        continue;
      }
      giveWeaponry(player, m);
    }
    if (!canSprint) {
      player.setSprinting(false);
    }
  }

  protected void equipKit(Player player, Material[] armor, Material[] weapons, PotionEffectType[] potionEffects, boolean canSprint) {
    clearInventory(player);
    removeStats(player);
    for (Material m : armor) {
      giveArmor(player, m);
    }
    for (Material m : weapons) {
      if (m == Material.ARROW) {
        // TODO: This is hardcoded right now. Figure out how to make this not hard-coded. Likely need a map for the material and the desired quantity.
        giveWeaponry(player, m, 64);
        continue;
      }
      giveWeaponry(player, m);
    }
    if (potionEffects.length > 0) {
      // TODO: This is hardcoded right now. Figure out how to make this not hard-coded. Likely need a map for the potion effect and the desired level.
      setStats(player, potionEffects, 1);
    }
    if (!canSprint) {
      player.setSprinting(false);
    }
  }

  private void giveWeaponry(Player player, Material weapon) {
    player.getInventory().addItem(new ItemStack(weapon));
  }

  private void giveWeaponry(Player player, Material weapon, int quantity) {
    player.getInventory().addItem(new ItemStack(weapon, quantity));
  }

  private void giveArmor(Player player, Material armor) {
    player.getInventory().addItem(new ItemStack(armor));
  }

  private void setStats(Player player, PotionEffectType[] effects, int effectLevel) {
    for (PotionEffectType effect : effects) {
      player.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, effectLevel));
    }
  }

  protected void removeStats(Player player) {
    for (PotionEffect effect : player.getActivePotionEffects()) {
      player.removePotionEffect(effect.getType());
    }
    player.setSprinting(true);
  }

  protected void clearInventory(Player player) {
    player.getInventory().clear();
  }

  protected boolean isCommandArgsEmpty(String[] args) {
    return args.length == 0;
  }

  protected void announce(Component message) {
    Bukkit.broadcast(message);
  }
}
