package io.papermc.kitPVP.kits;

import io.papermc.kitPVP.KitPVP;
import io.papermc.kitPVP.common.KitPVPArmor;
import io.papermc.kitPVP.common.KitSetup;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Archer extends KitPVPArmor implements BasicCommand, KitSetup {
  boolean isArcherEquipped = false;

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /tank <equip|unequip>");
    }

    final String message = String.join(" ", args);
    Player player = (Player) source.getSender();
    Component toggleMessage = null;
    Material[] armorPieces = new Material[4];

    if (!KitPVP.isPluginEnabled) {
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] The KitPVP plugin is disabled! Enable it by typing /kitpvp enable.",
          Placeholder.component("name", name)
      );
      return;
    }
    if (message.equalsIgnoreCase("equip")) {
      if (isArcherEquipped) {
        player.sendMessage("The Archer class is already equipped!");
        return;
      }
      clearInventory(player);
      giveWeaponry(player, Material.WOODEN_SWORD);
      giveWeaponry(player, Material.BOW);
      giveWeaponry(player, Material.ARROW);
      armorPieces[0] = Material.CHAINMAIL_HELMET;
      armorPieces[1] = Material.CHAINMAIL_CHESTPLATE;
      armorPieces[2] = Material.CHAINMAIL_LEGGINGS;
      armorPieces[3] = Material.CHAINMAIL_BOOTS;
      giveArmor(player, armorPieces);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <white><bold>Archer</white> class.",
          Placeholder.component("name", name)
      );
      isArcherEquipped = true;
    } else if (message.equalsIgnoreCase("unequip")) {
      if (!isArcherEquipped) {
        player.sendMessage("The Archer class is unequipped already!");
        return;
      }
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <white><bold>Archer</white> class.",
          Placeholder.component("name", name)
      );
      isArcherEquipped = false;
    } else {
      player.sendMessage("Invalid arguments: /archer <equip|unequip>");
    }
    if (null != toggleMessage) {
      Bukkit.broadcast(toggleMessage);
    }
  }
}
