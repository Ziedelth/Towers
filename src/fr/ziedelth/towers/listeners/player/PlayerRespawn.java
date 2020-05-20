package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.utils.TaskUtils;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        TowerPlayer towerPlayer = TowerPlayer.get(player);
        event.setRespawnLocation(towerPlayer.getTeam().getSpawnLocation());
        TaskUtils.runTaskLater(towerPlayer::init);
    }
}
