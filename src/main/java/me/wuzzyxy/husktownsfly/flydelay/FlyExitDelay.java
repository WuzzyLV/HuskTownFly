package me.wuzzyxy.husktownsfly.flydelay;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import me.wuzzyxy.husktownsfly.utils.MessageUtils;
import net.william278.husktowns.claim.TownClaim;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyExitDelay extends BukkitRunnable {

    HuskTownsFly plugin;
    Player player;
    final int time;
    int passed;
    public FlyExitDelay(HuskTownsFly plugin, Player player) {
        this.plugin = plugin;
        time = plugin.getPluginConfig().FLIGHT_GRACE_PERIOD;
        this.player = player;
        passed = 0;
    }


    @Override
    public void run() {
        if (passed >= time) {
            if (!isInOwnTown(player)) { //if isint in town then turn off flight
                player.setAllowFlight(false);
                MessageUtils.sendMessage(player, plugin.getPluginConfig().FLIGHT_ON_EXIT_MESSAGE);
            }
            cancel();
            return;
        }
        if (plugin.getFlyerList().isFlyEnabled(player.getUniqueId())) {
            if (!isInOwnTown(player)){
                MessageUtils.sendMessage(
                        player,
                        plugin.getPluginConfig().FLIGHT_ON_EXIT_WARN_MESSAGE
                                .replace("%time%", String.valueOf(time - passed))
                );
            }else { //if goes back into town
                cancel();
            }
        } else {
            player.setAllowFlight(false);
            cancel();
        }
        passed++;
    }

    private boolean isInOwnTown(Player player) {
        TownClaim claim= plugin.getHuskTownsAPI().getClaimAt(player.getLocation()).orElse(null);
        if (claim == null) return false;
        return claim.town().getMembers().containsKey(player.getUniqueId());
    }
}
