package me.t3sl4.factory.util;

import me.t3sl4.factory.T3SL4Factory;

import java.util.List;

public class MessageUtil {
    static SettingsManager manager = SettingsManager.getInstance();

    public static boolean Placeholder;
    public static boolean WorldSystem;
    public static boolean MySQLSystem;
    public static boolean SFactoryItemLore;
    public static boolean SFactoryItemEnchant;

    public static String Prefix;
    public static List<String> OfficialInfoLines;
    public static List<String> PlayerInfoLines;
    public static String FactoryItemID;
    public static String FactoryItemName;
    public static List<String> FactoryItemLore;
    public static List<String> FactoryItemEnchants;
    public static List<String> AvailableWorlds;
    public static String FactoryMenuTitle;
    public static List<String> FactoryMenuOwnerLore;
    public static List<String> FactoryMenuPlayerLore;
    public static List<String> FactoryMenuBlockOwnerLore;
    public static List<String> FactoryMenuBlockPlayerLore;
    public static String FactoryDropItem;
    public static String PermissionError;
    public static String GiveCommandERR;
    public static String DistributeCommandERR;
    public static String PlayerNotFound;
    public static String MustBeNumber;
    public static String WorldError;
    public static String DeleteError;
    public static String NullFactoryError;
    public static String PurgeCommandERR;
    public static String SorgulaCommandERR;
    public static String StatsCommandERR;
    public static String ConsoleError;
    public static String FactoryMenuBlockOwnerItemName;
    public static String FactoryMenuBlockPlayerItemName;
    public static String FactoryMenuBlockPlayerCommand;
    public static String FactoryMenuBlockOwnerTitle;
    public static String FactoryMenuBlockPlayerTitle;


    public static String GivenItem;
    public static String DistributedItem;
    public static String AddedItem;
    public static String Reload;
    public static String Deleted;
    public static String Purged;
    public static String DonusturmeCommandERR;
    public static String NewMaterialError;
    public static String NoFactory;
    public static String AlreadyEqualsMaterial;
    public static String ReplacedAllFactories;

    public static void loadMessages() {
        Prefix = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Prefix"));
        Placeholder = manager.config.getConfig().getBoolean("PlaceholderSupport");
        WorldSystem = manager.config.getConfig().getBoolean("Worlds.System");
        MySQLSystem = manager.config.getConfig().getBoolean("MySQL.System");
        OfficialInfoLines = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("InfoLines.Official"));
        PlayerInfoLines = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("InfoLines.Player"));
        SFactoryItemLore = manager.config.getConfig().getBoolean("FactoryItem.System.Lore");
        SFactoryItemEnchant = manager.config.getConfig().getBoolean("FactoryItem.System.Enchant");
        FactoryItemID = manager.config.getConfig().getString("FactoryItem.Specs.ID");
        FactoryItemName = T3SL4Factory.chatcolor(manager.config.getConfig().getString("FactoryItem.Specs.Name"));
        FactoryItemLore = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("FactoryItem.Specs.Lore"));
        FactoryItemEnchants = T3SL4Factory.colorizeList(manager.config.getConfig().getStringList("FactoryItem.Specs.Enchants"));
        PermissionError = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.PermissionError"));
        GiveCommandERR = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.GiveCommandERR"));
        PlayerNotFound = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.PlayerNotFound"));
        DistributeCommandERR = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.DistributeCommandERR"));
        MustBeNumber = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.MustBeNumber"));
        GivenItem = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.GivenItem"));
        DistributedItem = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.DistributedItem"));
        AddedItem = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.AddedItem"));
        Reload = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.Reload"));
        WorldError = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.WorldError"));
        AvailableWorlds = manager.config.getConfig().getStringList("Worlds.List");
        DeleteError = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.DeleteCommandERR"));
        NullFactoryError = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.NullFactoryError"));
        PurgeCommandERR = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.PurgeCommandERR"));
        Deleted = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.Deleted"));
        Purged = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.Purged"));
        FactoryMenuTitle = T3SL4Factory.chatcolor(manager.config.getConfig().getString("GUI.Title"));
        FactoryMenuOwnerLore = manager.config.getConfig().getStringList("GUI.BlockGUI.Owner.OwnerLore");
        FactoryMenuPlayerLore = manager.config.getConfig().getStringList("GUI.BlockGUI.Player.PlayerLore");
        SorgulaCommandERR = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.SorgulaCommandERR"));
        StatsCommandERR = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.StatsCommandERR"));
        ConsoleError = Prefix + T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.ConsoleError"));
        FactoryMenuBlockOwnerLore = manager.config.getConfig().getStringList("GUI.CommandGUI.OwnerLore");
        FactoryMenuBlockPlayerLore = manager.config.getConfig().getStringList("GUI.CommandGUI.PlayerLore");
        FactoryDropItem = manager.config.getConfig().getString("FactoryItem.Specs.DropItem");
        FactoryMenuBlockOwnerItemName = T3SL4Factory.chatcolor(manager.config.getConfig().getString("GUI.BlockGUI.Owner.OwnerItemName"));
        FactoryMenuBlockPlayerItemName = T3SL4Factory.chatcolor(manager.config.getConfig().getString("GUI.BlockGUI.Player.PlayerItemName"));
        FactoryMenuBlockPlayerCommand = manager.config.getConfig().getString("GUI.BlockGUI.Player.PlayerItemCommand");
        FactoryMenuBlockPlayerTitle = T3SL4Factory.chatcolor(manager.config.getConfig().getString("GUI.BlockGUI.Player.Title"));
        FactoryMenuBlockOwnerTitle = T3SL4Factory.chatcolor(manager.config.getConfig().getString("GUI.BlockGUI.Owner.Title"));
        DonusturmeCommandERR = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.CommandErrors.DonusturmeCommandERR"));
        NewMaterialError = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.NewMaterialError"));
        NoFactory = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.NoFactory"));
        AlreadyEqualsMaterial = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Errors.AlreadyEqualsMaterial"));
        ReplacedAllFactories = T3SL4Factory.chatcolor(manager.config.getConfig().getString("Success.ReplacedAllFactories"));
    }
}
