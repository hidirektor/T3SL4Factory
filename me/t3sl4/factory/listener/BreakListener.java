package me.t3sl4.factory.listener;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class BreakListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();
    @EventHandler
    public void onFactoryBreak(BlockBreakEvent e) {
        Player blokKiran = e.getPlayer();
        if(manager.data.getConfig().getConfigurationSection(String.valueOf(blokKiran.getUniqueId())) != null) {
            int kirilanX = e.getBlock().getX();
            int kirilanY = e.getBlock().getY();
            int kirilanZ = e.getBlock().getZ();
            int factoryCount = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount");
            for(int i=0; i<factoryCount; i++) {
                int kontrolX = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + i + ".X");
                int kontrolY = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + i + ".Y");
                int kontrolZ = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + i + ".Z");
                if(kirilanX == kontrolX && kirilanY == kontrolY && kirilanZ == kontrolZ) {
                    e.getBlock().breakNaturally(new ItemStack(Material.AIR, 1));
                    if(i == factoryCount-1 && factoryCount == 1) {
                        FactoryAPI.endTask(0, blokKiran, 0);
                        manager.data.getConfig().set(String.valueOf(blokKiran.getUniqueId()), null);
                        manager.data.save();
                        blokKiran.getInventory().addItem(manager.factoryItem.getItemStack());
                    } else if(i == factoryCount-1) {
                        int factoryCountTemp = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount");
                        FactoryAPI.endTask(1, blokKiran, (factoryCountTemp-1));
                        manager.data.getConfig().set(blokKiran.getUniqueId() + ".FactoryCount", manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount")-1);
                        manager.data.save();
                        manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + i, null);
                        manager.data.save();
                        blokKiran.getInventory().addItem(manager.factoryItem.getItemStack());
                    } else {
                        FactoryAPI.endTask(2, blokKiran, i);
                        manager.data.getConfig().set(blokKiran.getUniqueId() + ".FactoryCount", manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount")-1);
                        manager.data.save();
                        manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + i, null);
                        manager.data.save();
                        for(int j=i+1; j<=manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount")+1; j++) {
                            int ID = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + j + ".ID");
                            String worldName = manager.data.getConfig().getString(blokKiran.getUniqueId() + ".Factories." + j + ".World");
                            int X = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + j + ".X");
                            int Y = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + j + ".Y");
                            int Z = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + j + ".Z");
                            int realPos = j-1;
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos, null);
                            manager.data.save();
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos + ".ID", ID);
                            manager.data.save();
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos + ".World", worldName);
                            manager.data.save();
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos + ".X", X);
                            manager.data.save();
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos + ".Y", Y);
                            manager.data.save();
                            manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + realPos + ".Z", Z);
                            manager.data.save();
                        }
                        int removingSectionID = manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".FactoryCount");
                        manager.data.getConfig().set(blokKiran.getUniqueId() + ".Factories." + removingSectionID, null);
                        manager.data.save();
                        blokKiran.getInventory().addItem(manager.factoryItem.getItemStack());
                    }
                }
            }
        }
    }
}
