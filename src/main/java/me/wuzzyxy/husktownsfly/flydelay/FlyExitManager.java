package me.wuzzyxy.husktownsfly.flydelay;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class FlyExitManager {
    private final HuskTownsFly plugin;
    private HashMap<UUID, FlyExitDelay> exitDelays;

    public FlyExitManager(HuskTownsFly plugin){
        this.plugin = plugin;
        exitDelays = new HashMap<>();
    }

    public void addExitDelay(Player player){
        FlyExitDelay exitDelay = new FlyExitDelay(plugin, player);
        exitDelay.runTaskTimer(plugin, 0, 20);
        if (hasExitDelay(player)){
            exitDelays.get(player.getUniqueId()).cancel();
            removeExitDelay(player);
        }
        exitDelays.put(player.getUniqueId(), exitDelay);
    }

    public void removeExitDelay(Player player){
        exitDelays.remove(player.getUniqueId());
    }

    public FlyExitDelay getExitDelay(Player player){
        return exitDelays.get(player.getUniqueId());
    }

    public boolean hasExitDelay(Player player){
        return exitDelays.containsKey(player.getUniqueId());
    }
}
