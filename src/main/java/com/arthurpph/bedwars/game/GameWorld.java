package com.arthurpph.bedwars.game;

import org.bukkit.World;

public class GameWorld {
    private final World world;

    public GameWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
