package tw.ch1ck3n.bettertp.utils;

import org.bukkit.command.CommandSender;
import tw.ch1ck3n.bettertp.BetterTP;

public class ChatUtils {

    public static boolean sendMessage(CommandSender sender, String message) {
        sender.sendMessage(
                BetterTP.INSTANCE.getMessages().prefix + " " + message
        );
        return true;
    }

    public static boolean sendMessageWithoutPrefix(CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

    public static boolean sendNoPermissionMessage(CommandSender sender, String permission) {
        sender.sendMessage(
                BetterTP.INSTANCE.getMessages().prefix + " " +
                BetterTP.INSTANCE.getMessages().noCommandPermission.replace("%permission%", permission)
        );
        return true;
    }
}
