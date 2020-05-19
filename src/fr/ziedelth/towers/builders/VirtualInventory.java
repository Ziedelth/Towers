/*
 * Copyright (c) EvolzAPI v2 2019 - Ziedelth.
 */

package fr.ziedelth.towers.builders;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class VirtualInventory implements Listener {
    public abstract String name();

    public abstract int size();

    public abstract Map<Integer, VirtualButton> getChildrens();

    public abstract Inventory build();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null) return;
        ItemStack current = event.getCurrentItem();
        if (current == null || current.getType().equals(Material.AIR)) return;

        if (inventory.equals(this.build())) {
            event.setCancelled(true);

            for (VirtualButton nodes : this.getChildrens().values()) {
                if (current.equals(nodes.getItemStack())) {
                    if (nodes.getAction() != null) nodes.getAction().run();
                    break;
                }
            }
        }
    }
}
