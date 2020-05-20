package fr.ziedelth.towers.teams;

import fr.ziedelth.towers.builders.ScoreboardBuilder;
import fr.ziedelth.towers.utils.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class RedTeam implements Teams {
    @Override
    public String getName() {
        return "§cRouge";
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(Bukkit.getWorlds().get(0), 84.5, 192, 1152.5, 90f, 0f);
    }

    @Override
    public Location getPointFrom() {
        return new Location(Bukkit.getWorlds().get(0), 86, 200, 1151);
    }

    @Override
    public Location getPointTo() {
        return new Location(Bukkit.getWorlds().get(0), 83, 205, 1154);
    }

    @Override
    public ScoreboardBuilder.TeamBuilder getTeamBuilder() {
        return new ScoreboardBuilder.TeamBuilder().setChatColor(ChatColor.RED).setName(this.getName()).setHasCollision(false);
    }
}
