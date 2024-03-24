package tw.ch1ck3n.bettertp.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.utils.ChatUtils;
import tw.ch1ck3n.bettertp.utils.ConfigUtils;
import tw.ch1ck3n.bettertp.utils.CustomConfig;
import tw.ch1ck3n.bettertp.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BTPListCommand {

    public static boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.LIST)) return ChatUtils.sendNoPermissionMessage(sender, Permissions.LIST.getName());

        Messages messages = BetterTP.INSTANCE.getMessages();
        int count = 0;
        boolean titleSent = false;
        List<World> worlds = new ArrayList<>();
        if (args.length == 2) {
            World w = Bukkit.getWorld(args[1]);
            if (w == null) return ChatUtils.sendMessageWithoutPrefix(sender, messages.worldNotFound.replaceAll("%world-name%", w.getName()));
            worlds.add(w);
        } else {
            worlds = Bukkit.getWorlds();
        }
        for (World w: worlds) {
            CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), w.getName() + ".yml");
            if (!cc.exists()) continue;
            FileConfiguration config = cc.load();

            Set<String> anchors = config.getConfigurationSection("anchor" + ".").getKeys(false);
            if (anchors == null) continue;

            if (!anchors.isEmpty()) {
                if (!titleSent) {
                    if (args.length == 2) titleSent = ChatUtils.sendMessageWithoutPrefix(sender, messages.anchorListTitle.replaceAll("%world-name%", w.getName()));
                    else titleSent = ChatUtils.sendMessageWithoutPrefix(sender, messages.anchorListTitle.replaceAll("%world-name%", "All"));
                }
                for (String anchorName: anchors) {
                    Location anchor = ConfigUtils.getAnchor(config, anchorName);
                    String message = messages.anchorListForm.replaceAll("%anchor-name%", anchorName);
                    String info[] = messages.informationStrings.toArray(new String[0]);
                    String text = "";
                    for (String i: info) {
                        text = text + i;
                        if (messages.informationStrings.indexOf(i) != messages.informationStrings.size() - 1) text = text + "\n";
                    }
                    text = text.replaceAll("%position-x%", StringUtils.getString3(anchor.getX())).
                            replaceAll("%position-y%", StringUtils.getString3(anchor.getY())).
                            replaceAll("%position-z%", StringUtils.getString3(anchor.getZ())).
                            replaceAll("%rotation-yaw%", StringUtils.getString1(anchor.getYaw())).
                            replaceAll("%rotation-pitch%", StringUtils.getString1(anchor.getPitch())).
                            replaceAll("%world-name%", anchor.getWorld().getName()).
                            replaceAll("%author%", ConfigUtils.getAuthor(config, anchorName)).
                            replaceAll("%access%", ConfigUtils.getPublic(config, anchorName));
                    TextComponent textComponent = new TextComponent(message);
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(text).create()));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/btp tp " + w.getName() + "." + anchorName));
                    sender.spigot().sendMessage(textComponent);
                    count++;
                }
            }
        }
        if (count == 0) {
            return ChatUtils.sendMessage(sender, messages.noAnchorFound);
        } else {
            return true;
        }
    }
}
