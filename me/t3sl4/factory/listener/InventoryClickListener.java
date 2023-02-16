package me.t3sl4.factory.listener;

import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import me.t3sl4.factory.util.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String invTitle = e.getView().getTitle();
        Material mat = XMaterial.SKELETON_SKULL.parseMaterial();
        if (inv != null && invTitle.contains(MessageUtil.FactoryMenuTitle)) {
            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == mat) {
                e.setCancelled(true);
                p.closeInventory();
            }
        }
    }
}
