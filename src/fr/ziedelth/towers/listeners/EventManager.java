package fr.ziedelth.towers.listeners;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.listeners.entity.EntityDamage;
import fr.ziedelth.towers.listeners.player.PlayerJoin;
import fr.ziedelth.towers.listeners.player.PlayerMove;
import fr.ziedelth.towers.listeners.player.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public EventManager() {
        Towers towers = Towers.getInstance();
        PluginManager pm = Bukkit.getPluginManager();
        // Players
        pm.registerEvents(new PlayerJoin(), towers);
        pm.registerEvents(new PlayerQuit(), towers);
        pm.registerEvents(new PlayerMove(), towers);
        // Entities
        pm.registerEvents(new EntityDamage(), towers);
    }
}
