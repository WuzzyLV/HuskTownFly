package me.wuzzyxy.husktownsfly.listeners;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.Listener;
import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;

public class PlayerTeleportListener implements Listener {

    HuskTownsFly plugin;
    public PlayerTeleportListener(HuskTownsFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();

        if (player.hasPermission("husktownsfly.bypass")) return;

        Location location = event.getTo();

        if (!plugin.getFlyerList().isFlyEnabled(player.getUniqueId())) {
           player.setAllowFlight(false);
           return;
        }

        plugin.getHuskTownsAPI().getClaimAt(location).ifPresentOrElse(claim -> {
            Map<UUID, Integer> members = claim.town().getMembers();
            if (!members.containsKey(player.getUniqueId())) {
                player.setAllowFlight(false);
                MessageUtils.sendMessage(player, plugin.getPluginConfig().FLIGHT_ON_EXIT_MESSAGE);
                return;
            }

            player.setAllowFlight(true);
            MessageUtils.sendMessage(player, plugin.getPluginConfig().FLIGHT_ON_ENTER_MESSAGE);
        }, () -> {
            if (player.getAllowFlight()){
                player.setAllowFlight(false);
                MessageUtils.sendMessage(player, plugin.getPluginConfig().FLIGHT_ON_EXIT_MESSAGE);
            }
        });
    }

}
