package com.arthurpph.bedwars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }
}
