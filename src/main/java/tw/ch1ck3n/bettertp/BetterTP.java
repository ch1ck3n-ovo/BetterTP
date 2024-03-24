package tw.ch1ck3n.bettertp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tw.ch1ck3n.bettertp.commands.BTPMainCommand;
import tw.ch1ck3n.bettertp.commands.BTPCmdTab;
import tw.ch1ck3n.bettertp.configs.Messages;
import tw.ch1ck3n.bettertp.configs.Settings;
import tw.ch1ck3n.bettertp.listeners.PlayerListener;
import tw.ch1ck3n.bettertp.managers.PlayerManager;

public final class BetterTP extends JavaPlugin implements Listener {

    public static BetterTP INSTANCE;

    private PlayerManager playerManager;

    public FileConfiguration config;

    private Settings settings;

    private Messages messages;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.playerManager = new PlayerManager();

        this.saveDefaultConfig();
        config = getConfig();

        this.settings = new Settings();
        this.messages = new Messages();

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getCommand("btp").setExecutor(new BTPMainCommand());
        this.getCommand("btp").setTabCompleter(new BTPCmdTab());
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Settings getSettings() {
        return settings;
    }

    public Messages getMessages() {
        return messages;
    }
}
