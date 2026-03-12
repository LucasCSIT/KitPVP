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
  /**
   * Equips the kit that was selected by the player by giving the player all required armor, weapons, status effects, and potions.
   * @param player
   * @param armor
   * @param weapons
   * @param potionEffects
   * @param canSprint
   */
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

  /**
   * Gives the player the required weaponry.
   * @param player
   * @param weapon
   * @param quantity
   */
  private void giveWeaponry(Player player, Material weapon, Integer quantity) {
    player.getInventory().addItem(new ItemStack(weapon, quantity));
  }

  /**
   * Gives the player the required armor.
   * @param player
   * @param armor
   */
  private void giveArmor(Player player, Material armor) {
    player.getInventory().addItem(new ItemStack(armor));
  }

  /**
   * Gives the player the required stats.
   * @param player
   * @param potionEffects
   */
  private void setStats(Player player, HashMap<PotionEffectType, Integer> potionEffects) {
    for (Map.Entry<PotionEffectType, Integer> potionEffect : potionEffects.entrySet()) {
      player.addPotionEffect(new PotionEffect(potionEffect.getKey(), Integer.MAX_VALUE, potionEffect.getValue()));
    }
  }

  /**
   * Gives the player the required status effects.
   * @param player
   */
  protected void removeStats(Player player) {
    for (PotionEffect effect : player.getActivePotionEffects()) {
      player.removePotionEffect(effect.getType());
    }
    player.setSprinting(true);
  }

  /**
   * Enables or disables sprinting for the player depending on the selected kit.
   * @param player
   * @param canSprint
   */
  private void setSprinting(Player player, boolean canSprint) {
    player.setSprinting(canSprint);
  }

  /**
   * Clears the inventory of the player.
   * @param player
   */
  protected void clearInventory(Player player) {
    player.getInventory().clear();
  }

  /**
   * Checks if the arguments of a command is empty.
   * @param args
   * @return {@code True} if the arguments of a command is empty.
   */
  protected boolean isCommandArgsEmpty(String[] args) {
    return args.length == 0;
  }

  /**
   * Sends an announcement upon specific command execution.
   * @param message
   */
  protected void announce(Component message) {
    Bukkit.broadcast(message);
  }
}
