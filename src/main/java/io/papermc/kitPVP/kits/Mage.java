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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class Mage extends Kit implements BasicCommand {
  Material[] armor = new Material[4];
  HashMap<Material, Integer> weapons = new HashMap<>();
  HashMap<PotionEffectType, Integer> potionEffects = new HashMap<>();

  @Override
  public void execute(CommandSourceStack source, String @NonNull [] args) {
    final Component name = null != source.getExecutor()
        ? source.getExecutor().name()
        : source.getSender().name();

    if (isCommandArgsEmpty(args)) {
      source.getSender().sendRichMessage("Missing arguments. /mage <equip|unequip>");
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
      setArmor();
      setWeapons();
      equipKit(player, armor, weapons, potionEffects, true);
      giveDrinkablePotions(player);
      giveSplashPotions(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has equipped the <yellow><bold>Mage</yellow> class.",
          Placeholder.component("name", name)
      );
    } else if (message.equalsIgnoreCase("unequip")) {
      removeStats(player);
      clearInventory(player);
      toggleMessage = MiniMessage.miniMessage().deserialize(
          "[<red><bold>ALERT</red>] <dark_gray><name></dark_gray> has unequipped the <yellow><bold>Mage</yellow> class.",
          Placeholder.component("name", name)
      );
    } else {
      player.sendMessage("Invalid arguments: /mage <equip|unequip>");
    }
    announce(toggleMessage);
  }

  private void setArmor() {
    armor[0] = Material.GOLDEN_HELMET;
    armor[1] = Material.GOLDEN_CHESTPLATE;
    armor[2] = Material.GOLDEN_LEGGINGS;
    armor[3] = Material.GOLDEN_BOOTS;
  }

  private void setWeapons() {
    weapons.put(Material.GOLDEN_SWORD, 1);
  }

  // TODO: Put this in Kit.java so other kits can utilize this
  private void giveDrinkablePotions(Player player) {
    ItemStack potion = new ItemStack(Material.POTION);
    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

    potionMeta.setBasePotionType(PotionType.HEALING);
    potion.setItemMeta(potionMeta);
    player.getInventory().addItem(potion);

    potionMeta.setBasePotionType(PotionType.STRENGTH);
    potion.setItemMeta(potionMeta);
    player.getInventory().addItem(potion);
  }

  // TODO: Put this in Kit.java so other kits can utilize this
  private void giveSplashPotions(Player player) {
    ItemStack potion = new ItemStack(Material.SPLASH_POTION);
    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

    for (int i = 0; i < 2; i++) {
      potionMeta.setBasePotionType(PotionType.HARMING);
      potion.setItemMeta(potionMeta);
      player.getInventory().addItem(potion);
    }

    for (int i = 0; i < 2; i++) {
      potionMeta.setBasePotionType(PotionType.WEAKNESS);
      potion.setItemMeta(potionMeta);
      player.getInventory().addItem(potion);
    }

    potionMeta.setBasePotionType(PotionType.POISON);
    potion.setItemMeta(potionMeta);
    player.getInventory().addItem(potion);

    potionMeta.setBasePotionType(PotionType.SLOWNESS);
    potion.setItemMeta(potionMeta);
    player.getInventory().addItem(potion);
  }
}
