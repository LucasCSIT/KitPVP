package io.papermc.kitPVP.common;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class KitPVPArmor {
  protected void giveWeaponry(Player player, Material weapon) {
    player.getInventory().addItem(new ItemStack(weapon));
  }

  protected void giveArmor(Player player, Material[] armor) {
    for (Material armorPiece : armor) {
      player.getInventory().addItem(new ItemStack(armorPiece));
    }
  }

  protected void setStats(Player player, PotionEffectType[] effects, int effectLevel, boolean cannotSprint) {
    for (PotionEffectType effect : effects) {
      player.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, effectLevel));
    }
    if (cannotSprint) {
      player.setSprinting(false);
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
}
