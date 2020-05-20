package fr.ziedelth.towers.utils;

import fr.ziedelth.towers.builders.ScoreboardBuilder;
import org.bukkit.Color;
import org.bukkit.Location;

public interface Teams {
    String getName();
    Location getSpawnLocation();
    Location getPointFrom();
    Location getPointTo();
    ScoreboardBuilder.TeamBuilder getTeamBuilder();
    Color getArmorColor();
}
