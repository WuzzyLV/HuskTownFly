package me.wuzzyxy.husktownsfly.listeners;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class PlayerJoinLeaveListener implements Listener {

    HuskTownsFly plugin;
    public PlayerJoinLeaveListener(HuskTownsFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("husktownsfly.bypass")) return;

        Location location = player.getLocation();
        plugin.getHuskTownsAPI().getClaimAt(location).ifPresent(claim -> {
            Map<UUID, Integer> members = claim.town().getMembers();
            if (members.containsKey(player.getUniqueId())) {
                player.setAllowFlight(true);
            }
        });
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if (event.getPlayer().hasPermission("husktownsfly.bypass")) return;

        event.getPlayer().setAllowFlight(false);
    }

}
