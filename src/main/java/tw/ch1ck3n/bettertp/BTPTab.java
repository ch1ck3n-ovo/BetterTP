package tw.ch1ck3n.bettertp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BTPTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<String>();
        if (command.getName().equalsIgnoreCase("btp") && strings.length >= 0) {
            if (commandSender instanceof Player) {
                String cmds[] = BetterTP.INSTANCE.config.getConfigurationSection("commands" + ".").getValues(false).values().toArray(new String[0]);
                String perms[] = BetterTP.INSTANCE.config.getConfigurationSection("permissions" + ".").getValues(false).values().toArray(new String[0]);

                for (int i = 0; i < perms.length; i++) {
                    String perm = perms[i];
                    if(commandSender.hasPermission(perm) || perm.equalsIgnoreCase("none")) {
                        list.add(cmds[i + 1]);
                    }
                }
                return list;
            }
        }
        return list;
    }
}
