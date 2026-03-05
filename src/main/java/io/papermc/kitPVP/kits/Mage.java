package io.papermc.kitPVP.kits;

import io.papermc.kitPVP.common.Kit;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Mage extends Kit implements BasicCommand {
  @Override
  public void execute(CommandSourceStack source, String @NonNull [] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /mage <equip|unequip>");
    }
  }
}
