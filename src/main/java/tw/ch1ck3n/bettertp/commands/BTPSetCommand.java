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

public class BTPSetCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.SET)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.SET.getName());

        Messages messages = BetterTP.INSTANCE.getMessages();
        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name = args[1];

        CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), world.getName() + ".yml");
        if (!cc.exists()) cc.create();
        cc.reload();
        FileConfiguration config = cc.load();

        if (config.get("anchor" + "." + name) != null) {
            if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                ConfigUtils.addAnchor(config, name, player);
                cc.save();
                return ChatUtils.sendMessage(sender, messages.anchorEdited.replaceAll("%anchor-name%", name));
            } else {
                if (sender.hasPermission(Permissions.FORCE_SET)) {
                    ConfigUtils.addAnchor(config, name, player);
                    cc.save();
                    return ChatUtils.sendMessage(sender, messages.anchorEdited.replaceAll("%anchor-name%", name));
                } else {
                    return ChatUtils.sendNoPermissionMessage(sender, Permissions.FORCE_SET.getName());
                }
            }

        } else {
            return ChatUtils.sendMessage(sender, messages.anchorNotFound.replaceAll("%anchor-name%", name));
        }
    }
}
