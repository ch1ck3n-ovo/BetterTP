package tw.ch1ck3n.bettertp.utils;

import org.bukkit.entity.Player;

public class CustomPlayer {

    private long lastCommandTime;

    private final Player player;

    public CustomPlayer(Player player) {
        this.player = player;
        this.lastCommandTime = System.currentTimeMillis();
    }

    public boolean hasTimePassed(long cd) {
        return (System.currentTimeMillis() >= lastCommandTime + cd);
    }

    public long getCoolDownLeft(long cd) {
        return ((lastCommandTime + cd) - System.currentTimeMillis());
    }

    public Player getPlayer() {
        return player;
    }

    public void setLastCommandTime(long lastCommandTime) {
        this.lastCommandTime = lastCommandTime;
    }
}
