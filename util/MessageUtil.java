package me.t3sl4.factory.util;

import me.t3sl4.factory.T3SL4Factory;

import java.util.List;

public class MessageUtil {
    static SettingsManager manager = SettingsManager.getInstance();

    public static boolean Placeholder;
    public static boolean WorldSystem;
    public static boolean MySQLSystem;


    public static String Prefix;
    public static List<String> OfficialInfoLines;
    public static List<String> PlayerInfoLines;

    public static void loadMessages() {
        Prefix = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Prefix"));
        Placeholder = manager.config.getConfig().getBoolean("PlaceholderSupport");
        WorldSystem = manager.config.getConfig().getBoolean("Worlds.System");
        MySQLSystem = manager.config.getConfig().getBoolean("MySQL.System");
        OfficialInfoLines = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("InfoLines.Official"));
        PlayerInfoLines = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("InfoLines.Player"));
    }
}
