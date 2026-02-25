package io.papermc.kitPVP.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;

public class Archer implements BasicCommand {
  boolean isArcherEquipped = false;

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();
  }
}
