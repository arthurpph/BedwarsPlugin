package com.arthurpph.bedwars.game.generator;

import org.bukkit.Material;

public enum GeneratorType {
    IRON(Material.IRON_INGOT),
    EMERALD(Material.EMERALD),
    DIAMOND(Material.DIAMOND);

    private final Material material;

    GeneratorType(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
