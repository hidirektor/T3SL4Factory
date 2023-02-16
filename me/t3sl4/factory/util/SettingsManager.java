package me.t3sl4.factory.util;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import jdk.xml.internal.XMLLimitAnalyzer;
import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.commands.FactoryCommand;
import me.t3sl4.factory.item.CustomItem;
import me.t3sl4.factory.listener.BreakListener;
import me.t3sl4.factory.listener.ClickListener;
import me.t3sl4.factory.listener.InventoryClickListener;
import me.t3sl4.factory.listener.PlaceListener;
import me.t3sl4.factory.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    static SettingsManager instance = new SettingsManager();

    public ConfigAPI config;
    public ConfigAPI data;
    public ConfigAPI playerdata;

    private T3SL4Factory tfactory;

    public CustomItem factoryItem;

    public static SettingsManager getInstance() {
        return instance;
    }

    public static Map<Integer, BukkitTask> PlacedFactories = new HashMap<>();

    public void setup(T3SL4Factory tfactory) {
        this.tfactory = tfactory;
        this.config = new ConfigAPI(T3SL4Factory.getPlugin(), "settings", Boolean.valueOf(true));
        this.data = new ConfigAPI(T3SL4Factory.getPlugin(), "data", Boolean.valueOf(true));
        this.playerdata = new ConfigAPI(T3SL4Factory.getPlugin(), "playerdata", Boolean.valueOf(true));
        if (MessageUtil.MySQLSystem) {
            MySQL.readMySQL();
            MySQL.connect();
            MySQL.createTable();
        }
        registerCommands();
        registerListener(new Listener[] { new PlaceListener(), new BreakListener(), new ClickListener(), new InventoryClickListener()});
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
        factoryItemTask();
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

    private void factoryItemTask() {
        FactoryAPI.update(0);
    }
}
