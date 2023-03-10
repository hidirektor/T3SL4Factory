package me.t3sl4.factory.listener;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import me.t3sl4.factory.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String invTitle = e.getView().getTitle();
        Material mat = XMaterial.SKELETON_SKULL.parseMaterial();
        if(inv == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName() || e.getCurrentItem() == null || e.getCurrentItem().getType() != mat || e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);
        if(invTitle.contains(MessageUtil.FactoryMenuBlockOwnerTitle)) {
            if(e.getCurrentItem().getType().equals(mat)) {
                p.closeInventory();
            }
        }
        if(invTitle.contains(MessageUtil.FactoryMenuBlockPlayerTitle)) {
            if(e.getCurrentItem().getType().equals(mat)) {
                p.closeInventory();
                if(MessageUtil.FactoryMenuBlockPlayerCommand != null) {
                    Bukkit.dispatchCommand(p, MessageUtil.FactoryMenuBlockPlayerCommand);
                }
            }
        }
    }
}
