package com.arthurpph.bedwars.config;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.generator.Generator;
import com.arthurpph.bedwars.team.TeamColor;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class ConfigurationManager {
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

    private void saveLocation(ConfigurationSection section, String path, Location location) {
        section.set(path + ".x", location.getX());
        section.set(path + ".y", location.getY());
        section.set(path + ".z", location.getZ());
        section.set(path + ".yaw", location.getYaw());
        section.set(path + ".pitch", location.getPitch());
    }

    private void setupSection(ConfigurationSection section, String path) {
        if(!section.isConfigurationSection(path)) {
            section.createSection(path);
        }
    }

    private void setupIslands() {
        for(TeamColor color : TeamColor.values()) {
            setupSection(mapSection, color.name());
        }
    }
}
