package me.t3sl4.factory.menu;

import java.util.ArrayList;
import java.util.Iterator;

import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import me.t3sl4.factory.util.XMaterial;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerGUIManager {
    Inventory inv;
    static SettingsManager manager = SettingsManager.getInstance();
    Material mat = XMaterial.SKELETON_SKULL.parseMaterial();

    public PlayerGUIManager(Player envanterAcilacakOyuncu, Player sorgulanacakOyuncu) {
        this.inv = Bukkit.createInventory((InventoryHolder)null, InventoryType.HOPPER, MessageUtil.FactoryMenuTitle);
        ItemStack skull = new ItemStack(mat, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwner(sorgulanacakOyuncu.getName());
        meta.setDisplayName(ChatColor.AQUA.toString() + sorgulanacakOyuncu.getName());
        ArrayList<String> outLore = new ArrayList();
        Iterator itemLoreIterator;
        if(manager.data.getConfig().getConfigurationSection(sorgulanacakOyuncu.getUniqueId().toString()) != null) {
            itemLoreIterator = MessageUtil.FactoryMenuOwnerLore.iterator();
        } else {
            itemLoreIterator = MessageUtil.FactoryMenuPlayerLore.iterator();
        }

        while(itemLoreIterator.hasNext()) {
            String s = (String)itemLoreIterator.next();
            if(manager.data.getConfig().getConfigurationSection(sorgulanacakOyuncu.getUniqueId().toString()) != null) {
                outLore.add(T3SL4Factory.chatcolor(s.replaceAll("%player%", sorgulanacakOyuncu.getDisplayName()).replaceAll("%toplamadet%", manager.data.getConfig().getString(sorgulanacakOyuncu.getUniqueId() + ".FactoryCount"))));
            } else {
                outLore.add(T3SL4Factory.chatcolor(s));
            }
        }

        meta.setLore(outLore);
        skull.setItemMeta(meta);
        this.inv.setItem(2, skull);
        envanterAcilacakOyuncu.openInventory(this.inv);
    }
}
