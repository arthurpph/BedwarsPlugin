package com.arthurpph.bedwars.game;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.generator.Generator;
import com.arthurpph.bedwars.game.island.Island;
import com.arthurpph.bedwars.game.team.TeamColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameWorld {
    private final ConfigurationManager configManager;
    private final World world;
    private final Set<Island> islands;
    private final Set<Generator> generators;

    public GameWorld(World world, ConfigurationManager configManager) {
        this.configManager = configManager;
        this.world = world;
        this.islands = new HashSet<>();
        this.generators = new HashSet<>();
        loadIslands();
        loadGenerators();
    }

    public World getWorld() {
        return world;
    }

    public Set<Island> getIslands() {
        return islands;
    }

    public Set<Generator> getGenerators() {
        return generators;
    }

    public Island getIslandByPlayer(GamePlayer gamePlayer) {
        return islands.stream().filter(island -> island.teamColor() == gamePlayer.getTeamColor()).findFirst().orElse(null);
    }

    private void loadIslands() {
        for(TeamColor color : TeamColor.values()) {
            Map<String, Location> config;
            try {
                config = configManager.getIslandsConfig(color);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Island configuration for " + color.name() + " is missing or invalid.", e);
            }

            Island island = new Island(
                    config.get("firstCornerLocation"),
                    config.get("secondCornerLocation"),
                    config.get("bedLocation"),
                    config.get("spawnLocation"),
                    config.get("teamUpgradeLocation"),
                    config.get("generatorLocation"),
                    config.get("shopLocation"),
                    color
            );
            islands.add(island);
        }
    }

    private void loadGenerators() {
        generators.addAll(configManager.getGenerators());
    }
}
