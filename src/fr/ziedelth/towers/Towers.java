package fr.ziedelth.towers;

import fr.ziedelth.towers.listeners.EventManager;
import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;
import fr.ziedelth.towers.utils.TowerPlayer;
import fr.ziedelth.towers.utils.timers.EndManager;
import fr.ziedelth.towers.utils.timers.GameManager;
import fr.ziedelth.towers.utils.timers.LobbyManager;
import fr.ziedelth.towers.utils.timers.PreparationManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Towers extends JavaPlugin {
    private static Towers instance;
    private final LobbyManager lobbyManager = new LobbyManager();
    private final PreparationManager preparationManager = new PreparationManager();
    private final GameManager gameManager = new GameManager();
    private final EndManager endManager = new EndManager();

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
}
