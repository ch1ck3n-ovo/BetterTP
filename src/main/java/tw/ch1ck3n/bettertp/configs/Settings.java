package tw.ch1ck3n.bettertp.configs;

import tw.ch1ck3n.bettertp.BetterTP;

public class Settings {

    public final long minCommandCoolDown;

    public final boolean onlyExecuteByPlayers;

    public final boolean teleportCrossWorld;

    public final boolean teleportPrivateAnchor;

    public Settings() {
        this.minCommandCoolDown = BetterTP.INSTANCE.getConfig().getLong("setting.command.min-command-cool-down");
        this.onlyExecuteByPlayers = BetterTP.INSTANCE.getConfig().getBoolean("setting.command.only-execute-by-players");
        this.teleportCrossWorld = BetterTP.INSTANCE.getConfig().getBoolean("setting.teleport.teleport-cross-world");
        this.teleportPrivateAnchor = BetterTP.INSTANCE.getConfig().getBoolean("setting.teleport.teleport-private-anchor");
    }
}
