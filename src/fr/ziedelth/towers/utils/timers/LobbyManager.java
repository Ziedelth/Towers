package fr.ziedelth.towers.utils.timers;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.utils.ChatUtils;
import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;
import fr.ziedelth.towers.utils.TowerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class LobbyManager extends Timer {
    public LobbyManager() {
        this.setMaxSeconds(60);
    }

    @Override
    public void start() {
        super.start();
        GameStates.setState(GameStates.LOBBY);
        TowerPlayer.getPlayers().forEach(TowerPlayer::init);
    }

    @Override
    public void run() {
        int seconds = this.getSeconds();

        if (Bukkit.getOnlinePlayers().size() < Towers.getInstance().getMinPlayers()) {
            this.stop();
            this.resetSeconds();
            Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(ChatUtils.getNotEnoughPlayers()));
            return;
        }

        TowerPlayer.getPlayers().forEach(TowerPlayer::update);

        if (seconds == 60 || seconds == 30 || seconds == 10 || (seconds <= 5 && seconds >= 1)) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                player.sendMessage(ChatUtils.getStartMessage(seconds));
                player.sendTitle("§6" + seconds, "§eLA PARTIE COMMENCE BIENTÔT !", 0, 20, 0);
            });
        }

        if (seconds <= 0) {
            this.stop();
            Towers.getInstance().getPreparationManager().start();
        }
    }
}
