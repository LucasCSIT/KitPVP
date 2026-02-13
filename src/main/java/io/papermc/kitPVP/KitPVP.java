package io.papermc.kitPVP;

import io.papermc.kitPVP.commands.ToggleKitPVPCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPVP extends JavaPlugin implements Listener {
  public static boolean isPluginEnabled = false;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    registerCommands();
  }

  private void registerCommands() {
    registerCommand("kitpvp", new ToggleKitPVPCommand());
  }
}