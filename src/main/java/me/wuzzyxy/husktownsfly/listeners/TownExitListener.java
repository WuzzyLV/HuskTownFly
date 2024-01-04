package me.wuzzyxy.husktownsfly.listeners;

import me.wuzzyxy.husktownsfly.FlightInTownSetting;
import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.PluginConfig;
import me.wuzzyxy.husktownsfly.flydelay.FlyExitDelay;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import net.william278.husktowns.events.PlayerLeaveTownEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class TownExitListener implements Listener {
    HuskTownsFly plugin;
    FlightInTownSetting flightInTownSetting;
    PluginConfig config;

    public TownExitListener(HuskTownsFly plugin) {
        this.plugin = plugin;
        this.flightInTownSetting = plugin.getFlyerList();
        this.config = plugin.getPluginConfig();
    }

    @EventHandler
    public void onTownExit(PlayerLeaveTownEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("husktownsfly.bypass")) return;

        if (flightInTownSetting.isFlyEnabled(player.getUniqueId())) {
            if(event.getLeftTownClaim().town().getMembers().containsKey(player.getUniqueId())){
                new FlyExitDelay(plugin, player).runTaskTimer(plugin, 0, 20);
            }
        }
    }
}
