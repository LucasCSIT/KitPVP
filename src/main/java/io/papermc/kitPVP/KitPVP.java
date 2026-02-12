package io.papermc.kitPVP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class KitPVP extends JavaPlugin implements Listener {
  private boolean pluginEnabled = false;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEVents(this, this);
  }

  @Override
  public void onCommand(CommandSender player, Command command, String label, String[] args) {
    if (command.getName.equalsIgnoreCase("kitpvp")) {
      if (args.length == 0) {
        player.sendMessage("Missing arguments: /kitpvp <enable|disable>")
        return true;
      }
      if (args[0].equalsIgnoreCase("enable")) {
        pluginEnabled = true;
        player.sendMessage("KitPVP has been enabled!")
      } else if (args[0].equalsIgnoreCase("disable")) {
        pluginEnabled = false;
        player.sendMessage("KitPVP has been disabled!")
      } else {
        player.sendMessage("Arguments mismatch: /kitpvp <enable|disable>")
        return true;
      }
    }
    return false;
  }
}