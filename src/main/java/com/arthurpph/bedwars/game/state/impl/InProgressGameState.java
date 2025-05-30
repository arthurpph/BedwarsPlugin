package com.arthurpph.bedwars.game.state.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.Game;
import com.arthurpph.bedwars.game.GamePlayer;
import com.arthurpph.bedwars.game.generator.Generator;
import com.arthurpph.bedwars.game.generator.IslandGenerator;
import com.arthurpph.bedwars.game.island.Island;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.game.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class InProgressGameState implements GameState {
    private final Bedwars plugin;
    private final Game game;
    private final Set<IslandGenerator> islandGenerators;
    private final Set<Generator> worldGenerators;

    public InProgressGameState(Bedwars plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
        this.islandGenerators = new HashSet<>();
        this.worldGenerators = new HashSet<>();
    }

    @Override
    public void onEnable() {
        setTeams();
        teleportPlayers();
        startGenerators();
    }

    @Override
    public void onDisable() {
        final List<Generator> allGenerators = new ArrayList<>();
        allGenerators.addAll(islandGenerators);
        allGenerators.addAll(worldGenerators);

        for(Generator generator : allGenerators) {
            generator.stop();
        }
    }

    @Override
    public GameState getNextGameState() {
        return null;
    }

    private void setTeams() {
        List<TeamColor> colors = new ArrayList<>(List.of(TeamColor.values()));
        Collections.shuffle(colors);
        Iterator<TeamColor> colorIterator = colors.iterator();
        for(GamePlayer gamePlayer : game.getPlayers()) {
            if(!colorIterator.hasNext()) {
                final Player player = Bukkit.getPlayer(gamePlayer.getUuid());
                if(player != null) {
                    player.kickPlayer("No more teams available. Please report this to the staff team");
                }
                continue;
            }
            TeamColor color = colorIterator.next();
            gamePlayer.setTeamColor(color);
        }
    }

    private void teleportPlayers() {
        for(GamePlayer gamePlayer : game.getPlayers()) {
            final Player player = Bukkit.getPlayer(gamePlayer.getUuid());
            if(player == null) continue;
            final Island island = game.getGameWorld().getIslandByPlayer(gamePlayer);
            player.teleport(island.spawnLocation());
        }
    }

    private void startGenerators() {
        for(Island island : game.getGameWorld().getIslands()) {
            final IslandGenerator islandGenerator = new IslandGenerator(plugin, island.generatorLocation(), island.teamColor());
            islandGenerator.start();
            islandGenerators.add(islandGenerator);
        }

        for(Generator generator : game.getGameWorld().getGenerators()) {
            generator.start();
            worldGenerators.add(generator);
        }
    }
}
