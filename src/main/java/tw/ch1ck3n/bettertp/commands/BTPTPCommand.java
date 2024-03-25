package tw.ch1ck3n.bettertp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.configs.Settings;
import tw.ch1ck3n.bettertp.utils.ChatUtils;
import tw.ch1ck3n.bettertp.utils.ConfigUtils;
import tw.ch1ck3n.bettertp.utils.CustomConfig;

public class BTPTPCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.TP)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.TP.getName());

        Settings settings = BetterTP.INSTANCE.getSettings();
        Messages messages = BetterTP.INSTANCE.getMessages();
        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name, worldName;
//        Bukkit.getConsoleSender().sendMessage(args[1]);

        if (args[1].contains(".")) {
            name = args[1].split("\\.")[1];
            worldName = args[1].split("\\.")[0];
        } else {
            name = args[1];
            worldName = world.getName();
        }

        CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), worldName + ".yml");
        if (!cc.exists()) cc.create();
        cc.reload();
        FileConfiguration config = cc.load();

        if (config.contains("anchor" + "." + name)) {
            Location anchor = ConfigUtils.getAnchor(config, name);
            if (!player.getWorld().equals(anchor.getWorld())) {
                if (settings.teleportCrossWorld && sender.hasPermission(Permissions.TP_CROSS_WORLD)) {
                    if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                        player.teleport(anchor);
                        return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                    } else {
                        if (ConfigUtils.getPublic(config, name).equals("Private")) {
                            if (sender.hasPermission(Permissions.TP_PRIVATE_ANCHOR)) {
                                player.teleport(anchor);
                                return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                            } else {
                                return ChatUtils.sendNoPermissionMessage(sender, Permissions.TP_PRIVATE_ANCHOR.getName());
                            }
                        } else if (ConfigUtils.getPublic(config, name).equals("Public")) {
                            player.teleport(anchor);
                            return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                        }
                    }
                } else if (!settings.teleportCrossWorld) {
                    return ChatUtils.sendMessage(sender, messages.cannotTeleportCrossWorld.replaceAll("%world-name%", worldName + "." + name));
                } else if (!sender.hasPermission(Permissions.TP_CROSS_WORLD)) {
                    return ChatUtils.sendNoPermissionMessage(sender, Permissions.TP_CROSS_WORLD.getName());
                }
            } else {
                if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                    player.teleport(anchor);
                    return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                } else {
                    if (ConfigUtils.getPublic(config, name).equals("Private")) {
                        if (sender.hasPermission(Permissions.TP_PRIVATE_ANCHOR)) {
                            player.teleport(anchor);
                            return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                        } else {
                            return ChatUtils.sendNoPermissionMessage(sender, Permissions.TP_PRIVATE_ANCHOR.getName());
                        }
                    } else if (ConfigUtils.getPublic(config, name).equals("Public")) {
                        player.teleport(anchor);
                        return ChatUtils.sendMessage(sender, messages.playerTeleport.replaceAll("%anchor-name%", name));
                    }
                }
            }
        } else {
            return ChatUtils.sendMessage(sender, messages.anchorNotFound.replaceAll("%anchor-name%", name));
        }

        return true;
    }
}
