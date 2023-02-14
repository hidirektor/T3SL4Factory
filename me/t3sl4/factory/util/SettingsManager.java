package me.t3sl4.factory.util;

import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.commands.FactoryCommand;
import me.t3sl4.factory.item.CustomItem;
import me.t3sl4.factory.listener.PlaceListener;
import me.t3sl4.factory.mysql.MySQL;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;

public class SettingsManager {
    static SettingsManager instance = new SettingsManager();

    public ConfigAPI config;

    private T3SL4Factory tfactory;

    public CustomItem factoryItem;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(T3SL4Factory tfactory) {
        this.tfactory = tfactory;
        this.config = new ConfigAPI(T3SL4Factory.getPlugin(), "settings", Boolean.valueOf(true));
        if (MessageUtil.MySQLSystem) {
            MySQL.readMySQL();
            MySQL.connect();
            MySQL.createTable();
        }
        registerCommands();
        registerListener(new Listener[] { new PlaceListener()});
        MessageUtil.loadMessages();

        int facID = 0;
        int facData = 0;
        String[] tempVals = MessageUtil.FactoryItemID.split(":");
        if (tempVals.length == 1) {
            facID = Integer.parseInt(tempVals[0]);
            facData = 0;
        }
        if (tempVals.length == 2) {
            facID = Integer.parseInt(tempVals[0]);
            facData = Integer.parseInt(tempVals[1]);
        }
        this.factoryItem = new CustomItem(facID, facData, MessageUtil.FactoryItemName, MessageUtil.FactoryItemLore, MessageUtil.FactoryItemEnchants, MessageUtil.SFactoryItemLore, MessageUtil.SFactoryItemEnchant);
    }

    public void stop() {
        if (this.config.getConfig().getBoolean("mysql-enabled"))
            MySQL.close();
    }

    private void registerCommands() {
        this.tfactory.getCommand("factory").setExecutor(new FactoryCommand());
    }

    private void registerListener(Listener... listeners) {
        Arrays.<Listener>stream(listeners).forEach(listener -> this.tfactory.getServer().getPluginManager().registerEvents(listener, (Plugin)this.tfactory));
    }
}
