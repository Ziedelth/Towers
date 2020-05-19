package fr.ziedelth.towers.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
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

        double maxHealth = 20D;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setExp(0f);
        player.setLevel(0);
        boolean canFly = GameStates.isState(GameStates.END);
        player.setAllowFlight(canFly);
        player.setFlying(canFly);

        if (GameStates.isState(GameStates.LOBBY)) {

        }
    }
}
