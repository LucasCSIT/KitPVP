package io.papermc.kitPVP;

import io.papermc.kitPVP.kits.*;
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

  /**
   * Registers commands for each kit implemented.
   */
  private void registerCommands() {
    registerCommand("kitpvp", new ToggleKitPVPCommand());
    registerCommand("tank", new Tank());
    registerCommand("archer", new Archer());
    registerCommand("warrior", new Warrior());
    registerCommand("mage", new Mage());
    registerCommand("pyro", new Pyro());
  }
}