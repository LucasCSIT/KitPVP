package io.papermc.kitPVP.commands;

import io.papermc.kitPVP.KitPVP;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TankItems implements BasicCommand {
  boolean isTankEquipped = false;

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (args.length == 0) {
      source.getSender().sendRichMessage("Missing arguments. /tank <equip|unequip>");
    }

    final String message = String.join(" ", args);
    Player player = (Player) source.getSender();
    Component toggleMessage = null;

    if (!KitPVP.isPluginEnabled) {
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] The KitPVP plugin is disabled! Enable it by typing /kitpvp enable.",
          Placeholder.component("name", name)
      );
    }
    if (message.equalsIgnoreCase("equip")) {
      if (isTankEquipped) {
        player.sendMessage("The Tank class is already equipped!");
        return;
      }
      clearInventory(player);
      setTankStats(player);
      giveTankWeaponry(player);
      giveTankArmor(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <name> <dark_gray></dark_gray> has equipped the <blue>Tank</blue> class.",
          Placeholder.component("name", name)
      );
      isTankEquipped = true;
    } else if (message.equalsIgnoreCase("unequip")) {
      if (!isTankEquipped) {
        player.sendMessage("The Tank class is unequipped already!");
        return;
      }
      removeTankStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <name> <dark_gray></dark_gray> has unequipped the <blue>Tank</blue> class.",
          Placeholder.component("name", name)
      );
      isTankEquipped = false;
    } else {
      player.sendMessage("Invalid arguments: /tank <equip|unequip>");
    }

    if (null != toggleMessage) {
      Bukkit.broadcast(toggleMessage);
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

  private void giveTankArmor(Player player) {
    player.getInventory().addItem(new ItemStack(Material.DIAMOND_HELMET));
    player.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
    player.getInventory().addItem(new ItemStack(Material.DIAMOND_LEGGINGS));
    player.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS));
  }

  private void giveTankWeaponry(Player player) {
    player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
  }

  private void clearInventory(Player player) {
    player.getInventory().clear();
  }
}
