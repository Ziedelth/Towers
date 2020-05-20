package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntity implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        Entity damager = event.getDamager();

        if (damager instanceof Player) {
            Player pdamager = (Player) damager;

            if (this.isInSameTeam(player, pdamager)) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        } else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            ProjectileSource source = projectile.getShooter();
            if (!(source instanceof Player)) return;
            Player shooter = (Player) source;

            if (this.isInSameTeam(player, shooter)) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    private boolean isInSameTeam(Player p0, Player p1) {
        return TowerPlayer.get(p0).getTeam().equals(TowerPlayer.get(p1).getTeam());
    }
}
