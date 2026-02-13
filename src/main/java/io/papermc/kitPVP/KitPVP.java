package io.papermc.kitPVP;

import net.kyori.adventure.text.Component;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class KitPVP extends JavaPlugin implements Listener {
  private boolean pluginEnabled = false;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    registerCommand("kitpvp", new ToggleKitPVPCommand(pluginEnabled));
  }

  @Override
  public boolean onCommand(CommandSender player, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("kitpvp")) {
      togglePlugin(player, command, label, args);
      return true;
    }
    return false;
  }

  private void togglePlugin(CommandSender player, Command command, String label, String[] args) {
    if (args.length == 0) {
      player.sendMessage("Missing arguments: /kitpvp <enable|disable>");
    }
    if (args[0].equalsIgnoreCase("enable")) {
      player.sendMessage("KitPVP has been enabled!");
    } else if (args[0].equalsIgnoreCase("disable")) {
      pluginEnabled = false;
      player.sendMessage("KitPVP has been disabled!");
    } else {
      player.sendMessage("Arguments mismatch: /kitpvp <enable|disable>");
    }
  }
}