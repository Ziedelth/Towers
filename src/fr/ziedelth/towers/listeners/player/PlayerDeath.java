package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.utils.ChatUtils;
import fr.ziedelth.towers.utils.TaskUtils;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        TowerPlayer towerPlayer = TowerPlayer.get(player);
        towerPlayer.addDeath();
        TaskUtils.runTaskLater(() -> player.spigot().respawn());

        event.setDeathMessage(ChatUtils.getDeathMessage(player));

        if (killer != null) {
            TowerPlayer towerKiller = TowerPlayer.get(killer);
            towerKiller.addKill();
        }
    }
}
