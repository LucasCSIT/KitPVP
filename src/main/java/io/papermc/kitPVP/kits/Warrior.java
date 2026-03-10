package io.papermc.kitPVP.kits;

import io.papermc.kitPVP.KitPVP;
import io.papermc.kitPVP.common.Kit;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class Warrior extends Kit implements BasicCommand {
  Material[] armor = new Material[4];
  HashMap<Material, Integer> weapons = new HashMap<>();

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
      setArmor();
      setWeapons();
      equipKit(player, armor, weapons, false);
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

  private void setArmor() {
    armor[0] = Material.IRON_HELMET;
    armor[1] = Material.IRON_CHESTPLATE;
    armor[2] = Material.IRON_LEGGINGS;
    armor[3] = Material.IRON_BOOTS;
  }

  private void setWeapons() {
    weapons.put(Material.STONE_SWORD, 1);
  }
}
