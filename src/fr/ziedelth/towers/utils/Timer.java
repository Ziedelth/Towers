package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.Towers;
import org.bukkit.Bukkit;

public abstract class Timer {
    private int taskId;
    private boolean hasStart = false;
    private int maxSeconds = -1;
    private int seconds = 0;

    public void start() {
        if (this.hasStart) return;
        this.hasStart = true;

        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Towers.getInstance(), () -> {
            this.seconds++;
            this.run();
        }, 0L, 20L);
    }

    public void stop() {
        if (!this.hasStart) return;
        this.hasStart = false;
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    public int getMaxSeconds() {
        return maxSeconds;
    }

    public void setMaxSeconds(int maxSeconds) {
        this.maxSeconds = maxSeconds + 1;
    }

    public int getSeconds() {
        return this.maxSeconds - this.seconds;
    }

    public void resetSeconds() {
        this.seconds = 0;
    }

    public abstract void run();
}
