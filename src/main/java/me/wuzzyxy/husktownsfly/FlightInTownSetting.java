package me.wuzzyxy.husktownsfly;

import me.wuzzyxy.husktownsfly.database.Database;

import java.util.HashMap;
import java.util.UUID;

public class FlightInTownSetting {

    HashMap<UUID,Boolean> flyers;
    Database database;

    public FlightInTownSetting(Database database) {
        this.database = database;
        flyers=database.getFlyers();
    }

    public boolean isFlyEnabled(UUID playerUUID) {
        if (flyers.containsKey(playerUUID)) {
            return flyers.get(playerUUID);
        }
        database.setPlayerFlying(playerUUID, false);
        flyers.put(playerUUID, false);
        return false;
    }

    public boolean setFlight(UUID playerUUID, boolean flying) {
        database.setPlayerFlying(playerUUID, flying);
        flyers.put(playerUUID, flying);
        return flying;
    }




}
