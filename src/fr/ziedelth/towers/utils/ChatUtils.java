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

    public static String getNotEnoughPlayers() {
        return getPrefix() + "§cIl n'y a pas assez de joueurs dans la partie ! Remise à zéro du compte à rebours !";
    }

    public static String getEndMessage(int seconds) {
        return getPrefix() + "La partie se termine dans §e" + seconds + " seconde" + (seconds > 1 ? "s" : "") + " §7!";
    }

    public static String getDrawMessage() {
        return getPrefix() + "§fEgalité §7! Vous ferez mieux la prochaine fois !";
    }

    public static String getWinMessage() {
        return getPrefix() + "§aVictoire §7! Vous avez gagné la partie !";
    }

    public static String getLoseMessage() {
        return getPrefix() + "§cDéfaite §7! Vous vous êtes bien défendu !";
    }

    public static String getTeamJoinMessage(Teams team) {
        return getPrefix() + "Vous rejoignez l'équipe " + team.getName() + " §7!";
    }

    public static String addPersonnalScoreMessage() {
        return getPrefix() + "Vous avez marqué un point !";
    }

    public static String addTeamScoreMessage(Teams team) {
        return getPrefix() + team.getName() + " §7vient de marquer un point !";
    }

    public static String getDeathMessage(Player player) {
        return getPrefix() + "§c" + player.getName() + " §7est mort " + (player.getKiller() != null ? "de la main de §4" + player.getKiller().getName() + " §7!" : "!");
    }
}
