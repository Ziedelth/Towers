package fr.ziedelth.towers.utils.timers;

import fr.ziedelth.towers.utils.GameStates;
import fr.ziedelth.towers.utils.Timer;

public class EndManager extends Timer {
    public EndManager() {
        this.setMaxSeconds(10);
    }

    @Override
    public void start() {
        super.start();
        GameStates.setState(GameStates.END);
    }

    @Override
    public void run() {

    }
}
