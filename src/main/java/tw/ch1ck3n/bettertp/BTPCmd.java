package tw.ch1ck3n.bettertp;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BTPCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if (this.getBooleanFromConfig("settings.only-player")) {
                return this.sendMessage(sender, this.getMessageStringFromConfig("messages.caution-for-console"));
            }
        }

        PlayerInfo info = this.getPlayerInfo(sender);
        long cd = this.getLongFromConfig("settings.command-cd");
        if (!info.canCommand(cd)) {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.command.cool-down").replace("%cd-left%", String.format("%.1f", ((float) info.getCDLeft(cd)) / 1000f)));
        }
//        this.sendMessage(sender, this.getMessageStringFromConfig("messages.command.cool-down").replace("%cd-left%", String.format("%.1f", ((float) info.getCDLeft(cd)) / 1000f)));
        this.getPlayerInfo(sender).setLastCommandTime(System.currentTimeMillis());

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.list"))) {
                return this.runList(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.list"))) {
                return this.runList(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.help"))) {
                return this.runHelp(sender, command, s, args);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.add"))) {
                return this.runAdd(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.remove"))) {
                return this.runRemove(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.set"))) {
                return this.runSet(sender, command, s, args);
            } else if (args[0].equalsIgnoreCase(this.getMessageStringFromConfig("commands.teleport"))) {
                return this.runTeleport(sender, command, s, args);
            } else {
                return this.sendMessage(sender, this.getMessageStringFromConfig("messages.command.invalid").replace("%command%", args[0]));
            }
        } else {
            return this.runHelp(sender, command, s, args);
        }

        return true;
    }

    //<editor-fold desc="Run">

    private boolean runList(CommandSender sender, Command command, String s, String[] args) {

        String permission = this.getMessageStringFromConfig("permissions.list");
        if (!this.hasPermission(sender, permission)) {
            return this.sendNoPermissionMessage(sender, permission);
        }

        int count = 0;
        for (World w: Bukkit.getWorlds()) {
            File file = new File(BetterTP.INSTANCE.getDataFolder(), w.getName() + ".yml");
            if (!file.exists()) {
                continue;
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            Set<String> anchors = config.getConfigurationSection("anchor" + ".").getKeys(false);
            if (anchors == null) continue;

            if (!anchors.isEmpty()) {
                this.sendMessageWithoutPrefix(sender, this.getMessageStringFromConfig("messages.anchor.list-title"));
                for (String name: anchors) {
                    Location anchor = this.getAnchor(config, name);
                    String message = " §f+ §a" + name;
                    String XYZ = getMessageStringFromConfig("forms.xyz").
                            replace("%x%", getDouble3AsString(anchor.getX())).
                            replace("%y%", getDouble3AsString(anchor.getY())).
                            replace("%z%", getDouble3AsString(anchor.getZ()));
                    String facing = getMessageStringFromConfig("forms.facing").
                            replace("%yaw%", getFloat1AsString(anchor.getYaw())).
                            replace("%pitch%", getFloat1AsString(anchor.getPitch()));
                    String world = getMessageStringFromConfig("forms.world").
                            replace("%world-name%", anchor.getWorld().getName());
                    TextComponent textComponent = new TextComponent(message);
                    textComponent.setHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder(
                                            getMessageStringFromConfig("messages.anchor.information") + "\n" +
                                            XYZ + "\n" +
                                            facing + "\n" +
                                            world + "\n" + "\n" +
                                            getMessageStringFromConfig("messages.click-to-teleport")
                                    ).create()
                            )
                    );
                    textComponent.setClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/btp " + getMessageStringFromConfig("commands.teleport") + " " + name)
                    );
                    sender.spigot().sendMessage(textComponent);
                    count++;
                }
            }
        }
        if (count == 0) {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.no-found"));
        } else {
            return true;
        }
    }

    private boolean runAdd(CommandSender sender, Command command, String s, String[] args) {

        String permission = this.getMessageStringFromConfig("permissions.add");
        if (!this.hasPermission(sender, permission)) {
            return this.sendNoPermissionMessage(sender, permission);
        }

        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name = args[1];

        File file = new File(BetterTP.INSTANCE.getDataFolder(), world.getName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains("anchor" + "." + name)) {
            config.createSection("anchor" + "." + name);
            this.addAnchor(config, name, player);
            this.saveConfig(config, file);
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.added").replace("%anchor-name%", name));
        } else {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.existed").replace("%anchor-name%", name));
        }
    }

    private boolean runRemove(CommandSender sender, Command command, String s, String[] args) {

        String permission = this.getMessageStringFromConfig("permissions.remove");
        if (!this.hasPermission(sender, permission)) {
            return this.sendNoPermissionMessage(sender, permission);
        }

        World world = ((Player) sender).getWorld();
        String name, worldName;
        if (args[1].contains(".")) {
            name = args[1].split(".")[0];
            worldName = args[1].split(".")[0];
        } else {
            name = args[1];
            worldName = world.getName();
        }

        File file = new File(BetterTP.INSTANCE.getDataFolder(), worldName + ".yml");
        if (!file.exists()) {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.world.not-found").replace("%world-name%", worldName));
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.get("anchor" + "." + name) != null ) {
            config.set("anchor" + "." + name, null);
            this.saveConfig(config, file);
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.removed").replace("%anchor-name%", name));
        } else {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.not-found").replace("%anchor-name%", name));
        }
    }

    private boolean runSet(CommandSender sender, Command command, String s, String[] args) {

        String permission = this.getMessageStringFromConfig("permissions.set");
        if (!this.hasPermission(sender, permission)) {
            return this.sendNoPermissionMessage(sender, permission);
        }

        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name = args[1];

        File file = new File(BetterTP.INSTANCE.getDataFolder(), world.getName() + ".yml");
        if (!file.exists()) {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.world.not-found").replace("%world-name%", world.getName()));
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.get("anchor" + "." + name) != null) {
            this.addAnchor(config, name, player);
            this.saveConfig(config, file);
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.edited").replace("%anchor-name%", name));
        } else {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.not-found").replace("%anchor-name%", name));
        }
    }

    private boolean runTeleport(CommandSender sender, Command command, String s, String[] args) {

        String permission = this.getMessageStringFromConfig("permissions.teleport");
        if (!this.hasPermission(sender, permission)) {
            return this.sendNoPermissionMessage(sender, permission);
        }

        Player player = ((Player) sender).getPlayer();
        World world = ((Player) sender).getWorld();
        String name, worldName;
        if (args[1].contains(".")) {
            name = args[1].split(".")[0];
            worldName = args[1].split(".")[0];
        } else {
            name = args[1];
            worldName = world.getName();
        }

        File file = new File(BetterTP.INSTANCE.getDataFolder(), worldName + ".yml");
        if (!file.exists()) {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.world.not-found").replace("%world-name%", worldName));
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.contains("anchor" + "." + name)) {
            Location anchor = this.getAnchor(config, name);
            if (!player.getWorld().equals(anchor.getWorld()) && this.getBooleanFromConfig("settings.cross-world")) {
                return this.sendMessage(sender, this.getMessageStringFromConfig("messages.player.teleport-cross-world").replace("%world-name%", name));
            } else {
                player.teleport(anchor);
                return this.sendMessage(sender, this.getMessageStringFromConfig("messages.player.teleport").replace("%world-name%", name));
            }
        } else {
            return this.sendMessage(sender, this.getMessageStringFromConfig("messages.anchor.not-found").replace("%anchor-name%", name));
        }
    }

    private boolean runHelp(CommandSender sender, Command command, String s, String[] args) {
        List<String> commands = Arrays.asList("add", "help", "list", "remove", "set", "teleport");
        this.sendMessageWithoutPrefix(sender, this.getMessageStringFromConfig("messages.command.title"));
        for (String cmd: commands) {
            this.sendMessageWithoutPrefix(sender,
                    this.getMessageStringFromConfig("messages.command." + cmd + ".usage").replace("%" + cmd + "%", this.getMessageStringFromConfig("commands." + cmd)) + ": " +
                    this.getMessageStringFromConfig("messages.command." + cmd + ".description")
            );
        }
        return true;
    }

    //</editor-fold>

    //<editor-fold desc="Config">

    private boolean addAnchor(FileConfiguration config, String name, Player player) {
        config.set("anchor" + "." + name + "." + "world", player.getWorld());
        config.set("anchor" + "." + name + "." + "position" + "." + "x", player.getLocation().getX());
        config.set("anchor" + "." + name + "." + "position" + "." + "y", player.getLocation().getY());
        config.set("anchor" + "." + name + "." + "position" + "." + "z", player.getLocation().getZ());
        config.set("anchor" + "." + name + "." + "rotation" + "." + "yaw", player.getLocation().getYaw());
        config.set("anchor" + "." + name + "." + "rotation" + "." + "pitch", player.getLocation().getPitch());
        return true;
    }

    private Location getAnchor(FileConfiguration config, String name) {
        String w = config.getString("anchor" + "." + name + "." + "world");
        double x = config.getDouble("anchor" + "." + name + "." + "position" + ".x");
        double y = config.getDouble("anchor" + "." + name + "." + "position" + ".y");
        double z = config.getDouble("anchor" + "." + name + "." + "position" + ".z");
        float yaw = (float) config.getDouble("anchor" + "." + name + "." + "rotation" + ".yaw");
        float pitch = (float) config.getDouble("anchor" + "." + name + "." + "rotation" + ".pitch");
        Location anchor = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);
        return anchor;
    }

    private boolean saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //</editor-fold>

    //<editor-fold desc="Utility">

    private Boolean getBooleanFromConfig(String path) {
        return BetterTP.INSTANCE.config.getBoolean(path);
    }

    private String getDouble3AsString(double d) {
        return String.format("%.3f", d);
    }

    private String getFloat1AsString(float f) {
        return String.format("%.1f", f);
    }

    private long getLongFromConfig(String path) {
        return BetterTP.INSTANCE.config.getLong(path);
    }

    private String getMessageStringFromConfig(String path) {
        return BetterTP.INSTANCE.config.getString(path).replaceAll("&", "§");
    }

    private PlayerInfo getPlayerInfo(CommandSender sender) {
        for (PlayerInfo p : BetterTP.INSTANCE.playerInfos) {
            if (p.getPlayer() == ((Player) sender).getPlayer()) {
                return p;
            }
        }

        return null;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    private boolean sendMessage(CommandSender sender, String message) {
        sender.sendMessage(
                getMessageStringFromConfig("messages.prefix") + " " + message
        );
        return true;
    }

    private boolean sendMessageWithoutPrefix(CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

    private boolean sendNoPermissionMessage(CommandSender sender, String permission) {
        sender.sendMessage(
                getMessageStringFromConfig("messages.prefix") + " " +
                getMessageStringFromConfig("messages.player.no-permission").replace("%permission%", permission).replaceAll("&", "§")
        );
        return true;
    }

    //</editor-fold>

}
