package com.arthurpph.bedwars.game.generator;

import com.arthurpph.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Generator extends BukkitRunnable {
    private final Bedwars plugin;
    private final GeneratorType generatorType;
    private final Location location;
    private final int intervalTicks;

    public Generator(Bedwars plugin, GeneratorType generatorType, Location location) {
        this.plugin = plugin;
        this.generatorType = generatorType;
        this.location = location;
        this.intervalTicks = 20 * 10;
    }

    @Override
    public void run() {
        location.getWorld().dropItemNaturally(location, new ItemStack(generatorType.getMaterial()));
    }

    public void start() {
        runTaskTimer(plugin, 0L, intervalTicks);
    }

    public void stop() {
        cancel();
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public Location getLocation() {
        return location;
    }
}
