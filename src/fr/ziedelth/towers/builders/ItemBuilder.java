package fr.ziedelth.towers.builders;

import fr.ziedelth.towers.Towers;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class ItemBuilder implements Listener {
    private static final Plugin PLUGIN = Towers.getInstance();

    public final Material material;
    public String name;
    private int amount = 1;
    private boolean unbreakable = false;
    private String skull;
    private String[] lore = new String[0];
    private Color color = null;
    private EnchantmentBuilder[] enchantment = new EnchantmentBuilder[0];
    private ItemFlag[] flag = new ItemFlag[0];
    private boolean canInteract = true;
    private PlayerRunnableBuilder interact = null;
    private short durability;
    private Pattern[] pattern = new Pattern[0];
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.material = material;
        Bukkit.getPluginManager().registerEvents(this, PLUGIN);
    }

    public ItemBuilder(ItemBuilder itemBuilder) {
        this.material = itemBuilder.material;
        this.amount = itemBuilder.amount;
        this.unbreakable = itemBuilder.unbreakable;
        this.name = itemBuilder.name;
        this.skull = itemBuilder.skull;
        this.lore = itemBuilder.lore;
        this.color = itemBuilder.color;
        this.enchantment = itemBuilder.enchantment;
        this.flag = itemBuilder.flag;
        this.canInteract = itemBuilder.canInteract;
        this.interact = itemBuilder.interact;
        this.durability = itemBuilder.durability;
        this.pattern = itemBuilder.pattern;
        Bukkit.getPluginManager().registerEvents(this, PLUGIN);
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder skull(String skull) {
        this.skull = skull;
        return this;
    }

    public ItemBuilder lore(String... lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLore(String lore) {
        for (int i = 0; i < this.lore.length; i++) if (this.lore[i].equalsIgnoreCase(lore)) return this;
        String[] newLore = new String[this.lore.length + 1];
        for (int i = 0; i < this.lore.length; i++) newLore[i] = this.lore[i];
        newLore[this.lore.length] = lore;
        this.lore = newLore;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public ItemBuilder enchantment(EnchantmentBuilder... enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public ItemBuilder addEnchantment(EnchantmentBuilder builder) {
        for (int i = 0; i < this.enchantment.length; i++) if (this.enchantment[i].equals(builder)) return this;
        EnchantmentBuilder[] newEnchantmentBuilder = new EnchantmentBuilder[this.enchantment.length + 1];
        for (int i = 0; i < this.enchantment.length; i++) newEnchantmentBuilder[i] = this.enchantment[i];
        newEnchantmentBuilder[this.enchantment.length] = builder;
        this.enchantment = newEnchantmentBuilder;
        return this;
    }

    public ItemBuilder flag(ItemFlag... flag) {
        this.flag = flag;
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag builder) {
        for (int i = 0; i < this.flag.length; i++) if (this.flag[i].equals(builder)) return this;
        ItemFlag[] newFlagBuilder = new ItemFlag[this.flag.length + 1];
        for (int i = 0; i < this.flag.length; i++) newFlagBuilder[i] = this.flag[i];
        newFlagBuilder[this.flag.length] = builder;
        this.flag = newFlagBuilder;
        return this;
    }

    public ItemBuilder canInteract(boolean canInteract) {
        this.canInteract = canInteract;
        return this;
    }

    public ItemBuilder interact(PlayerRunnableBuilder interact) {
        this.interact = interact;
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder pattern(Pattern... pattern) {
        this.pattern = pattern;
        return this;
    }

    public ItemBuilder addPattern(Pattern pattern) {
        for (int i = 0; i < this.pattern.length; i++) if (this.pattern[i].equals(pattern)) return this;
        Pattern[] newPattern = new Pattern[this.pattern.length + 1];
        for (int i = 0; i < this.pattern.length; i++) newPattern[i] = this.pattern[i];
        newPattern[this.pattern.length] = pattern;
        this.pattern = newPattern;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(this.material, this.amount);
        itemStack.setDurability(this.durability);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setUnbreakable(this.unbreakable);
        if (this.lore != null && this.lore.length > 0) itemMeta.setLore(Arrays.asList(this.lore));
        if (this.skull != null) ((SkullMeta) itemMeta).setOwner(this.skull);
        if (this.color != null) ((LeatherArmorMeta) itemMeta).setColor(this.color);
        if (this.enchantment != null && this.enchantment.length > 0) for (int i = 0; i < this.enchantment.length; i++)
            itemMeta.addEnchant(this.enchantment[i].getEnchantment(), this.enchantment[i].getPower(), this.enchantment[i].isB());
        if (this.flag != null && this.flag.length > 0) itemMeta.addItemFlags(this.flag);
        if (itemMeta instanceof BannerMeta) ((BannerMeta) itemMeta).setPatterns(Arrays.asList(this.pattern));
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
        return itemStack;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().equals(Material.AIR)) return;

        if (item.equals(this.itemStack)) {
            if (!this.canInteract) event.setResult(Event.Result.DENY);
            else if (this.interact != null && this.interact.getPlayer().equals(player)) this.interact.run();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType().equals(Material.AIR)) return;

        if (item.equals(this.itemStack) && this.canInteract && this.interact != null && this.interact.getPlayer().equals(player)) {
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            this.interact.run();
        }
    }
}
