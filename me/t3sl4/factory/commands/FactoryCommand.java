package me.t3sl4.factory.commands;

import me.t3sl4.factory.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("factory")) {
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
        }
        return true;
    }
}
