package me.t3sl4.factory.listener;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlaceListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void onFactoryPlace(BlockPlaceEvent e) {
        Player blokKoyan = e.getPlayer();
        ItemStack koyulanBlokStack = blokKoyan.getItemInHand();
        ItemMeta koyulanBlokMeta = koyulanBlokStack.getItemMeta();
        if(koyulanBlokMeta != null && koyulanBlokMeta.getDisplayName().equals(MessageUtil.FactoryItemName) && koyulanBlokMeta.getLore().equals(MessageUtil.FactoryItemLore)) {
            if(MessageUtil.WorldSystem) {
                if(!MessageUtil.AvailableWorlds.contains(blokKoyan.getWorld().getName())) {
                    e.setCancelled(true);
                    blokKoyan.sendMessage(MessageUtil.WorldError.replaceAll("%dunya%", blokKoyan.getWorld().getName()));
                    return;
                }
            }
            if(manager.data.getConfig().getConfigurationSection(String.valueOf(blokKoyan.getUniqueId())) == null) {
                int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
                manager.playerdata.getConfig().set("Players.Count", playerCount+1);
                manager.playerdata.save();
                manager.playerdata.getConfig().set("Players.Players." + (manager.playerdata.getConfig().getInt("Players.Count")-1) + ".UUID", blokKoyan.getUniqueId().toString());
                manager.playerdata.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Name", blokKoyan.getName());
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", 1);
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".ID", FactoryAPI.createRandomID());
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".World", blokKoyan.getWorld().getName());
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Level", 1);
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".X", e.getBlockPlaced().getX());
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Y", e.getBlockPlaced().getY());
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Z", e.getBlockPlaced().getZ());
                manager.data.save();
                int factoryCount = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount");
                int data = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".ID");
                int factoryLevel = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Level");
                FactoryAPI.startTask(e.getBlockPlaced(), factoryLevel, data);
            } else {
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")+1);
                manager.data.save();
                int factoryCount = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount");
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".ID", FactoryAPI.createRandomID());
                manager.data.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".World", blokKoyan.getWorld().getName());
                manager.data.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Level", 1);
                manager.data.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".X", e.getBlockPlaced().getX());
                manager.data.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Y", e.getBlockPlaced().getY());
                manager.data.save();
                manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Z", e.getBlockPlaced().getZ());
                manager.data.save();
                int data = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".ID");
                int factoryLevel = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Level");
                FactoryAPI.startTask(e.getBlockPlaced(), factoryLevel, data);
            }
        }
    }
}
