package tw.ch1ck3n.bettertp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import tw.ch1ck3n.bettertp.BetterTP;
import tw.ch1ck3n.bettertp.configs.Permissions;
import tw.ch1ck3n.bettertp.configs.Settings;
import tw.ch1ck3n.bettertp.utils.ConfigUtils;
import tw.ch1ck3n.bettertp.utils.CustomConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BTPCmdTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (commandSender instanceof Player) {
            if (command.getName().equalsIgnoreCase("btp")) {
                if (strings.length == 1) {
                    String cmds[] = {"add", "help", "list", "remove", "set", "tp"};
                    Permission perms[] = {Permissions.ADD, Permissions.HELP, Permissions.LIST, Permissions.REMOVE, Permissions.SET, Permissions.TP};

                    for (int i = 0; i < perms.length; i++) {
                        Permission perm = perms[i];
                        if(commandSender.hasPermission(perm)) list.add(cmds[i]);
                    }
                    return list;
                } else if (strings.length == 2) {
                    if (strings[0].equals("list")) {
                        Settings settings = BetterTP.INSTANCE.getSettings();
                        if (settings.teleportCrossWorld && commandSender.hasPermission(Permissions.TP_CROSS_WORLD)) {
                            for (World w : Bukkit.getWorlds()) {
                                list.add(w.getName());
                            }
                        }
                        return list;
                    } else if (strings[0].equals("remove")) {
                        for (World w : Bukkit.getWorlds()) {
                            CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), w.getName() + ".yml");
                            if (!cc.exists()) continue;
                            FileConfiguration config = cc.load();

                            Set<String> anchors = config.getConfigurationSection("anchor" + ".").getKeys(false);
                            if (anchors == null) continue;

                            for (String anchor: anchors) {
                                if (ConfigUtils.getAuthor(config, anchor).equals(commandSender.getName())) {
                                    list.add(w.getName() + "." + anchor);
                                } else if (!ConfigUtils.getAuthor(config, anchor).equals(commandSender.getName()) && commandSender.hasPermission(Permissions.FORCE_REMOVE)) {
                                    list.add(w.getName() + "." + anchor);
                                }
                            }
                        }
                        return list;
                    } else if (strings[0].equals("set")) {
                        for (World w : Bukkit.getWorlds()) {
                            CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), w.getName() + ".yml");
                            if (!cc.exists()) continue;
                            FileConfiguration config = cc.load();

                            Set<String> anchors = config.getConfigurationSection("anchor" + ".").getKeys(false);
                            if (anchors == null) continue;

                            for (String anchor: anchors) {
                                if (ConfigUtils.getAuthor(config, anchor).equals(commandSender.getName())) {
                                    list.add(w.getName() + "." + anchor);
                                } else if (!ConfigUtils.getAuthor(config, anchor).equals(commandSender.getName()) && commandSender.hasPermission(Permissions.FORCE_SET)) {
                                    list.add(w.getName() + "." + anchor);
                                }
                            }
                        }
                        return list;
                    } else if (strings[0].equals("tp")) {
                        Settings settings = BetterTP.INSTANCE.getSettings();
                        Player player = ((Player) commandSender);
                        for (World w : Bukkit.getWorlds()) {
                            CustomConfig cc = new CustomConfig(BetterTP.INSTANCE.getDataFolder(), w.getName() + ".yml");
                            if (!cc.exists()) continue;
                            FileConfiguration config = cc.load();

                            Set<String> anchors = config.getConfigurationSection("anchor" + ".").getKeys(false);
                            if (anchors == null) continue;

                            for (String name: anchors) {
                                Location anchor = ConfigUtils.getAnchor(config, name);
                                if (!player.getWorld().equals(anchor.getWorld())) {
                                    if (settings.teleportCrossWorld && commandSender.hasPermission(Permissions.TP_CROSS_WORLD)) {
                                        if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                                            list.add(w.getName() + "." + name);
                                        } else {
                                            if (commandSender.hasPermission(Permissions.TP_PRIVATE_ANCHOR)) {
                                                list.add(w.getName() + "." + name);
                                            }
                                        }
                                    }
                                } else {
                                    if (player.getName().equals(ConfigUtils.getAuthor(config, name))) {
                                        list.add(w.getName() + "." + name);
                                    } else {
                                        if (commandSender.hasPermission(Permissions.TP_CROSS_WORLD)) {
                                            list.add(w.getName() + "." + name);
                                        }
                                    }
                                }
                            }
                        }
                        return list;
                    }
                }
            }
        }
        return list;
    }
}
