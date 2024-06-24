package me.wuzzyxy.husktownsfly;

import me.wuzzyxy.husktownsfly.commands.FlyCommand;
import me.wuzzyxy.husktownsfly.commands.ReloadCommand;
import me.wuzzyxy.husktownsfly.database.Database;
import me.wuzzyxy.husktownsfly.database.H2Database;
import me.wuzzyxy.husktownsfly.listeners.*;
import net.william278.husktowns.api.BukkitHuskTownsAPI;
import net.william278.husktowns.api.HuskTownsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class HuskTownsFly extends JavaPlugin {

    private BukkitHuskTownsAPI huskTownsAPI;
    private Database database;
    private FlightInTownSetting flightInTownSetting;
    private PluginConfig config;
    @Override
    public void onEnable() {
        huskTownsAPI = BukkitHuskTownsAPI.getInstance();

        config = new PluginConfig(this);

        try {
            database = new H2Database(this);
        } catch (SQLException e) {
            getLogger().severe("Failed to initialize database!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

        flightInTownSetting = new FlightInTownSetting(database);

        // Register listeners
        getServer().getPluginManager().registerEvents(new TownEnterListener(this), this);
        getServer().getPluginManager().registerEvents(new TownExitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);

        //register commands
        getCommand("townfly").setExecutor(new FlyCommand(this));
        getCommand("husktownsfly").setExecutor(new ReloadCommand(this));

    }

    @Override
    public void onDisable() {
        database.close();
    }
    // GETTERS
    public BukkitHuskTownsAPI getHuskTownsAPI() {
        return huskTownsAPI;
    }

    public FlightInTownSetting getFlyerList() {
        return flightInTownSetting;
    }
    public PluginConfig getPluginConfig() {
        return config;
    }

    public PluginConfig reloadPluginConfig() {
        config = new PluginConfig(this);
        return config;
    }


}
