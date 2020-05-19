package fr.ziedelth.towers.utils.timers;

import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;

public class GameManager extends Timer {
    public GameManager() {
        this.setMaxSeconds(1200);
    }

    @Override
    public void start() {
        super.start();
        GameStates.setState(GameStates.GAME);
    }

    @Override
    public void run() {

    }
}
