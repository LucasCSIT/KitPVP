package io.papermc.kitPVP;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

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
    final Component toggleMessage = MiniMessage.miniMessage().deserialize(
        ""
    );

    Bukkit.broadcast(toggleMessage);
  }
}