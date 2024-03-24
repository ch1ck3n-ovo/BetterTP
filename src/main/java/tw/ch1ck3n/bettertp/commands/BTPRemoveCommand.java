package tw.ch1ck3n.bettertp.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.utils.ChatUtils;
import tw.ch1ck3n.bettertp.utils.ConfigUtils;
import tw.ch1ck3n.bettertp.utils.CustomConfig;

public class BTPRemoveCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.REMOVE)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.REMOVE.getName());

        Messages messages = BetterTP.INSTANCE.getMessages();
        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name, worldName;
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

        if (config.get("anchor" + "." + name) != null) {
            if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                config.set("anchor" + "." + name, null);
                cc.save();
                return ChatUtils.sendMessage(sender, messages.anchorRemoved.replaceAll("%anchor-name%", name));
            } else {
                if (sender.hasPermission(Permissions.FORCE_REMOVE)) {
                    config.set("anchor" + "." + name, null);
                    cc.save();
                    return ChatUtils.sendMessage(sender, messages.anchorRemoved.replaceAll("%anchor-name%", name));
                } else {
                    return ChatUtils.sendNoPermissionMessage(sender, Permissions.FORCE_REMOVE.getName());
                }
            }
        } else {
            return ChatUtils.sendMessage(sender, messages.anchorNotFound.replaceAll("%anchor-name%", name));
        }
    }
}
