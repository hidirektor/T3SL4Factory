package me.t3sl4.factory.listener;

import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void onFactoryPlace(BlockPlaceEvent e) {
        Player blokKoyan = e.getPlayer();
        ItemStack koyulanBlokStack = blokKoyan.getItemInHand();
        ItemMeta koyulanBlokMeta = koyulanBlokStack.getItemMeta();
        if(koyulanBlokMeta != null && koyulanBlokMeta.getDisplayName().equals(MessageUtil.FactoryItemName) && koyulanBlokMeta.getLore().equals(MessageUtil.FactoryItemLore)) {
            if(MessageUtil.WorldSystem) {
                if(MessageUtil.AvailableWorlds.contains(blokKoyan.getWorld().getName())) {
                    if(manager.data.getConfig().getConfigurationSection(String.valueOf(blokKoyan.getUniqueId())) == null) {
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Name", blokKoyan.getDisplayName());
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", 1);
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".ID", manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1);
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".X", e.getBlockPlaced().getX());
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Y", e.getBlockPlaced().getY());
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Z", e.getBlockPlaced().getZ());
                        manager.data.save();
                    } else {
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")+1);
                        manager.data.save();
                        int factoryCount = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount");
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".ID", (factoryCount-1));
                        manager.data.save();
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".X", e.getBlockPlaced().getX());
                        manager.data.save();
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Y", e.getBlockPlaced().getY());
                        manager.data.save();
                        manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Z", e.getBlockPlaced().getZ());
                        manager.data.save();
                    }
                } else {
                    e.setCancelled(true);
                    blokKoyan.sendMessage(MessageUtil.WorldError.replaceAll("%dunya%", blokKoyan.getWorld().getName()));
                }
            } else {
                if(manager.data.getConfig().getConfigurationSection(String.valueOf(blokKoyan.getUniqueId())) == null) {
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Name", blokKoyan.getDisplayName());
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", 1);
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".ID", manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1);
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".X", e.getBlockPlaced().getX());
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Y", e.getBlockPlaced().getY());
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")-1) + ".Z", e.getBlockPlaced().getZ());
                    manager.data.save();
                } else {
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".FactoryCount", manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount")+1);
                    manager.data.save();
                    int factoryCount = manager.data.getConfig().getInt(blokKoyan.getUniqueId() + ".FactoryCount");
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".ID", (factoryCount-1));
                    manager.data.save();
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".X", e.getBlockPlaced().getX());
                    manager.data.save();
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Y", e.getBlockPlaced().getY());
                    manager.data.save();
                    manager.data.getConfig().set(blokKoyan.getUniqueId() + ".Factories." + (factoryCount-1) + ".Z", e.getBlockPlaced().getZ());
                    manager.data.save();
                }
            }
        }
    }
}
