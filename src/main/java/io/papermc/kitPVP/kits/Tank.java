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

public class Tank extends Kit implements BasicCommand {
  Material[] armor = new Material[4];
  Material[] weapons = new Material[1];
  PotionEffectType[] potionEffects = new PotionEffectType[1];

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
      setPotionEffects();
      equipKit(player, armor, weapons, potionEffects, false);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <blue><bold>Tank</blue> class.",
          Placeholder.component("name", name)
      );
    } else if (message.equalsIgnoreCase("unequip")) {
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <blue><bold>Tank</blue> class.",
          Placeholder.component("name", name)
      );
    } else {
      player.sendMessage("Invalid arguments: /tank <equip|unequip>");
    }
    announce(toggleMessage);
  }

  private void setArmor() {
    armor[0] = Material.DIAMOND_HELMET;
    armor[1] = Material.DIAMOND_CHESTPLATE;
    armor[2] = Material.DIAMOND_LEGGINGS;
    armor[3] = Material.DIAMOND_BOOTS;
  }

  private void setWeapons() {
    weapons[0] = Material.STONE_SWORD;
  }

  private void setPotionEffects() {
    potionEffects[0] = PotionEffectType.SLOWNESS;
  }
}
