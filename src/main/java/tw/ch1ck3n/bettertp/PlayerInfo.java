package tw.ch1ck3n.bettertp;

import org.bukkit.entity.Player;

public class PlayerInfo {

    private long lastCommandTime;

    private Player player;

    public PlayerInfo(Player player) {
        this.player = player;
        this.lastCommandTime = System.currentTimeMillis();
    }

    public boolean canCommand(long cd) {
        return (System.currentTimeMillis() >= lastCommandTime + cd);
    }

    public long getCDLeft(long cd) {
        return ((lastCommandTime + cd) - System.currentTimeMillis());
    }

    public long getLastCommandTime() {
        return lastCommandTime;
    }

    public void setLastCommandTime(long lastCommandTime) {
        this.lastCommandTime = lastCommandTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
