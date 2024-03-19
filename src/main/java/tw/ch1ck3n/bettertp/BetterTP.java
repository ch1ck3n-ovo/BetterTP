package tw.ch1ck3n.bettertp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BetterTP extends JavaPlugin implements Listener {

    public static BetterTP INSTANCE;

    public ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    public FileConfiguration config;

    @Override
    public void onEnable() {
        INSTANCE = this;

        for (Player p: Bukkit.getOnlinePlayers()) {
            playerInfos.add(new PlayerInfo(p));
        }
        
        this.saveDefaultConfig();
        config = getConfig();

        this.getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("btp").setExecutor(new BTPCmd());
        this.getCommand("btp").setTabCompleter(new BTPTab());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        playerInfos.add(new PlayerInfo(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerInfos.remove(new PlayerInfo(e.getPlayer()));
    }
}
