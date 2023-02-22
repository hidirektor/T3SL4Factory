package me.t3sl4.factory;

import me.t3sl4.factory.util.SettingsManager;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.hologram.base.Holograms;
import me.t3sl4.factory.util.mcyaml.YamlOf;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class T3SL4Factory extends JavaPlugin {
    SettingsManager manager = SettingsManager.getInstance();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
        Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
        Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
        Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
        Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
        Bukkit.getConsoleSender().sendMessage("    ");
        Bukkit.getConsoleSender().sendMessage("T3SL4 Series: T3SL4Factory");
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") || Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &aPlaceholder Destegi Aktif Edildi"));
            if(MessageUtil.Placeholder && Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                //new MVdWPlaceholder();
                Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &cMVdWPlaceholder tespit edildi!"));
                Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &cPlaceholders: &e[ {madencoin_miktar} ]"));
            } else if(!MessageUtil.Placeholder && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                //new PAPIPlaceholder(this).register();
                Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &cPlaceholderAPI tespit edildi!"));
                Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &cPlaceholders: &e[ %madencoin_miktar% ]"));
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(T3SL4Factory.chatcolor("&e[CAC] &cPlaceholder yuklu olmadigindan bazi ozellikler devre disi"));
        }
        //Holograms hologram =
                //new Holograms(this, new YamlOf(this, "natural", "holograms"));
        this.manager.setup(this);
    }

    public void onDisable() {
        this.manager.stop();
    }

    public static String chatcolor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorizeList(List<String> str) {
        for(int x=0; x<str.size(); x++) {
            str.set(x, str.get(x).replace("&", "§"));
        }
        return str;
    }

    public static T3SL4Factory getPlugin() {
        return (T3SL4Factory) Bukkit.getPluginManager().getPlugin("T3SL4Factory");
    }
}
