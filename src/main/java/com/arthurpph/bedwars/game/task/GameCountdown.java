package com.arthurpph.bedwars.game.task;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class GameCountdown extends BukkitRunnable {
    private final Bedwars plugin;
    private final Game game;
    private final Runnable onFinish;
    private int count;

    public GameCountdown(Bedwars plugin, Game game, int seconds, Runnable onFinish) {
        this.plugin = plugin;
        this.game = game;
        this.count = seconds;
        this.onFinish = onFinish;
    }

    @Override
    public void run() {
        List<Player> gamePlayers = game.getPlayers().stream()
                .map(gamePlayer -> Bukkit.getPlayer(gamePlayer.getUuid()))
                .filter(Objects::nonNull)
                .toList();

        if(count <= 0) {
            for(Player player : gamePlayers) {
                if(player == null || !player.isOnline()) continue;

                player.sendMessage(ChatColor.GREEN + "Game starting!");
            }
            cancel();
            Bukkit.getScheduler().runTask(plugin, onFinish);
            return;
        }

        for(Player player : gamePlayers) {
            if(player == null || !player.isOnline()) continue;

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            player.sendMessage(ChatColor.YELLOW + "Game starting in " + count + " seconds!");
        }
        count--;
    }
}
