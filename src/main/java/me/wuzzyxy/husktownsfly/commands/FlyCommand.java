package me.wuzzyxy.husktownsfly.commands;

import me.wuzzyxy.husktownsfly.FlightInTownSetting;
import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.PluginConfig;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    HuskTownsFly plugin;
    FlightInTownSetting flightInTownSetting;
    PluginConfig config;
    public FlyCommand(HuskTownsFly plugin) {
        this.plugin = plugin;
        this.flightInTownSetting = plugin.getFlyerList();
        this.config = plugin.getPluginConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage("This command can only be executed by players!");
            return true;
        }
        Player player = (Player) commandSender;

        if (!player.hasPermission("husktownsfly.fly")) {
            MessageUtils.sendMessage(player, config.NO_PERMISSION_MESSAGE);
            return true;
        }

        if (flightInTownSetting.isFlyEnabled(player.getUniqueId())) {
            player.setAllowFlight(false);
            flightInTownSetting.setFlight(player.getUniqueId(), false);
            MessageUtils.sendMessage(player, config.FLIGHT_DISABLED_MESSAGE);
            return true;
        }
        
        plugin.getHuskTownsAPI().getClaimAt(player.getLocation()).ifPresent(claim -> {
            if (claim.town().getMembers().containsKey(player.getUniqueId())) {
                MessageUtils.sendMessage(player, config.FLIGHT_ON_ENTER_MESSAGE);
                player.setAllowFlight(true);
            }
        });
        flightInTownSetting.setFlight(player.getUniqueId(), true);
        MessageUtils.sendMessage(player, config.FLIGHT_ENABLED_MESSAGE);
    


        return true;
    }
}
