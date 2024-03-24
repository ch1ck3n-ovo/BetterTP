package tw.ch1ck3n.bettertp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.utils.ChatUtils;

public class BTPHelpCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.HELP)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.HELP.getName());

        Messages messages = BetterTP.INSTANCE.getMessages();
        for (String m: messages.helpStrings) ChatUtils.sendMessageWithoutPrefix(sender, m);
        return true;
    }
}
