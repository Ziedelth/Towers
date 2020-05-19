package fr.ziedelth.towers.listeners.entity;

import fr.ziedelth.towers.utils.GameStates;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;

        // Si la partie n'a pas commencé
        if (!GameStates.isState(GameStates.GAME)) {
            event.setDamage(0);
            event.setCancelled(true);
            return;
        }
    }
}
