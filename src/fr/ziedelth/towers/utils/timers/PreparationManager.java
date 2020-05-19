package fr.ziedelth.towers.utils.timers;

import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;

public class PreparationManager extends Timer {
    public PreparationManager() {
        this.setMaxSeconds(10);
    }

    @Override
    public void start() {
        super.start();
        GameStates.setState(GameStates.PREPARATION);
    }

    @Override
    public void run() {

    }
}
