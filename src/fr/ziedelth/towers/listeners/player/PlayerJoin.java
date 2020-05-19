package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.utils.ChatUtils;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TowerPlayer towerPlayer = TowerPlayer.get(player);
        towerPlayer.init();
        event.setJoinMessage(ChatUtils.getPlayerJoinMessage(player));
    }
}
