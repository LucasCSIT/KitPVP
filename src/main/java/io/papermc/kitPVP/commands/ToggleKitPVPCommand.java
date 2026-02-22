package io.papermc.kitPVP.commands;

import io.papermc.kitPVP.KitPVP;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.List;

public class ToggleKitPVPCommand implements BasicCommand {
  @Override
  public void execute(CommandSourceStack source, String[] args) {
    final Component name = source.getExecutor() != null
        ? source.getExecutor().name()
        : source.getSender().name();

    if (args.length == 0) {
      source.getSender().sendRichMessage("Missing arguments. /kitpvp <enable|disable>");
      return;
    }

    final String message = String.join(" ", args);
    Component toggleMessage = null;

    if (message.equalsIgnoreCase("enable")) {
      if (KitPVP.isPluginEnabled) {
        source.getSender().sendMessage("ERROR: KitPVP is already enabled!");
        return;
      }
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <name> <dark_gray></dark_gray> has enabled KitPVP.",
          Placeholder.component("name", name),
          Placeholder.unparsed("message", message)
      );
      KitPVP.isPluginEnabled = true;
    } else if (message.equalsIgnoreCase("disable")) {
      if (!KitPVP.isPluginEnabled) {
        source.getSender().sendMessage("ERROR: KitPVP is already disabled!");
        return;
      }
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <name> <dark_gray></dark_gray> has disabled KitPVP",
          Placeholder.component("name", name),
          Placeholder.unparsed("message", message)
      );
      KitPVP.isPluginEnabled = false;
    } else {
      if (source.getSender() instanceof Player player) {
        player.sendMessage("Invalid arguments: /kitpvp <enable|disable>");
      }
    }

    if (null != toggleMessage) {
      Bukkit.broadcast(toggleMessage);
    }
  }

  @Override
  public @NonNull Collection<String> suggest(@NonNull CommandSourceStack stack, String[] args) {
    if (args.length <= 1) {
      return List.of("enable", "disable");
    }

    return List.of();
  }
}