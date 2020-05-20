package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.Towers;
import fr.ziedelth.towers.builders.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TowerPlayer {
    private static final List<TowerPlayer> players = new ArrayList<>();
    private final Player player;
    private ScoreboardBuilder scoreboardBuilder;
    private int kills;
    private int deaths;
    private Teams team;
    private int score;

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

    public static List<TowerPlayer> getPlayersInTeam(Teams team) {
        return getPlayers().stream().filter(towerPlayer -> towerPlayer.getTeam().equals(team)).collect(Collectors.toList());
    }

    public static int getScoreOf(Teams team) {
        return getPlayersInTeam(team).stream().mapToInt(TowerPlayer::getScore).sum();
    }

    public Player getPlayer() {
        return player;
    }

    public void init() {
        if (this.scoreboardBuilder == null) {
            this.scoreboardBuilder = new ScoreboardBuilder("§6Towers");
            this.scoreboardBuilder.addObjective(DisplaySlot.SIDEBAR);
            this.scoreboardBuilder.addHealthObjective(DisplaySlot.PLAYER_LIST);
            Towers.getInstance().getTeams().forEach(team -> this.scoreboardBuilder.addTeam(team.getTeamBuilder()));
            getPlayers().forEach(players -> this.scoreboardBuilder.addTo(players.team.getName(), players.getPlayer()));
        }

        if (GameStates.isState(GameStates.LOBBY)) {
            this.player.teleport(Towers.getInstance().getLobbyLocation());
            if (Bukkit.getOnlinePlayers().size() >= 2) Towers.getInstance().getLobbyManager().start();
        } else if (GameStates.isState(GameStates.PREPARATION)) {
            this.joinTeam();
            this.player.teleport(Towers.getInstance().getLobbyLocation());
        } else if (GameStates.isState(GameStates.GAME)) {
            this.joinTeam();
            this.player.teleport(this.team.getSpawnLocation());
            // TODO: Inventory
        } else if (GameStates.isState(GameStates.END)) {
            this.player.teleport(Towers.getInstance().getLobbyLocation());
        }

        InventoryUtils.setInventory(this.player);
        this.update();
    }

    public void update() {
        this.scoreboardBuilder.updateHealth();
        this.scoreboardBuilder.addDisplayScore("§fPhase: §e" + GameStates.getState().name());
        this.scoreboardBuilder.addDisplayScore("§fTemps restant: §e" + new SimpleDateFormat("mm:ss").format(Towers.getInstance().getCurrentManager().getSeconds()));
        this.scoreboardBuilder.addDisplayScore("§fJoueur(s): §e" + Bukkit.getOnlinePlayers().size());
        this.scoreboardBuilder.addDisplayScore("§0");
        this.scoreboardBuilder.addDisplayScore("§fEquipe: " + (this.team != null ? this.team.getName() : "§7???"));
        this.scoreboardBuilder.addDisplayScore("§fElimination(s): §e" + this.getKills());
        this.scoreboardBuilder.addDisplayScore("§fMort(s): §e" + this.getDeaths());
        this.scoreboardBuilder.addDisplayScore("§fPoint(s) marqué(s): §e" + this.getScore());
        this.scoreboardBuilder.addDisplayScore("§1");
        Towers.getInstance().getTeams().forEach(team -> this.scoreboardBuilder.addDisplayScore(team.getName() + "§f: §e" + getScoreOf(team)));
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

    public Teams getTeam() {
        return team;
    }

    public void joinTeam() {
        if (Towers.getInstance().allHaveTheSameNumberPlayers()) {
            int random_int = new Random().nextInt(Towers.getInstance().getTeams().size());
            Teams random_team = Towers.getInstance().getTeams().get(random_int);
            this.setTeam(random_team);
        } else this.setTeam(Towers.getInstance().getSmallerTeam());
    }

    public void setTeam(Teams team) {
        this.team = team;
        this.score = 0;
        this.player.sendMessage(ChatUtils.getTeamJoinMessage(this.team));
        getPlayers().forEach(players -> players.scoreboardBuilder.addTo(this.team.getName(), this.player));
    }

    public boolean isInScoreLocation(Teams team) {
        Location location = this.player.getLocation();
        double minX = Math.min(team.getPointFrom().getX(), team.getPointTo().getX()), maxX = Math.max(team.getPointFrom().getX(), team.getPointTo().getX());
        double minY = Math.min(team.getPointFrom().getY(), team.getPointTo().getY()), maxY = Math.max(team.getPointFrom().getY(), team.getPointTo().getY());
        double minZ = Math.min(team.getPointFrom().getZ(), team.getPointTo().getZ()), maxZ = Math.max(team.getPointFrom().getZ(), team.getPointTo().getZ());
        return (location.getX() >= minX && location.getX() <= maxX) && (location.getY() >= minY && location.getY() <= maxY) && (location.getZ() >= minZ && location.getZ() <= maxZ);
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score++;
        this.player.teleport(this.team.getSpawnLocation());
        this.player.sendMessage(ChatUtils.addPersonnalScoreMessage());
        Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(ChatUtils.addTeamScoreMessage(this.team)));
    }
}
