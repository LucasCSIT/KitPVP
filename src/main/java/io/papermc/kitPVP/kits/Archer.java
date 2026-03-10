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
import org.jspecify.annotations.NonNull;

import java.util.HashMap;

public class Archer extends Kit implements BasicCommand {
  HashMap<Material, Integer> weapons = new HashMap<>();
  Material[] armor = new Material[4];

  @Override
  public void execute(CommandSourceStack source, String @NonNull [] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /archer <equip|unequip>");
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
      setArmor();
      setWeapons();
      equipKit(player, armor, weapons,true);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <white><bold>Archer</white> class.",
          Placeholder.component("name", name)
      );
    } else if (message.equalsIgnoreCase("unequip")) {
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <white><bold>Archer</white> class.",
          Placeholder.component("name", name)
      );
    } else {
      player.sendMessage("Invalid arguments: /archer <equip|unequip>");
    }
    announce(toggleMessage);
  }

  private void setArmor() {
    armor[0] = Material.CHAINMAIL_HELMET;
    armor[1] = Material.CHAINMAIL_CHESTPLATE;
    armor[2] = Material.CHAINMAIL_LEGGINGS;
    armor[3] = Material.CHAINMAIL_BOOTS;
  }

  private void setWeapons() {
    weapons.put(Material.BOW, 1);
    weapons.put(Material.WOODEN_SWORD, 1);
    weapons.put(Material.ARROW, 64);
  }
}
