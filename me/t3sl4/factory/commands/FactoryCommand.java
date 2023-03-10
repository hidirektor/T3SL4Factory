package me.t3sl4.factory.commands;

import me.t3sl4.factory.FactoryAPI;
import me.t3sl4.factory.menu.CommandGUIManager;
import me.t3sl4.factory.util.MessageUtil;
import me.t3sl4.factory.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                            commandSender.sendMessage(MessageUtil.GivenItem.replaceAll("%player%", gonderilecekOyuncu.getName()).replaceAll("%adet%", String.valueOf(1)));
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
                            commandSender.sendMessage(MessageUtil.GivenItem.replaceAll("%player%", gonderilecekOyuncu.getName()).replaceAll("%adet%", String.valueOf(gonderilecekAdet)));
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
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.delete") || commandSender instanceof ConsoleCommandSender) {
                    if(args.length == 2) {
                        Player silinecekOyuncu = Bukkit.getPlayerExact(args[1]);
                        if(silinecekOyuncu != null) {
                            String uuid = silinecekOyuncu.getUniqueId().toString();
                            if(manager.data.getConfigurationSection(uuid) != null) {
                                int factoryCount = manager.data.getConfig().getInt(uuid + ".FactoryCount");
                                for(int i=0; i<factoryCount; i++) {
                                    String worldName = manager.data.getConfig().getString(uuid + ".Factories." + i + ".World");
                                    int X = manager.data.getConfig().getInt(uuid + ".Factories." + i + ".X");
                                    int Y = manager.data.getConfig().getInt(uuid + ".Factories." + i + ".Y");
                                    int Z = manager.data.getConfig().getInt(uuid + ".Factories." + i + ".Z");
                                    int ID = manager.data.getConfig().getInt(uuid + ".Factories." + i + ".ID");
                                    FactoryAPI.endTask(silinecekOyuncu, i);
                                    //manager.PlacedFactories.remove(ID);
                                    Location removingLoc = new Location(Bukkit.getWorld(worldName), X,Y,Z);
                                    Bukkit.getWorld(worldName).getBlockAt(removingLoc).setType(Material.AIR);
                                }
                                //FactoryAPI.update(1);
                                manager.data.getConfig().set(uuid, null);
                                manager.data.save();
                                commandSender.sendMessage(MessageUtil.Deleted.replaceAll("%player%", silinecekOyuncu.getName()).replaceAll("%adet%", String.valueOf(factoryCount)));
                            } else {
                                commandSender.sendMessage(MessageUtil.NullFactoryError.replaceAll("%player%", silinecekOyuncu.getName()));
                            }
                        } else {
                            commandSender.sendMessage(MessageUtil.PlayerNotFound.replaceAll("%player%", args[1]));
                        }
                    } else {
                        commandSender.sendMessage(MessageUtil.DeleteError);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                }
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
                if(commandSender instanceof Player) {
                    Player komutGiren = (Player)commandSender;
                    if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.sorgula")) {
                        if(args.length == 2) {
                            Player sorgulanacakOyuncu = Bukkit.getPlayerExact(args[1]);
                            if(sorgulanacakOyuncu != null) {
                                new CommandGUIManager(komutGiren, sorgulanacakOyuncu);
                            } else {
                                komutGiren.sendMessage(MessageUtil.PlayerNotFound.replaceAll("%player%", args[1]));
                            }
                        } else {
                            komutGiren.sendMessage(MessageUtil.SorgulaCommandERR);
                        }
                    } else {
                        komutGiren.sendMessage(MessageUtil.PermissionError);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.ConsoleError);
                }
            }
            if(args[0].equalsIgnoreCase("purge")) {
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.purge") || commandSender instanceof ConsoleCommandSender) {
                    if(args.length == 1) {
                        int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
                        int totalFactoryCount = 0;
                        for(int i=0; i<playerCount; i++) {
                            String playerUUID = manager.playerdata.getConfig().getString("Players.Players." + i + ".UUID");
                            int playerFactoryCount = manager.data.getConfig().getInt(playerUUID + ".FactoryCount");
                            totalFactoryCount += playerFactoryCount;
                            for(int j=0; j<playerFactoryCount; j++) {
                                String worldName = manager.data.getConfig().getString(playerUUID + ".Factories." + j + ".World");
                                int X = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".X");
                                int Y = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".Y");
                                int Z = manager.data.getConfig().getInt(playerUUID + ".Factories." + j + ".Z");
                                Location removingLoc = new Location(Bukkit.getWorld(worldName), X, Y, Z);
                                removingLoc.getBlock().setType(Material.AIR);
                            }
                            manager.playerdata.getConfig().set("Players.Count", 0);
                            manager.playerdata.save();
                            manager.playerdata.getConfig().set("Players.Players." + i, null);
                            manager.playerdata.save();
                            manager.data.getConfig().set(playerUUID, null);
                            manager.data.save();
                        }
                        commandSender.sendMessage(MessageUtil.Purged.replaceAll("%toplamoyuncu%", String.valueOf(playerCount)).replaceAll("%adet%", String.valueOf(totalFactoryCount)));
                        FactoryAPI.stopAllTasks();
                    } else {
                        commandSender.sendMessage(MessageUtil.PurgeCommandERR);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                }
            }
            if(args[0].equalsIgnoreCase("stats")) {
                if(commandSender instanceof Player) {
                    Player komutGonderen = (Player)commandSender;
                    if(komutGonderen.isOp() || komutGonderen.hasPermission("t3sl4factory.stats")) {
                        if(args.length == 1) {
                            new CommandGUIManager(komutGonderen, komutGonderen);
                        } else {
                            komutGonderen.sendMessage(MessageUtil.StatsCommandERR);
                        }
                    } else {
                        komutGonderen.sendMessage(MessageUtil.PermissionError);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.ConsoleError);
                }
            }
            if(args[0].equalsIgnoreCase("donustur")) {
                if(commandSender instanceof ConsoleCommandSender) {
                    commandSender.sendMessage(MessageUtil.ConsoleError);
                    return false;
                }
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.donustur")) {
                    if(args.length == 2) {
                        ItemStack newItemStack = new ItemStack(Material.getMaterial(args[1]));
                        if(newItemStack != null) {
                            if(manager.playerdata.getConfig().getInt("Players.Count") != 0) {
                                String checkUUID = manager.playerdata.getConfig().getString("Players.Players." + 0 + ".UUID");
                                String worldName = manager.data.getConfig().getString(checkUUID + ".Factories." + 0 + ".World");
                                int checkX = manager.data.getConfig().getInt(checkUUID + ".Factories." + 0 + ".X");
                                int checkY = manager.data.getConfig().getInt(checkUUID + ".Factories." + 0 + ".Y");
                                int checkZ = manager.data.getConfig().getInt(checkUUID + ".Factories." + 0 + ".Z");
                                Location checkLoc = new Location(Bukkit.getWorld(worldName), checkX, checkY, checkZ);
                                ItemStack checkItemStack = new ItemStack(checkLoc.getBlock().getType());
                                if(!newItemStack.equals(checkItemStack)) {
                                    FactoryAPI.changeFactoryBlock(newItemStack, (Player)commandSender);
                                    /*int degisenBlokSayisi = 0;
                                    int playerCount = manager.playerdata.getConfig().getInt("Players.Count");
                                    for(int i=0; i<playerCount; i++) {
                                        String UUID = manager.playerdata.getConfig().getString("Players." + i + ".UUID");
                                        int playerFactoryCount = manager.data.getConfig().getInt(UUID + ".FactoryCount");
                                        for(int j=0; j<playerFactoryCount; j++) {
                                            String replaceWorld = manager.data.getConfig().getString(UUID + ".Factories." + j + ".World");
                                            int replaceX = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".X");
                                            int replaceY = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".Y");
                                            int replaceZ = manager.data.getConfig().getInt(UUID + ".Factories." + j + ".Z");
                                            Location replaceLoc = new Location(Bukkit.getWorld(replaceWorld), replaceX, replaceY, replaceZ);
                                            replaceLoc.getBlock().setType(checkItemStack.getType());
                                            degisenBlokSayisi++;
                                        }
                                    }*/

                                } else {
                                    commandSender.sendMessage(MessageUtil.AlreadyEqualsMaterial);
                                }
                            } else {
                                commandSender.sendMessage(MessageUtil.NoFactory);
                            }
                        } else {
                            commandSender.sendMessage(MessageUtil.NewMaterialError);
                        }
                    } else {
                        commandSender.sendMessage(MessageUtil.DonusturmeCommandERR);
                    }
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                }
            }
            if(args[0].equalsIgnoreCase("reload")) {
                if(commandSender.isOp() || commandSender.hasPermission("t3sl4factory.reload") || commandSender instanceof ConsoleCommandSender) {
                    this.manager.config.load();
                    manager.loadCustomItem();
                    MessageUtil.loadMessages();
                    this.manager.data.load();
                    this.manager.playerdata.load();
                    FactoryAPI.stopAllTasks();
                    FactoryAPI.update(1);
                    commandSender.sendMessage(MessageUtil.Reload);
                    return true;
                } else {
                    commandSender.sendMessage(MessageUtil.PermissionError);
                    return false;
                }
            }
        }
        return true;
    }
}
