package com.arthurpph.bedwars;

import com.arthurpph.bedwars.command.BedwarsCommand;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.GameManager;
import com.arthurpph.bedwars.listener.PlayerInteractListener;
import com.arthurpph.bedwars.wizard.WizardContext;
import com.arthurpph.bedwars.wizard.WizardManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bedwars extends JavaPlugin {
    private GameManager gameManager;
    private WizardManager<WizardContext> wizardManager;
    private ConfigurationManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        createManagers();
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {}

    private void createManagers() {
        gameManager = new GameManager(this);
        wizardManager = new WizardManager<WizardContext>();
        configManager = new ConfigurationManager(this, "bedwarsone");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerInteractListener(wizardManager), this);
    }

    private void registerCommands() {
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(
            new BedwarsCommand(gameManager, wizardManager, configManager)
        );
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public WizardManager<WizardContext> getWizardManager() {
        return wizardManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configManager;
    }
}
