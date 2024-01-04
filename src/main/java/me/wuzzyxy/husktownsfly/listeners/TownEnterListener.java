package me.wuzzyxy.husktownsfly.listeners;

import me.wuzzyxy.husktownsfly.FlightInTownSetting;
import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.flydelay.FlyExitDelay;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import net.william278.husktowns.events.PlayerEnterTownEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownEnterListener implements Listener {
    HuskTownsFly plugin;
    FlightInTownSetting flightInTownSetting;

    public TownEnterListener(HuskTownsFly plugin) {
        this.plugin = plugin;
        this.flightInTownSetting = plugin.getFlyerList();
    }

    @EventHandler
    public void onTownEnter(PlayerEnterTownEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("husktownsfly.bypass")) return;

        if (flightInTownSetting.isFlyEnabled(player.getUniqueId())) {
            if(event.getEnteredTownClaim().town().getMembers().containsKey(player.getUniqueId())){
                MessageUtils.sendMessage(player, plugin.getPluginConfig().FLIGHT_ON_ENTER_MESSAGE);
                player.setAllowFlight(true);
            }else {
                new FlyExitDelay(plugin, player).runTaskTimer(plugin, 0, 20);
            }
        }
    }
}
