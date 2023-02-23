package me.t3sl4.factory.menu;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import me.t3sl4.factory.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockGUIManager {
    Inventory inv;
    static SettingsManager manager = SettingsManager.getInstance();
    Material mat = XMaterial.SKELETON_SKULL.parseMaterial();

    public BlockGUIManager(Player tiklayanOyuncu, int id, boolean sahiplik, String menuTitle) {
        this.inv = Bukkit.createInventory((InventoryHolder)null, InventoryType.HOPPER, menuTitle);
        ItemStack skull = new ItemStack(mat, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwner(tiklayanOyuncu.getName());
        ArrayList<String> outLore = new ArrayList();
        Iterator itemLoreIterator;
        if(sahiplik) {
            itemLoreIterator = MessageUtil.FactoryMenuBlockOwnerLore.iterator();
        } else {
            itemLoreIterator = MessageUtil.FactoryMenuBlockPlayerLore.iterator();
        }

        //TODO
        //yükseltme sistemi !!!
        while(itemLoreIterator.hasNext()) {
            String s = (String)itemLoreIterator.next();
            if(sahiplik) {
                meta.setDisplayName(MessageUtil.FactoryMenuBlockOwnerItemName.replaceAll("%player%", tiklayanOyuncu.getName()));
                outLore.add(T3SL4Factory.chatcolor(s.replaceAll("%id%", String.valueOf(id)).replaceAll("%toplamadet%", manager.data.getConfig().getString(tiklayanOyuncu.getUniqueId() + ".FactoryCount")).replaceAll("%level%", String.valueOf(manager.data.getConfig().getInt(tiklayanOyuncu.getUniqueId() + ".Factories." + id + ".Level")))));
            } else {
                meta.setDisplayName(MessageUtil.FactoryMenuBlockPlayerItemName.replaceAll("%player%", FactoryAPI.findNameByID(id)));
                outLore.add(T3SL4Factory.chatcolor(s.replaceAll("%player%", FactoryAPI.findNameByID(id))));
            }
        }

        meta.setLore(outLore);
        skull.setItemMeta(meta);
        this.inv.setItem(2, skull);
        tiklayanOyuncu.openInventory(this.inv);
    }
}
