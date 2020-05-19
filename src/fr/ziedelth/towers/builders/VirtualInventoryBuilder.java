/*
 * Copyright (c) EvolzAPI v2 2019 - Ziedelth.
 */

package fr.ziedelth.towers.builders;

import fr.ziedelth.towers.Towers;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class VirtualInventoryBuilder extends VirtualInventory {
    private static final Plugin PLUGIN = Towers.getInstance();

    private final Map<Integer, VirtualButton> childrens = new HashMap<>();
    private final String name;
    private final int size;
    private final Inventory inventory;

    public VirtualInventoryBuilder(String name, int size) {
        Bukkit.getPluginManager().registerEvents(this, PLUGIN);
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, this.size(), this.name());
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Map<Integer, VirtualButton> getChildrens() {
        return this.childrens;
    }

    public void addChildren(VirtualButton node) {
        for (int i = 0; i < this.size; i++) {
            if (!this.childrens.containsKey(i)) {
                this.getChildrens().put(i, node);
                break;
            }
        }
    }

    public void setChildren(int slot, VirtualButton node) {
        this.getChildrens().put(slot, node);
    }

    @Override
    public Inventory build() {
        for (int i : this.getChildrens().keySet()) this.inventory.setItem(i, this.getChildrens().get(i).getItemStack());
        return this.inventory;
    }
}
