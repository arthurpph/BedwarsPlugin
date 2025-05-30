package com.arthurpph.bedwars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {
    @EventHandler
    public void handleProjectileLaunch(ProjectileLaunchEvent event) {
        event.setCancelled(true);
    }
}
