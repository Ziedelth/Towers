package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.builders.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TowerPlayer {
    private static final List<TowerPlayer> players = new ArrayList<>();
    private final Player player;
    private ScoreboardBuilder scoreboardBuilder;
    private int kills;
    private int deaths;
    public TowerPlayer(Player player) {
        this.player = player;
    }

    public static List<TowerPlayer> getPlayers() {
        return players;
    }

    public static TowerPlayer get(Player player) {
        TowerPlayer towerPlayer = null;

        for (TowerPlayer players : getPlayers()) {
            if (players.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                towerPlayer = players;
                break;
            }
        }

        if (towerPlayer == null) {
            towerPlayer = new TowerPlayer(player);
            getPlayers().add(towerPlayer);
        }

        return towerPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void init() {
        if (this.scoreboardBuilder == null) {
            this.scoreboardBuilder = new ScoreboardBuilder("§6Towers");
            this.scoreboardBuilder.addObjective(DisplaySlot.SIDEBAR);
            this.scoreboardBuilder.addHealthObjective(DisplaySlot.PLAYER_LIST);
        }

        InventoryUtils.setInventory(this.player);

        if (GameStates.isState(GameStates.LOBBY) && Bukkit.getOnlinePlayers().size() >= 2)
            Towers.getInstance().getLobbyManager().start();
    }

    public void update() {
        this.scoreboardBuilder.updateHealth();
        this.scoreboardBuilder.addDisplayScore("§fPhase: §e" + GameStates.getState().name());
        this.scoreboardBuilder.addDisplayScore("§fTemps restant: §e" + new SimpleDateFormat("mm:ss").format(Towers.getInstance().getCurrentManager().getSeconds()));
        this.scoreboardBuilder.addDisplayScore("§fJoueur(s): §e" + Bukkit.getOnlinePlayers().size());
        this.scoreboardBuilder.addDisplayScore("§0");
        this.scoreboardBuilder.addDisplayScore("§fEquipe: §7???");
        this.scoreboardBuilder.addDisplayScore("§fElimination(s): §e" + this.getKills());
        this.scoreboardBuilder.addDisplayScore("§fMort(s): §e" + this.getDeaths());
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        this.deaths++;
    }
}
