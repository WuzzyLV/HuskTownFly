package me.wuzzyxy.husktownsfly.database;

import java.util.HashMap;
import java.util.UUID;

public interface Database {

    boolean isPlayerFlying(UUID playerUUID);
    boolean setPlayerFlying(UUID playerUUID, boolean flying);
    HashMap<UUID, Boolean> getFlyers();
    void close();
}
