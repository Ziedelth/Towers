package fr.ziedelth.towers.builders;

import org.bukkit.entity.Player;

public abstract class PlayerRunnableBuilder {
    private Player player;

    public PlayerRunnableBuilder(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void run();
}
