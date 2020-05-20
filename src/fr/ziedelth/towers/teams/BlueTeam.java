package fr.ziedelth.towers.teams;

import fr.ziedelth.towers.builders.ScoreboardBuilder;
import fr.ziedelth.towers.utils.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;

public class BlueTeam implements Teams {
    @Override
    public String getName() {
        return "§9Bleue";
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(Bukkit.getWorlds().get(0), -83.5, 192, 1152.5, -90f, 0f);
    }

    @Override
    public Location getPointFrom() {
        return new Location(Bukkit.getWorlds().get(0), -85, 200, 1154);
    }

    @Override
    public Location getPointTo() {
        return new Location(Bukkit.getWorlds().get(0), -82, 205, 1151);
    }

    @Override
    public ScoreboardBuilder.TeamBuilder getTeamBuilder() {
        return new ScoreboardBuilder.TeamBuilder().setChatColor(ChatColor.BLUE).setName(this.getName()).setHasCollision(false);
    }

    @Override
    public Color getArmorColor() {
        return Color.BLUE;
    }
}
