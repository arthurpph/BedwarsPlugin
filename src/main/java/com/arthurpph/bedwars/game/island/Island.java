package com.arthurpph.bedwars.game.island;

import com.arthurpph.bedwars.game.team.TeamColor;
import org.bukkit.Location;

public record Island(Location firstCornerLocation,
                     Location secondCornerLocation,
                     Location bedLocation,
                     Location spawnLocation,
                     Location teamUpgradeLocation,
                     Location generatorLocation,
                     Location shopLocation,
                     TeamColor teamColor) {
}
