package tw.ch1ck3n.bettertp.configs;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public interface Permissions {

    Permission ADD = new org.bukkit.permissions.Permission("btp.add", PermissionDefault.TRUE);

    Permission HELP = new org.bukkit.permissions.Permission("btp.help", PermissionDefault.TRUE);

    Permission LIST = new org.bukkit.permissions.Permission("btp.list", PermissionDefault.TRUE);

    Permission REMOVE = new org.bukkit.permissions.Permission("btp.remove", PermissionDefault.TRUE);

    Permission FORCE_REMOVE = new org.bukkit.permissions.Permission("btp.force-remove", PermissionDefault.OP);

    Permission SET = new org.bukkit.permissions.Permission("btp.set", PermissionDefault.TRUE);

    Permission FORCE_SET = new org.bukkit.permissions.Permission("btp.force-set", PermissionDefault.OP);

    Permission TP = new org.bukkit.permissions.Permission("btp.tp", PermissionDefault.TRUE);

    Permission IGNORE_CD = new org.bukkit.permissions.Permission("btp.ignore-cd", PermissionDefault.OP);

    Permission TP_CROSS_WORLD = new org.bukkit.permissions.Permission("btp.tp-cross-world", PermissionDefault.OP);

    Permission TP_PRIVATE_ANCHOR = new org.bukkit.permissions.Permission("btp.tp-private-anchor", PermissionDefault.OP);
}
