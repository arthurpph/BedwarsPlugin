package com.arthurpph.bedwars.game.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum TeamColor {
    RED(Material.RED_WOOL, ChatColor.RED, "Red"),
    BLUE(Material.BLUE_WOOL, ChatColor.BLUE, "Blue"),;
//    GREEN(Material.GREEN_WOOL, ChatColor.GREEN, "Green"),
//    YELLOW(Material.YELLOW_WOOL, ChatColor.YELLOW, "Yellow"),
//    AQUA(Material.CYAN_WOOL, ChatColor.AQUA, "Aqua"),
//    GRAY(Material.GRAY_WOOL, ChatColor.GRAY, "Gray"),
//    WHITE(Material.WHITE_WOOL, ChatColor.WHITE, "White"),
//    PINK(Material.PINK_WOOL, ChatColor.LIGHT_PURPLE, "Pink");

    private final Material woolMaterial;
    private final ChatColor chatColor;
    private final String formattedName;

    TeamColor(Material woolMaterial, ChatColor chatColor, String formattedName) {
        this.woolMaterial = woolMaterial;
        this.chatColor = chatColor;
        this.formattedName = formattedName;
    }

    public static TeamColor fromWoolMaterial(Material material) {
        for (TeamColor color : values()) {
            if (color.getWoolMaterial() == material) {
                return color;
            }
        }
        throw new IllegalArgumentException("No TeamColor found for material: " + material);
    }

    public Material getWoolMaterial() {
        return woolMaterial;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
