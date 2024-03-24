package tw.ch1ck3n.bettertp.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private final File file;

    private YamlConfiguration config;

    public CustomConfig(File parent, String child) {
        this.file = new File(parent, child);
        if (this.exists()) this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void create() {
        if (!this.exists()) {
            try {
                this.file.createNewFile();
                this.reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean exists() {
        return this.file.exists();
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public long getLong(String path) {
        return this.config.getLong(path);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public YamlConfiguration load() {
        return this.config;
    }

    public boolean reload() {
        if (this.exists()) {
            this.config = YamlConfiguration.loadConfiguration(this.file);
            return true;
        }
        return false;
    }

    public boolean save() {
        if (this.exists()) {
            try {
                this.config.save(this.file);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
