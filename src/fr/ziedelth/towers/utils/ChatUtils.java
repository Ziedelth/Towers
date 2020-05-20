package fr.ziedelth.towers.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static String getPrefix() {
        return "§5[§6§lTowers§5] §7";
    }

    public static String getPlayerJoinMessage(Player player) {
        return getPrefix() + "§e" + player.getName() + " §7a rejoint la partie ! §a(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")";
    }

    public static String getPlayerQuitMessage(Player player) {
        return getPrefix() + "§e" + player.getName() + " §7a quitté la partie ! §a(" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + Bukkit.getMaxPlayers() + ")";
    }

    public static String getStartMessage(int seconds) {
        return getPrefix() + "La partie commence dans §e" + seconds + " seconde" + (seconds > 1 ? "s" : "") + " §7!";
    }

    public static String getTeamJoinMessage(Teams team) {
        return getPrefix() + "Vous rejoignez l'équipe " + team.getName() + " §7!";
    }
}
