package tw.ch1ck3n.bettertp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tw.ch1ck3n.bettertp.BetterTP;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        BetterTP.INSTANCE.getPlayerManager().register(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        BetterTP.INSTANCE.getPlayerManager().unregister(e.getPlayer());
    }

}
