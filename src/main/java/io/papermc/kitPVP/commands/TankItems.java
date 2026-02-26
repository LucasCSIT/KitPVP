package io.papermc.kitPVP.commands;

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
import org.jspecify.annotations.NonNull;

public class TankItems extends KitPVPArmor implements BasicCommand, KitSetup {
  boolean isTankEquipped = false;

  @Override
  public void execute(CommandSourceStack source, String @NonNull [] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /tank <equip|unequip>");
    }

    final String message = String.join(" ", args);
    Player player = (Player) source.getSender();
    Component toggleMessage = null;
    // TODO: The code can be cleaned up a bit. Maybe find a cleaner way to store the below two lines of info?
    PotionEffectType[] effectTypes = new PotionEffectType[1];
    Material[] armorPieces = new Material[4];

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
      effectTypes[0] = PotionEffectType.SLOWNESS;
      setStats(player, effectTypes, 2, true);
      giveWeaponry(player, Material.STONE_SWORD);
      armorPieces[0] = Material.DIAMOND_HELMET;
      armorPieces[1] = Material.DIAMOND_CHESTPLATE;
      armorPieces[2] = Material.DIAMOND_LEGGINGS;
      armorPieces[3] = Material.DIAMOND_BOOTS;
      giveArmor(player, armorPieces);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <blue>Tank</blue> class.",
          Placeholder.component("name", name)
      );
      isTankEquipped = true;
    } else if (message.equalsIgnoreCase("unequip")) {
      if (!isTankEquipped) {
        player.sendMessage("The Tank class is unequipped already!");
        return;
      }
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <blue>Tank</blue> class.",
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
}
