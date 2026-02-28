package io.papermc.kitPVP.kits;

import io.papermc.kitPVP.KitPVP;
import io.papermc.kitPVP.common.KitPVPArmor;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Warrior extends KitPVPArmor implements BasicCommand {
  @Override
  public void execute(CommandSourceStack source, String @NonNull [] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /warrior <equip|unequip>");
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
      announce(toggleMessage);
      return;
    }
    if (message.equalsIgnoreCase("equip")) {
      clearInventory(player);
      removeStats(player);
      setStats(player, effectTypes, 2, true);
      giveWeaponry(player, Material.STONE_SWORD);
      armorPieces[0] = Material.IRON_HELMET;
      armorPieces[1] = Material.IRON_CHESTPLATE;
      armorPieces[2] = Material.IRON_LEGGINGS;
      armorPieces[3] = Material.IRON_BOOTS;
      giveArmor(player, armorPieces);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <grey><bold>Warrior</grey> class.",
          Placeholder.component("name", name)
      );
    } else if (message.equalsIgnoreCase("unequip")) {
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <gray><bold>Warrior</gray> class.",
          Placeholder.component("name", name)
      );
    } else {
      player.sendMessage("Invalid arguments: /warrior <equip|unequip>");
    }
    announce(toggleMessage);
  }
}
