package me.t3sl4.factory;

import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class FactoryAPI {
    static SettingsManager manager = SettingsManager.getInstance();

    public static int findIDByLoc(Location blockLoc) {
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
                    return manager.data.getConfig().getInt(uuid + ".Factories." + j + ".ID");
                }
            }
        }
        return 0;
    }

    public static String findOwnerByLoc(Location blockLoc) {
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

    public static String findNameByID(int id) {
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        for(int i=0; i<playerCount; i++) {
            String playerUUID = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(playerUUID + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                int ID = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".ID");
                if(id == ID) {
                    return manager.data.getConfig().getString(playerUUID + ".Name");
                }
            }
        }
        return null;
    }

    public static boolean checkLocationFactoryIsNull(Location loc, Player p) {
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        int checkX = loc.getBlockX();
        int checkY = loc.getBlockY();
        int checkZ   = loc.getBlockZ();
        for(int i=0; i<playerCount; i++) {
            String uuid = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                int orgX = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".X");
                int orgY = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Y");
                int orgZ = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Z");
                if(checkX == orgX && checkY == orgY && checkZ == orgZ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void updateFactory(Location blockLoc, int nextLevel, Player clickedPlayer) {
        String UUID = clickedPlayer.getUniqueId().toString();
        int updatedFactoryX = blockLoc.getBlockX();
        int updatedFactoryY = blockLoc.getBlockY();
        int updatedFactoryZ = blockLoc.getBlockZ();
        int playerFactoryCount = manager.data.getConfig().getInt(UUID + ".FactoryCount");
        for(int i=0; i<playerFactoryCount; i++) {
            int checkX = manager.data.getConfig().getInt(UUID + ".Factories." + i + ".X");
            int checkY = manager.data.getConfig().getInt(UUID + ".Factories." + i + ".Y");
            int checkZ = manager.data.getConfig().getInt(UUID + ".Factories." + i + ".Z");
            if(updatedFactoryX == checkX && updatedFactoryY == checkY && updatedFactoryZ == checkZ) {
                manager.data.getConfig().set(UUID + ".Factories." + i + ".Level", nextLevel);
                manager.data.save();
                update(1);
            }
        }
    }

    public static void changeFactoryBlock(ItemStack newMaterial, Player p) {
        stopAllTasks();
        int degisenBlokSayisi = 0;
        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
        for(int i=0; i<playerCount; i++) {
            String UUID = manager.playerdata.getConfig().getString("Players.Player." + i + ".UUID");
            int playerFactoryCount = manager.data.getConfig().getInt(UUID + ".FactoryCount");
            for(int j=0; j<playerFactoryCount; j++) {
                String worldName = manager.data.getConfig().getString(UUID + ".Factories." + j + ".World");
                int checkX = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".X");
                int checkY = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".Y");
                int checkZ = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".Z");
                Location checkLoc = new Location(Bukkit.getWorld(worldName), checkX, checkY, checkZ);
                checkLoc.getBlock().setType(newMaterial.getType());
                degisenBlokSayisi++;
            }
        }
        update(1);
        p.sendMessage(MessageUtil.ReplacedAllFactories.replaceAll("%totalblok%", String.valueOf(degisenBlokSayisi)));
    }

    public static boolean checkOwner(Location blockLoc, Player tiklayanOyuncu) {
        int checkX = blockLoc.getBlockX();
        int checkY = blockLoc.getBlockY();
        int checkZ = blockLoc.getBlockZ();
        String uuid = tiklayanOyuncu.getUniqueId().toString();
        int playerFactoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
        for(int j=0; j<playerFactoryCount; j++) {
            int playerX = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".X");
            int playerY = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Y");
            int playerZ = manager.data.getConfig().getInt(uuid + ".Factories." + j + ".Z");
            if(checkX == playerX && checkY == playerY && checkZ == playerZ) {
                return true;
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

    public static void update(int stat) {
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
                int ID = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".ID");
                Location blockLoc = new Location(Bukkit.getWorld(worldName), X, Y, Z);
                BukkitTask task = Bukkit.getScheduler().runTaskTimer(T3SL4Factory.getPlugin(), () -> {
                    ItemStack factoryDropItem = new ItemStack(Material.getMaterial(MessageUtil.FactoryDropItem));
                    if (blockLoc.getWorld() != null) {
                        blockLoc.getWorld().dropItemNaturally(blockLoc, factoryDropItem);
                    }
                }, 0L, (long)factoryLevel * 20L);
                if(stat == 0) {
                    manager.PlacedFactories.put(ID, task);
                }
            }
        }
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

    public static void endTask(Player blokKiran, int place) {
        BukkitTask endTask = manager.PlacedFactories.get(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
        endTask.cancel();
        manager.PlacedFactories.remove(manager.data.getConfig().getInt(blokKiran.getUniqueId() + ".Factories." + place + ".ID"));
    }

    public static void stopAllTasks() {
        for(BukkitTask task : manager.PlacedFactories.values()) {
            task.cancel();
        }
    }
}
