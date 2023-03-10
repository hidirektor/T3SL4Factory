package me.t3sl4.factory.util;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.commands.FactoryCommand;
import me.t3sl4.factory.util.hologram.base.Holograms;
import me.t3sl4.factory.util.item.CustomItem;
import me.t3sl4.factory.listener.BreakListener;
import me.t3sl4.factory.listener.ClickListener;
import me.t3sl4.factory.listener.InventoryClickListener;
import me.t3sl4.factory.listener.PlaceListener;
import me.t3sl4.factory.util.mcyaml.YamlOf;
import me.t3sl4.factory.util.mysql.MySQL;
import org.bukkit.event.Listener;
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

    public Holograms holograms;

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

        loadCustomItem();
        factoryItemTask();
        //this.holograms = hologram;
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

    public void loadCustomItem() {
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
}
