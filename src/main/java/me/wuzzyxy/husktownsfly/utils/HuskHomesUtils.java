package me.wuzzyxy.husktownsfly.utils;

import net.william278.husktowns.claim.Position;
import net.william278.husktowns.claim.World;
import org.bukkit.Location;

public class HuskHomesUtils {
    public static Position locationToPosition(Location location) {
        World world = World.of(location.getWorld().getUID(), location.getWorld().getName(), location.getWorld().getEnvironment().name());
        return Position.at(location.getBlockX(), location.getBlockY(), location.getBlockZ(), world);
    }
}
