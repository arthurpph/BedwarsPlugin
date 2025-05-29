package com.arthurpph.bedwars.util;

import com.arthurpph.bedwars.game.GameWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public final class WorldUtils {
    public static World createGameWorld(String sourceWorldName) {
//        String targetWorldName = sourceWorldName + System.currentTimeMillis();
//
//        try {
//            loadWorldFromSubfolder("bedwarsmaps", sourceWorldName, targetWorldName);
//        } catch (IOException e) {
//            Bukkit.getLogger().severe("Error loading world " + sourceWorldName + ": " + e.getMessage());
//            return null;
//        }

        WorldCreator creator = new WorldCreator(sourceWorldName);
        return Bukkit.createWorld(creator);
    }

//    public static void loadWorldFromSubfolder(String subfolder, String sourceWorldName, String targetWorldName) throws IOException {
//        File source = new File(Bukkit.getServer().getWorldContainer(), subfolder + File.separator + sourceWorldName);
//        File target = new File(Bukkit.getServer().getWorldContainer(), targetWorldName);
//
//        if(!source.exists()) {
//            throw new FileNotFoundException("Source world not found: " + source.getAbsolutePath());
//        }
//
//        if(target.exists()) return;
//
//        FileUtils.copyFolder(source, target);
//    }
//    TODO: use subfolder to find game world for better organization
}
