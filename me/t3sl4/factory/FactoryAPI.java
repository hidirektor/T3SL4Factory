package me.t3sl4.factory;

import me.t3sl4.factory.factory.IFactory;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class FactoryAPI {
    static SettingsManager manager = SettingsManager.getInstance();

    public static String findByLoc(Location blockLoc, Player p) {
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        int checkX = blockLoc.getBlockX();
        int checkY = blockLoc.getBlockY();
        int checkZ = blockLoc.getBlockZ();
        for(int i=0; i<playerCount; i++) {
            String uuid = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                int playerX = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".X");
                int playerY = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Y");
                int playerZ = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Z");
                if(checkX == playerX && checkY == playerY && checkZ == playerZ) {
                    return manager.data.getConfig().getString(uuid + ".Name");
                }
            }
        }
        return null;
    }

    public static boolean checkPlayerFactory(Location blockLoc, Player p) {
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        int checkX = blockLoc.getBlockX();
        int checkY = blockLoc.getBlockY();
        int checkZ = blockLoc.getBlockZ();
        for(int i=0; i<playerCount; i++) {
            String uuid = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                int playerX = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".X");
                int playerY = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Y");
                int playerZ = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Z");
                if(checkX == playerX && checkY == playerY && checkZ == playerZ) {
                    if(manager.data.getConfig().getString(uuid + ".Name").equals(p.getDisplayName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getPlayerCount() {
        return manager.playerdata.getConfig().getInt("Players.Count");
    }

    public static int getPlayerFactoryCount(String uuid) {
        return manager.data.getConfig().getInt(uuid + ".FactoryCount");
    }

    public static int createRandomID() {
        Random serial = new Random();
        return serial.nextInt((999999999 - 1) + 1) + 1;
    }

    public static void update() {
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        for(int i=0; i<playerCount; i++) {
            String playerUUID = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(playerUUID + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                String worldName = manager.data.getConfig().getString(playerUUID + ".Factories." + j + ".World");
                int X = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".X");
                int Y = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".Y");
                int Z = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".Z");
                int factoryLevel = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".Level");
                Location blockLoc = new Location(Bukkit.getWorld(worldName), X, Y, Z);
                BukkitTask task = Bukkit.getScheduler().runTaskTimer(T3SL4Factory.getPlugin(), () -> {
                    ItemStack factoryDropItem = new ItemStack(Material.getMaterial(MessageUtil.FactoryDropItem));
                    if (blockLoc.getWorld() != null) {
                        blockLoc.getWorld().dropItemNaturally(blockLoc, factoryDropItem);
                    }
                }, 0L, (long)factoryLevel * 20L);
            }
        }
        Runnable runnable = () -> {
            for(BukkitTask tempTask : manager.PlacedFactories.values()) {
                Bukkit.getScheduler().runTask(T3SL4Factory.getPlugin(), (Runnable) tempTask);
            }
        };
        Bukkit.getScheduler().runTask(T3SL4Factory.getPlugin(), runnable);
    }

    public static void startTask(Block placedBlock, int factoryLevel, int id) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(T3SL4Factory.getPlugin(), () -> {
            ItemStack factoryDropItem = new ItemStack(Material.getMaterial(MessageUtil.FactoryDropItem));
            Location location = placedBlock.getLocation();
            if (location.getWorld() != null) {
                location.getWorld().dropItemNaturally(location, factoryDropItem);
            }
        }, 0L, (long)factoryLevel * 20L);
        manager.PlacedFactories.put(id, task);
    }

    public static void endTask(int status, Player blokKiran, int place) {
        if(status == 0) {
            BukkitTask endTask = manager.PlacedFactories.get(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
            endTask.cancel();
            manager.PlacedFactories.remove(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
        } else if(status == 1) {
            BukkitTask endTask = manager.PlacedFactories.get(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
            endTask.cancel();
            manager.PlacedFactories.remove(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
        } else {
            BukkitTask endTask = manager.PlacedFactories.get(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
            endTask.cancel();
            manager.PlacedFactories.remove(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
        }
    }

    public static void stopAllTasks() {
        for(BukkitTask task : manager.PlacedFactories.values()) {
            task.cancel();
        }
    }
}
