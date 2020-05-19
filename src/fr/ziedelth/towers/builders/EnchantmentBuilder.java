package fr.ziedelth.towers.builders;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentBuilder {
    private final Enchantment enchantment;
    private final int power;
    private final boolean b;

    public EnchantmentBuilder(Enchantment enchantment, int power, boolean b) {
        this.enchantment = enchantment;
        this.power = power;
        this.b = b;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getPower() {
        return power;
    }

    public boolean isB() {
        return b;
    }
}
