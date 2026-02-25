package io.papermc.kitPVP.common;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

abstract class KitPVPArmor {
  private void giveWeaponry(Player player, Material weapon) {
    player.getInventory().addItem(new ItemStack(weapon));
  }

  private void giveArmor(Player player, Material[] armor) {
    for (Material armorPiece : armor) {
      player.getInventory().addItem(new ItemStack(armorPiece));
    }
  }

  private void setStats(Player player, PotionEffectType[] effects, int effectLevel, boolean cannotSprint) {
    for (PotionEffectType effect : effects) {
      player.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, effectLevel));
    }
    if (cannotSprint) {
      player.setSprinting(false);
    }
  }

  private void removeStats(Player player, PotionEffectType[] effects) {
    for (PotionEffectType effect : effects) {
      player.removePotionEffect(effect);
    };
    player.setSprinting(true);
  }

  private void clearInventory(Player player) {
    player.getInventory().clear();
  }

  private boolean isCommandArgsEmpty(String[] args) {
    return args.length == 0;
  }
}
