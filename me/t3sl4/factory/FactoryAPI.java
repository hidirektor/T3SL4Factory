package me.t3sl4.factory;

import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
}
