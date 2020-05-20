package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtils {
    public static void clearInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setArmorContents(new ItemStack[4]);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }

    public static void setInventory(Player player) {
        clearInventory(player);

        TowerPlayer towerPlayer = TowerPlayer.get(player);
        PlayerInventory inventory = player.getInventory();
        double maxHealth = 20D;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setExp(0f);
        player.setLevel(0);
        boolean canFly = GameStates.isState(GameStates.END);
        player.setAllowFlight(canFly);
        player.setFlying(canFly);

        if (GameStates.isState(GameStates.GAME)) {
            inventory.setItem(0, new ItemBuilder(Material.WOOD_SWORD).canInteract(false).unbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build());
            inventory.setHelmet(new ItemBuilder(Material.LEATHER_HELMET).canInteract(false).color(towerPlayer.getTeam().getArmorColor()).unbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build());
            inventory.setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).canInteract(false).unbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build());
            inventory.setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).canInteract(false).color(towerPlayer.getTeam().getArmorColor()).unbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build());
            inventory.setBoots(new ItemBuilder(Material.LEATHER_BOOTS).canInteract(false).color(towerPlayer.getTeam().getArmorColor()).unbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build());
        }
    }
}
