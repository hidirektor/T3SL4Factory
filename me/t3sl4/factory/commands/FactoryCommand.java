package me.t3sl4.factory.commands;

import me.t3sl4.factory.item.CustomItem;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class FactoryCommand implements CommandExecutor {
    SettingsManager manager = SettingsManager.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("factory")) {
            if(args.length == 0) {
                if(commandSender instanceof Player) {
                    Player komutGonderen = (Player)commandSender;
                    if(komutGonderen.isOp() || komutGonderen.hasPermission("t3sl4factory.general")) {
                        for(String line : MessageUtil.OfficialInfoLines) {
                            komutGonderen.sendMessage(line);
                        }
                    } else {
                        for(String line : MessageUtil.PlayerInfoLines) {
                            komutGonderen.sendMessage(line);
                        }
                    }
                } else {
                    for(String line : MessageUtil.OfficialInfoLines) {
                        commandSender.sendMessage(line);
                    }
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("ver")) {
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.give") || commandSender instanceof ConsoleCommandSender) {
                    if(args.length == 2) {
                        Player gonderilecekOyuncu = Bukkit.getPlayerExact(args[1]);
                        if(gonderilecekOyuncu != null) {
                            gonderilecekOyuncu.getInventory().addItem(manager.factoryItem.getItemStack());
                            commandSender.sendMessage(MessageUtil.GivenItem.replaceAll("%player%", gonderilecekOyuncu.getDisplayName()).replaceAll("%adet%", String.valueOf(1)));
                            gonderilecekOyuncu.sendMessage(MessageUtil.AddedItem.replaceAll("%adet%", String.valueOf(1)));
                            return true;
                        } else {
                            commandSender.sendMessage(MessageUtil.PlayerNotFound.replaceAll("%player%", args[1]));
                        }
                    } else if(args.length == 3) {
                        int gonderilecekAdet = 0;
                        try {
                            gonderilecekAdet = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            commandSender.sendMessage(MessageUtil.MustBeNumber.replaceAll("%deger%", args[1]));
                            return true;
                        }
                        Player gonderilecekOyuncu = Bukkit.getPlayerExact(args[1]);
                        if(gonderilecekOyuncu != null) {
                            for(int i=0; i<gonderilecekAdet; i++) {
                                gonderilecekOyuncu.getInventory().addItem(manager.factoryItem.getItemStack());
                            }
                            commandSender.sendMessage(MessageUtil.GivenItem.replaceAll("%player%", gonderilecekOyuncu.getDisplayName()).replaceAll("%adet%", String.valueOf(gonderilecekAdet)));
                            gonderilecekOyuncu.sendMessage(MessageUtil.AddedItem.replaceAll("%adet%", String.valueOf(gonderilecekAdet)));
                        } else {
                            commandSender.sendMessage(MessageUtil.PlayerNotFound.replaceAll("%player%", args[1]));
                        }
                    } else {
                        commandSender.sendMessage(MessageUtil.GiveCommandERR);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                }
            }
            if(args[0].equalsIgnoreCase("sil")) {

            }
            if(args[0].equalsIgnoreCase("dagit")) {
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.distribute") || commandSender instanceof ConsoleCommandSender) {
                    if(args.length == 1) {
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.getInventory().addItem(manager.factoryItem.getItemStack());
                            p.sendMessage(MessageUtil.AddedItem.replaceAll("%adet%", String.valueOf(1)));
                        }
                        commandSender.sendMessage(MessageUtil.DistributedItem.replaceAll("%adet%", String.valueOf(1)));
                    } else if(args.length == 2) {
                        int gonderilecekAdet = 0;
                        try {
                            gonderilecekAdet = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            commandSender.sendMessage(MessageUtil.MustBeNumber.replaceAll("%deger%", args[1]));
                            return true;
                        }
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            for(int i=0; i<gonderilecekAdet; i++) {
                                p.getInventory().addItem(manager.factoryItem.getItemStack());
                            }
                            p.sendMessage(MessageUtil.AddedItem.replaceAll("%adet%", String.valueOf(1)));
                        }
                        commandSender.sendMessage(MessageUtil.DistributedItem.replaceAll("%adet%", String.valueOf(gonderilecekAdet)));
                    } else {
                        commandSender.sendMessage(MessageUtil.DistributeCommandERR);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                }
            }
            if(args[0].equalsIgnoreCase("sorgula")) {

            }
            if(args[0].equalsIgnoreCase("purge")) {

            }
            if(args[0].equalsIgnoreCase("reload")) {
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.reload") || commandSender instanceof ConsoleCommandSender) {
                    this.manager.config.load();
                    MessageUtil.loadMessages();
                    this.manager.data.load();
                    commandSender.sendMessage(MessageUtil.Reload);
                    return true;
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                    return false;
                }
            }
            if(args[0].equalsIgnoreCase("top10")) {

            }
            if(args[0].equalsIgnoreCase("stats")) {

            }
        }
        return true;
    }
}
