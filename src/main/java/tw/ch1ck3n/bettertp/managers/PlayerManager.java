package tw.ch1ck3n.bettertp.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tw.ch1ck3n.bettertp.utils.CustomPlayer;

import java.util.ArrayList;

public class PlayerManager {

    private ArrayList<CustomPlayer> players = new ArrayList<>();

    public PlayerManager() {
        for (Player p: Bukkit.getOnlinePlayers()) {
            this.register(p);
        }
    }

    public CustomPlayer register(Player player){
        CustomPlayer cp = new CustomPlayer(player);
        this.players.add(cp);
        return cp;
    }

    public void unregister(Player player){
        CustomPlayer cp = getNullIfNotRegistered(player);
        if (cp != null)  players.remove(cp);
    }

    @Deprecated
    public CustomPlayer getNullIfNotRegistered(Player player){
        for (CustomPlayer cp : players) if (cp.getPlayer() == player) return cp;
        return null;
    }

    public CustomPlayer getPlayer(Player player){
        CustomPlayer p = getNullIfNotRegistered(player);
        if (p != null) return p;
        return register(player);
    }

}
