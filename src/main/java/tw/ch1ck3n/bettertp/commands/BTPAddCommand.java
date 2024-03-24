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

public class BTPAddCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.ADD)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.ADD.getName());

        Messages messages = BetterTP.INSTANCE.getMessages();
        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name = args[1];

        CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), world.getName() + ".yml");
        if (!cc.exists()) cc.create();
        cc.reload();
        FileConfiguration config = cc.load();

        if (!config.contains("anchor" + "." + name)) {
            config.createSection("anchor" + "." + name);
            if (args.length == 3) {
                if (!args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) {
                    return ChatUtils.sendMessage(sender, messages.invalidArgument.replaceAll("%argument%", args[2]));
                } else {
                    ConfigUtils.addAnchor(config, name, player, Boolean.parseBoolean(args[2]));
                }
            } else {
                ConfigUtils.addAnchor(config, name, player);
            }
            cc.save();
            return ChatUtils.sendMessage(sender, messages.anchorAdded.replaceAll("%anchor-name%", name));
        } else {
            return ChatUtils.sendMessage(sender, messages.anchorExisted.replaceAll("%anchor-name%", name));
        }
    }
}
