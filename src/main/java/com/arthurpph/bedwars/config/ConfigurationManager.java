package com.arthurpph.bedwars.config;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.generator.Generator;
import com.arthurpph.bedwars.game.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ConfigurationManager {
    private static final List<String> ISLAND_CONFIGURATION_PATHS = List.of(
            "firstCornerLocation",
            "secondCornerLocation",
            "bedLocation",
            "spawnLocation",
            "teamUpgradeLocation",
            "generatorLocation",
            "shopLocation"
    );

    private final Bedwars plugin;
    private final ConfigurationSection mapSection;

    public ConfigurationManager(Bedwars plugin, String mapName) {
        this.plugin = plugin;

        final Configuration configuration = plugin.getConfig();
        setupSection(configuration, "maps");
        setupSection(configuration, "maps." + mapName);
        this.mapSection = configuration.getConfigurationSection("maps." + mapName);
        setupIslands();
        plugin.saveConfig();
    }

    public void setGenerator(Generator generator) {
        setupSection(mapSection, "generators");
        final ConfigurationSection generatorsSection = mapSection.getConfigurationSection("generators");
        final String generatorId = UUID.randomUUID().toString();
        final ConfigurationSection generatorSection = generatorsSection.createSection(generatorId);
        generatorSection.set("type", generator.generatorType().toString());
        saveLocation(generatorSection, "location", generator.location());
        plugin.saveConfig();
    }

    public void setIslandLocation(TeamColor color, String path, Location location) {
        setupSection(mapSection, color.name());
        final ConfigurationSection islandSection = mapSection.getConfigurationSection(color.name());
        saveLocation(islandSection, path, location);
        plugin.saveConfig();
    }

    public Map<String, Location> getIslandsConfig(TeamColor color) throws IllegalArgumentException {
        final String teamColorName = color.name();
        final ConfigurationSection islandSection = mapSection.getConfigurationSection(color.name());
        if(islandSection == null) {
            throw new IllegalStateException("Island section for " + teamColorName + " does not exist.");
        }
        final Map<String, Location> locations = new HashMap<>();

        for(String path : ISLAND_CONFIGURATION_PATHS) {
            if(!islandSection.isSet(path)) {
                throw new IllegalArgumentException("Path " + path + " does not exist in the " + teamColorName + " island configuration section.");
            }
            locations.put(path, getLocation(islandSection, path));
        }

        return locations;
    }

    public void setupSection(ConfigurationSection section, String path) {
        if(!section.isConfigurationSection(path)) {
            section.createSection(path);
        }
    }

    private Location getLocation(ConfigurationSection section, String path) throws IllegalArgumentException {
        if(!section.isConfigurationSection(path)) {
            throw new IllegalArgumentException("Path " + path + " does not exist in the " + section.getName() + " configuration section.");
        }

        double x = section.getDouble(path + ".x");
        double y = section.getDouble(path + ".y");
        double z = section.getDouble(path + ".z");
        float yaw = (float) section.getDouble(path + ".yaw");
        float pitch = (float) section.getDouble(path + ".pitch");
        return new Location(Bukkit.getWorld(mapSection.getName()), x, y, z, yaw, pitch);
    }

    private void saveLocation(ConfigurationSection section, String path, Location location) {
        section.set(path + ".x", location.getX());
        section.set(path + ".y", location.getY());
        section.set(path + ".z", location.getZ());
        section.set(path + ".yaw", location.getYaw());
        section.set(path + ".pitch", location.getPitch());
    }

    private void setupIslands() {
        for(TeamColor color : TeamColor.values()) {
            setupSection(mapSection, color.name());
        }
    }
}
