package fr.ziedelth.towers;

import fr.ziedelth.towers.listeners.EventManager;
import fr.ziedelth.towers.teams.BlueTeam;
import fr.ziedelth.towers.teams.RedTeam;
import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Teams;
import fr.ziedelth.towers.utils.Timer;
import fr.ziedelth.towers.utils.TowerPlayer;
import fr.ziedelth.towers.utils.timers.EndManager;
import fr.ziedelth.towers.utils.timers.GameManager;
import fr.ziedelth.towers.utils.timers.LobbyManager;
import fr.ziedelth.towers.utils.timers.PreparationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Towers extends JavaPlugin {
    private static Towers instance;
    private final LobbyManager lobbyManager = new LobbyManager();
    private final PreparationManager preparationManager = new PreparationManager();
    private final GameManager gameManager = new GameManager();
    private final EndManager endManager = new EndManager();
    private final List<Teams> teams = new ArrayList<>();
    private Location lobby_location;
    private final int maxScore = 5;
    private final int minPlayers = 2;

    public static Towers getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
        GameStates.setState(GameStates.LOBBY);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        new EventManager();
        this.addTeam(new RedTeam());
        this.addTeam(new BlueTeam());
        this.lobby_location = new Location(Bukkit.getWorlds().get(0), 0, 64, 0, 0f, 0f);

        Bukkit.getOnlinePlayers().forEach(player -> {
            TowerPlayer towerPlayer = TowerPlayer.get(player);
            towerPlayer.init();
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Timer getCurrentManager() {
        return (GameStates.isState(GameStates.LOBBY) ? this.lobbyManager : GameStates.isState(GameStates.PREPARATION) ? this.preparationManager : GameStates.isState(GameStates.GAME) ? this.gameManager : this.endManager);
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public PreparationManager getPreparationManager() {
        return preparationManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public EndManager getEndManager() {
        return endManager;
    }

    public void addTeam(Teams team) {
        this.teams.add(team);
    }

    public List<Teams> getTeams() {
        return teams;
    }

    public boolean allTeamsHaveTheSameNumberPlayers() {
        int length = TowerPlayer.getPlayersInTeam(this.teams.get(0)).size();
        for (int i = 1; i < this.teams.size(); i++) if (TowerPlayer.getPlayersInTeam(this.teams.get(i)).size() != length) return false;
        return true;
    }

    public Teams getSmallerTeam() {
        Teams team = null;
        int length = Integer.MAX_VALUE;

        for (int i = 1; i < this.teams.size(); i++) {
            Teams check = this.teams.get(i);
            int team_length = TowerPlayer.getPlayersInTeam(check).size();

            if (team_length < length) {
                length = team_length;
                team = check;
            }
        }

        return team;
    }

    public boolean allTeamsHaveTheSameScore() {
        int length = TowerPlayer.getScoreOf(this.teams.get(0));
        for (int i = 1; i < this.teams.size(); i++) if (TowerPlayer.getScoreOf(this.teams.get(i)) != length) return false;
        return true;
    }

    public Teams getTeamWithTheHighestScore() {
        Teams team = null;
        int length = Integer.MIN_VALUE;

        for (int i = 1; i < this.teams.size(); i++) {
            Teams check = this.teams.get(i);
            int team_score = TowerPlayer.getScoreOf(check);

            if (team_score > length) {
                length = team_score;
                team = check;
            }
        }

        return team;
    }

    public Location getLobbyLocation() {
        return lobby_location;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMinPlayers() {
        return minPlayers;
    }
}
