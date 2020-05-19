/*
 * Copyright (c) EvolzAPI v2 2019 - Ziedelth.
 */

package fr.ziedelth.towers.builders;

import org.bukkit.inventory.ItemStack;

public class VirtualButton {
    private final ItemStack itemStack;
    private Runnable runnable;

    public VirtualButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Runnable getAction() {
        return runnable;
    }

    public VirtualButton setOnActionListener(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }
}
