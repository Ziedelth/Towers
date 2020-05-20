package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.Towers;
import org.bukkit.Bukkit;

public class TaskUtils {
    public static void runTaskLater(Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(Towers.getInstance(), runnable, 1L);
    }
}
