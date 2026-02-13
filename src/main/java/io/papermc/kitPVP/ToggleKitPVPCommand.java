package io.papermc.kitPVP;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ToggleKitPVPCommand implements BasicCommand {
  private boolean pluginEnabled;

  public ToggleKitPVPCommand(boolean pluginEnabled) {
    this.pluginEnabled = pluginEnabled;
  }

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
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "<red><bold>BROADCAST</red> <name> <dark_gray></dark_gray> has enabled KitPVP.",
          Placeholder.component("name", name),
          Placeholder.unparsed("message", message)
      );
    } else if (message.equalsIgnoreCase("disable")) {
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "<red><bold>BROADCAST</red> <name> <dark_gray></dark_gray> has disabled KitPVP",
          Placeholder.component("name", name),
          Placeholder.unparsed("message", message)
      );
    } else {
      if (source.getSender() instanceof Player player) {
        player.sendMessage("Invalid arguments. /kitpvp <enable|disable>");
      }
    }

    if (null != toggleMessage) {
      Bukkit.broadcast(toggleMessage);
    }
  }
}