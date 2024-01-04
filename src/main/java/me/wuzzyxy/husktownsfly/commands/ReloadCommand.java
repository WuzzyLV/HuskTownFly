package me.wuzzyxy.husktownsfly.commands;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.PluginConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    HuskTownsFly plugin;
    PluginConfig config;
    public ReloadCommand(HuskTownsFly plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("Reloading HuskTownsFly config...");
        plugin.reloadConfig();

        return true;
    }
}