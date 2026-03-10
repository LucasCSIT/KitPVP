package io.papermc.kitPVP.common;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public abstract class Kit {
  protected void equipKit(Player player, Material[] armor, HashMap<Material, Integer> weapons, HashMap<PotionEffectType, Integer> potionEffects, boolean canSprint) {
    clearInventory(player);
    removeStats(player);
    for (Material m : armor) {
      giveArmor(player, m);
    }
    for (Map.Entry<Material, Integer> weapon : weapons.entrySet()) {
      giveWeaponry(player, weapon.getKey(), weapon.getValue());
    }
    if (!potionEffects.isEmpty()) {
      setStats(player, potionEffects);
    }
    setSprinting(player, canSprint);
  }

  private void giveWeaponry(Player player, Material weapon, Integer quantity) {
    player.getInventory().addItem(new ItemStack(weapon, quantity));
  }

  private void giveArmor(Player player, Material armor) {
    player.getInventory().addItem(new ItemStack(armor));
  }

  private void setStats(Player player, HashMap<PotionEffectType, Integer> potionEffects) {
    for (Map.Entry<PotionEffectType, Integer> potionEffect : potionEffects.entrySet()) {
      player.addPotionEffect(new PotionEffect(potionEffect.getKey(), Integer.MAX_VALUE, potionEffect.getValue()));
    }
  }

  protected void removeStats(Player player) {
    for (PotionEffect effect : player.getActivePotionEffects()) {
      player.removePotionEffect(effect.getType());
    }
    player.setSprinting(true);
  }

  private void setSprinting(Player player, boolean canSprint) {
    player.setSprinting(canSprint);
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
