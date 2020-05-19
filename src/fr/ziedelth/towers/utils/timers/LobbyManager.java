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
    }

    @Override
    public void run() {
        int seconds = this.getSeconds();

        if (seconds == 60 || seconds == 30 || seconds == 10 || (seconds <= 5 && seconds >= 1)) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                player.sendMessage(ChatUtils.getStartMessage(seconds));
                TowerPlayer.get(player).update();
            });
        }

        if (seconds <= 0) {
            this.stop();
            Towers.getInstance().getPreparationManager().start();
        }
    }
}
