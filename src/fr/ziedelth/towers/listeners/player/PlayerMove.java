package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!GameStates.isState(GameStates.GAME)) return;
        TowerPlayer towerPlayer = TowerPlayer.get(player);
        Towers.getInstance().getTeams().stream().filter(teams -> !teams.equals(towerPlayer.getTeam()) && towerPlayer.isInScoreLocation(teams)).forEach(teams -> towerPlayer.addScore());
    }
}
