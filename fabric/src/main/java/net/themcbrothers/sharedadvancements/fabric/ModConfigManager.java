package net.themcbrothers.sharedadvancements.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.themcbrothers.sharedadvancements.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json");

    private static ModConfig config = new ModConfig();

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(json, ModConfig.class);
            } catch (IOException e) {
                Constants.LOG.error("Failed to read config, using defaults: {}", e.getMessage());
                config = new ModConfig();
            }
        }

        // Always write back so missing keys are filled with defaults
        save();
    }

    /**
     * Persists the current config to disk.
     */
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(config));
        } catch (IOException e) {
            Constants.LOG.error("Failed to save config: {}", e.getMessage());
        }
    }

    /**
     * Read-only access to the config from anywhere in your mod.
     */
    public static ModConfig get() {
        return config;
    }
}
