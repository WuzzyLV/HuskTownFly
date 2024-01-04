package me.wuzzyxy.husktownsfly.database;

import me.wuzzyxy.husktownsfly.HuskTownsFly;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class H2Database implements Database {
    HuskTownsFly plugin;
    public H2Database(HuskTownsFly plugin) throws SQLException {
        this.plugin = plugin;
        initialize();
    }

    private Connection connection;
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            setConnection();
        }
        return connection;
    }

    private void setConnection() throws SQLException {
        JdbcConnectionPool cp = JdbcConnectionPool.create(
                "jdbc:h2:"+plugin.getDataFolder().getAbsolutePath()+"/data/Database;AUTO_SERVER=TRUE",
                "sa",
                "sa"
        );
        connection = cp.getConnection();
    }

    private void initialize() throws SQLException {
        getConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(36) PRIMARY KEY, flying BOOLEAN)");
    }

    private boolean playerExists(UUID playerUUID) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM players WHERE uuid = ?");
            ps.setString(1, playerUUID.toString());
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPlayerFlying(UUID playerUUID) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM players WHERE uuid = ?");
            ps.setString(1, playerUUID.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return setPlayerFlying(playerUUID, false);
            }
            return rs.getBoolean("flying");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setPlayerFlying(UUID playerUUID, boolean flying) {
        try {
            PreparedStatement ps;
            if (playerExists(playerUUID)) {
                ps = getConnection().prepareStatement("UPDATE players SET flying = ? WHERE uuid = ?");
            } else {
                ps = getConnection().prepareStatement("INSERT INTO players (flying, uuid) VALUES (?, ?)");
            }
            ps.setBoolean(1, flying);
            ps.setString(2, playerUUID.toString());
            ps.execute();
            return flying;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<UUID, Boolean> getFlyers() {
        HashMap<UUID, Boolean> flyers = new HashMap<>();
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM players");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flyers.put(UUID.fromString(rs.getString("uuid")), rs.getBoolean("flying"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flyers;
    }

    @Override
    public void close() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
