package com.arthurpph.bedwars.generator;

import org.bukkit.Location;

public class Generator {
    private final GeneratorType generatorType;
    private final Location location;

    public Generator(GeneratorType generatorType, Location location) {
        this.generatorType = generatorType;
        this.location = location;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public Location getLocation() {
        return location;
    }
}
