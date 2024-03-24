package tw.ch1ck3n.bettertp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigUtils {

    public static boolean addAnchor(FileConfiguration config, String name, Player player) {
        return ConfigUtils.addAnchor(config, name, player, true);
    }

    public static boolean addAnchor(FileConfiguration config, String name, Player player, boolean b) {
        String p = b ? "Public" : "Private";
        config.set("anchor" + "." + name + "." + "world", player.getWorld().getName());
        config.set("anchor" + "." + name + "." + "author", player.getName());
        config.set("anchor" + "." + name + "." + "access", p);
        config.set("anchor" + "." + name + "." + "position" + "." + "x", player.getLocation().getX());
        config.set("anchor" + "." + name + "." + "position" + "." + "y", player.getLocation().getY());
        config.set("anchor" + "." + name + "." + "position" + "." + "z", player.getLocation().getZ());
        config.set("anchor" + "." + name + "." + "rotation" + "." + "yaw", player.getLocation().getYaw());
        config.set("anchor" + "." + name + "." + "rotation" + "." + "pitch", player.getLocation().getPitch());
        return true;
    }

    public static Location getAnchor(FileConfiguration config, String name) {
        String w = config.getString("anchor" + "." + name + "." + "world");
        double x = config.getDouble("anchor" + "." + name + "." + "position" + ".x");
        double y = config.getDouble("anchor" + "." + name + "." + "position" + ".y");
        double z = config.getDouble("anchor" + "." + name + "." + "position" + ".z");
        float yaw = (float) config.getDouble("anchor" + "." + name + "." + "rotation" + ".yaw");
        float pitch = (float) config.getDouble("anchor" + "." + name + "." + "rotation" + ".pitch");
        return new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);
    }

    public static String getAuthor(FileConfiguration config, String name) {
        return config.getString("anchor" + "." + name + "." + "author");
    }

    public static String getPublic(FileConfiguration config, String name) {
        return config.getString("anchor" + "." + name + "." + "access");
    }
}
