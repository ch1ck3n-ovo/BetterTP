package tw.ch1ck3n.bettertp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.configs.Settings;
import tw.ch1ck3n.bettertp.utils.ChatUtils;
import tw.ch1ck3n.bettertp.utils.CustomPlayer;

public class BTPMainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Settings settings = BetterTP.INSTANCE.getSettings();
        Messages messages = BetterTP.INSTANCE.getMessages();
        if (!(sender instanceof Player)) {
            if (settings.onlyExecuteByPlayers) {
                Bukkit.getConsoleSender().sendMessage(messages.cannotExecuteInConsole);
                return true;
            }
        }

        CustomPlayer info = BetterTP.INSTANCE.getPlayerManager().getPlayer(((Player) sender).getPlayer());
        long coolDown = settings.minCommandCoolDown;
        if (!sender.hasPermission(Permissions.IGNORE_CD)) {
            if (!info.hasTimePassed(coolDown))
                return ChatUtils.sendMessage(sender, messages.commandInCoolDown.
                        replace("%cool-down-left%", String.format("%.1f", ((float) info.getCoolDownLeft(coolDown)) / 1000f)));
            BetterTP.INSTANCE.getPlayerManager().getPlayer(((Player) sender).getPlayer()).setLastCommandTime(System.currentTimeMillis());
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                return BTPListCommand.onCommand(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase("help")) {
                return BTPHelpCommand.onCommand(sender, command, s, args);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                return BTPAddCommand.onCommand(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase("list")) {
                return BTPListCommand.onCommand(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase("remove")) {
                return BTPRemoveCommand.onCommand(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase("set")) {
                return BTPSetCommand.onCommand(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase("tp")) {
                return BTPTPCommand.onCommand(sender, command, s, args);
            } else {
                return ChatUtils.sendMessage(sender, messages.invalidCommand.replaceAll("%command%", args[0]));
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                return BTPAddCommand.onCommand(sender, command, s, args);
            }
        } else {
            return BTPHelpCommand.onCommand(sender, command, s, args);
        }

        return true;
    }

}
