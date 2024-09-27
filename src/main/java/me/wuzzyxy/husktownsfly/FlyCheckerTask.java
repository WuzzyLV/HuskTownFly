package me.wuzzyxy.husktownsfly;

import me.wuzzyxy.husktownsfly.flydelay.FlyExitManager;
import me.wuzzyxy.husktownsfly.utils.HuskHomesUtils;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class FlyCheckerTask extends BukkitRunnable {

    HuskTownsFly plugin;
    FlyExitManager flyExitManager;

    public FlyCheckerTask(HuskTownsFly plugin) {
        this.plugin = plugin;
        this.flyExitManager = plugin.getFlyExitManager();
    }

    @Override
    public void run() {
        plugin.getFlyerList().getFlyers().forEach((uuid, flying) -> {
            if (!flying) return;

            Player p = plugin.getServer().getPlayer(uuid);
            if (p == null) return;
            if (!p.isOnline()) return;

            if (p.hasPermission("husktownsfly.bypass")) return;

            plugin.getHuskTownsAPI().getClaimAt(HuskHomesUtils.locationToPosition(p.getLocation())).ifPresentOrElse(claim -> {
                Map<UUID, Integer> members = claim.town().getMembers();
                if (!members.containsKey(p.getUniqueId())) {
                    p.setAllowFlight(false);
                    MessageUtils.sendMessage(p, plugin.getPluginConfig().FLIGHT_ON_EXIT_MESSAGE);
                    return;
                }

                if (p.getAllowFlight()) return;
                p.setAllowFlight(true);
                MessageUtils.sendMessage(p, plugin.getPluginConfig().FLIGHT_ON_ENTER_MESSAGE);
            }, () -> {
                if (!p.getAllowFlight()) return;
                if (flyExitManager.hasExitDelay(p)) return;
                flyExitManager.addExitDelay(p);
            });

        });
    }
}
