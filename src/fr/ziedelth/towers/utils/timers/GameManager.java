package fr.ziedelth.towers.utils.timers;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.utils.ChatUtils;
import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class GameManager extends Timer {
    public GameManager() {
        this.setMaxSeconds(1200);
    }

    @Override
    public void start() {
        super.start();
        GameStates.setState(GameStates.GAME);
        TowerPlayer.getPlayers().forEach(TowerPlayer::init);
    }

    public void finish() {
        this.stop();
        Towers.getInstance().getEndManager().start();

        // Egalite
        if (Towers.getInstance().allTeamsHaveTheSameScore()) Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(ChatUtils.getDrawMessage()));
        else {
            TowerPlayer.getPlayers().forEach(towerPlayer -> {
                // Gagnant
                if (towerPlayer.getTeam().equals(Towers.getInstance().getTeamWithTheHighestScore())) towerPlayer.getPlayer().sendMessage(ChatUtils.getWinMessage());
                    // Perdant
                else towerPlayer.getPlayer().sendMessage(ChatUtils.getLoseMessage());
            });
        }
    }

    @Override
    public void run() {
        int seconds = this.getSeconds();

        TowerPlayer.getPlayers().forEach(TowerPlayer::update);

        if (seconds == 60 || seconds == 30 || seconds == 10 || (seconds <= 5 && seconds >= 1)) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                player.sendMessage(ChatUtils.getEndMessage(seconds));
                player.sendTitle("§6" + seconds, "§eLA PARTIE SE TERMINE BIENTÔT !", 0, 20, 0);
            });
        }

        if (seconds <= 0) {
            this.finish();
        }
    }
}
