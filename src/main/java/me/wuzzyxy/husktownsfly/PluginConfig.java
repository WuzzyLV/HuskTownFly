package me.wuzzyxy.husktownsfly;

public class PluginConfig {

    public PluginConfig(HuskTownsFly plugin) {
        plugin.saveDefaultConfig();

        NO_PERMISSION_MESSAGE = plugin.getConfig().getString("messages.no-permission", "You don't have permission to do that");
        FLIGHT_ENABLED_MESSAGE = plugin.getConfig().getString("messages.flight-enabled", "Flight enabled");
        FLIGHT_DISABLED_MESSAGE = plugin.getConfig().getString("messages.flight-disabled", "Flight disabled");
        FLIGHT_ON_EXIT_MESSAGE = plugin.getConfig().getString("messages.flight-on-exit", "Flight disabled because town left");
        FLIGHT_ON_EXIT_WARN_MESSAGE = plugin.getConfig().getString("messages.flight-exit-warning", "Flight will be disabled in %time%.");
        FLIGHT_ON_ENTER_MESSAGE = plugin.getConfig().getString("messages.flight-on-enter", "Flight enabled because town entered");
        FLIGHT_GRACE_PERIOD = plugin.getConfig().getInt("flight-grace-period", 5);
    }

    public String NO_PERMISSION_MESSAGE;
    public String FLIGHT_ENABLED_MESSAGE;
    public String FLIGHT_DISABLED_MESSAGE;
    public String FLIGHT_ON_EXIT_MESSAGE;

    public String FLIGHT_ON_EXIT_WARN_MESSAGE;
    public String FLIGHT_ON_ENTER_MESSAGE;
    public int FLIGHT_GRACE_PERIOD;


}
