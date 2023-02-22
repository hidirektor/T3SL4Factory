package me.t3sl4.factory.util.item;

import me.t3sl4.factory.T3SL4Factory;
import me.t3sl4.factory.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {
    int id = 0;
    int data = 0;
    String name = "";
    List<String> lore = new ArrayList<>();
    List<String> enchants = new ArrayList<>();
    boolean SLore = false;
    boolean SEnch = false;

    public CustomItem(int id, int data, String name, List<String> lore, List<String> enchants, boolean SLore, boolean SEnch) {
        this.id = id;
        this.data = data;
        this.name = name;
        this.SLore = SLore;
        this.SEnch = SEnch;
        this.lore = lore;
        this.enchants = enchants;
    }

    public ItemStack getItemStack() {
        if (this.id != 0 && Material.getMaterial(this.id) != null) {
            ItemStack item = new ItemStack(Material.getMaterial(this.id), 1, (short)0, Byte.valueOf((byte)this.data));
            ItemMeta m = item.getItemMeta();
            m.setDisplayName(T3SL4Factory.chatcolor(this.name));
            if(this.SLore) {
                m.setLore(this.lore);
            }
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(m);
            if(this.SEnch) {
                for(String enchantVal : MessageUtil.FactoryItemEnchants) {
                    if(enchantVal != null) {
                        String[] enchantDivided = enchantVal.split(":");
                        String enchantName = enchantDivided[0];
                        int enchantLevel = Integer.parseInt(enchantDivided[1]);
                        item.addUnsafeEnchantment(Enchantment.getByName(enchantName), enchantLevel);
                    }
                }
            }
            return item;
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
    public List<String> getEnchants() {
        return this.enchants;
    }
    public void setEnchants(List<String> enchants) {
        this.enchants = enchants;
    }
}
