package com.arthurpph.bedwars.game.generator;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.team.TeamColor;
import org.bukkit.Location;

public class IslandGenerator extends Generator{
    private final TeamColor teamColor;

    public IslandGenerator(Bedwars plugin, Location location, TeamColor teamColor) {
        super(plugin, GeneratorType.IRON, location);
        this.teamColor = teamColor;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }
}
