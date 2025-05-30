package com.arthurpph.bedwars;

import com.arthurpph.bedwars.command.BedwarsCommand;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.GameManager;
import com.arthurpph.bedwars.listener.*;
import com.arthurpph.bedwars.wizard.view.WizardIslandSelectorView;
import com.arthurpph.bedwars.wizard.WizardManager;
import me.devnatan.inventoryframework.ViewFrame;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bedwars extends JavaPlugin {
    private GameManager gameManager;
    private WizardManager wizardManager;
    private ConfigurationManager configManager;
    private ViewFrame viewFrame;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        createManagers();
        registerEvents();
        registerViews();
        registerCommands();
    }

    @Override
    public void onDisable() {}

    private void createManagers() {
        configManager = new ConfigurationManager(this, "bedwarsone");
        wizardManager = new WizardManager();
        gameManager = new GameManager(this, wizardManager, configManager);
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerInteractListener(wizardManager), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);
    }

    private void registerCommands() {
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(
            new BedwarsCommand(this, gameManager, wizardManager, configManager, viewFrame)
        );
    }

    private void registerViews() {
        viewFrame = ViewFrame.create(this)
            .with(new WizardIslandSelectorView(this, wizardManager, configManager))
            .register();
    }
}
