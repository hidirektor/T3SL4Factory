package me.t3sl4.factory.listener;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.menu.BlockGUIManager;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();
    @EventHandler
    public void onRightClickOnFactory(PlayerInteractEvent e) {
        Player tiklayanOyuncu = e.getPlayer();
        Block tiklananBlok = e.getClickedBlock();
        Action tiklamaHareketi = e.getAction();
        String uuid = tiklayanOyuncu.getUniqueId().toString();
        int tiklananID = 0;
        if(tiklamaHareketi.equals(Action.RIGHT_CLICK_BLOCK) && !e.isBlockInHand()) {
            if(manager.data.getConfigurationSection(uuid) != null) {
                int playerFactoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
                int tiklananX = tiklananBlok.getX();
                int tiklananY = tiklananBlok.getY();
                int tiklananZ = tiklananBlok.getZ();
                for(int i=0; i<playerFactoryCount; i++) {
                    if(manager.data.getConfig().getInt(uuid + ".Factories." + i + ".X") == tiklananX &&
                            manager.data.getConfig().getInt(uuid + ".Factories." + i + ".Y") == tiklananY &&
                            manager.data.getConfig().getInt(uuid + ".Factories." + i + ".Z") == tiklananZ) {
                        tiklananID = i;
                    }
                }
                new BlockGUIManager(tiklayanOyuncu, tiklananID, true, tiklayanOyuncu.getDisplayName());
            } else {
                new BlockGUIManager(tiklayanOyuncu, tiklananID, false, FactoryAPI.findByLoc(tiklananBlok.getLocation(), tiklayanOyuncu));
            }
        }
    }
}
