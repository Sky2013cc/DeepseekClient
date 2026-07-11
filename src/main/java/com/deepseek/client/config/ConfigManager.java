package com.deepseek.client.config;

import com.deepseek.client.DeepseekClientMod;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ConfigManager {
    private static final Path CONFIG_DIR = Path.of("DeepseekClient");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private String currentConfig = "default";

    public ConfigManager() {
        try {
            Files.createDirectories(CONFIG_DIR);
            // Create official configs if they don't exist
            if (!getConfigPath("official_grim").toFile().exists()) {
                saveOfficialGrimConfig();
            }
            if (!getConfigPath("official_vanilla").toFile().exists()) {
                saveOfficialVanillaConfig();
            }
            if (!getConfigPath("default").toFile().exists()) {
                saveConfig("default", createDefaultConfig());
            }
            // Load last used config
            loadConfig("official_grim");
        } catch (Exception e) {
            DeepseekClientMod.LOGGER.error("Config init failed", e);
        }
    }

    public void saveConfig(String name, Map<String, Object> data) {
        try {
            Files.writeString(getConfigPath(name), GSON.toJson(data));
            currentConfig = name;
        } catch (IOException e) {
            DeepseekClientMod.LOGGER.error("Failed to save config: " + name, e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> loadConfig(String name) {
        Path path = getConfigPath(name);
        if (!path.toFile().exists()) return new HashMap<>();
        try {
            String json = Files.readString(path);
            currentConfig = name;
            return GSON.fromJson(json, Map.class);
        } catch (IOException e) {
            DeepseekClientMod.LOGGER.error("Failed to load config: " + name, e);
            return new HashMap<>();
        }
    }

    public List<String> listConfigs() {
        List<String> configs = new ArrayList<>();
        File[] files = CONFIG_DIR.toFile().listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File f : files) {
                configs.add(f.getName().replace(".json", ""));
            }
        }
        return configs;
    }

    public String getCurrentConfig() { return currentConfig; }

    private Path getConfigPath(String name) {
        return CONFIG_DIR.resolve(name + ".json");
    }

    private Map<String, Object> createDefaultConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("version", DeepseekClientMod.VERSION);
        config.put("config_name", "default");
        config.put("description", "Default configuration");

        Map<String, Object> modules = new LinkedHashMap<>();
        modules.put("HUD", true);
        modules.put("ClickGUI", true);
        modules.put("Commands", true);
        modules.put("Sprint", true);
        modules.put("FullBright", false);
        modules.put("KillAura", false);
        modules.put("Speed", false);
        modules.put("Fly", false);
        modules.put("AntiKnockback", false);
        modules.put("NoFall", false);
        config.put("modules", modules);

        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("killaura_range", 4.2);
        settings.put("killaura_cps", 8);
        settings.put("speed_mode", "strafe");
        settings.put("fly_mode", "collision");
        settings.put("hud_color", "white");
        settings.put("theme", "dark");
        config.put("settings", settings);
        return config;
    }

    private void saveOfficialGrimConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("version", DeepseekClientMod.VERSION);
        config.put("config_name", "official_grim");
        config.put("description", "Official Grim Anticheat optimized config");
        config.put("author", "DeepseekClient");
        config.put("server", "GrimAC");

        Map<String, Object> modules = new LinkedHashMap<>();
        // Combat — Grim safe
        modules.put("KillAura", true);
        modules.put("AimAssist", true);
        modules.put("AutoClicker", true);
        modules.put("AntiBot", true);
        modules.put("AntiKnockback", true);  // 40% velocity
        modules.put("HitBox", false);        // Grim detects this
        modules.put("MoreKB", false);
        modules.put("AutoSoup", true);
        modules.put("BowAimBot", true);
        modules.put("AutoRod", true);
        modules.put("BedAura", true);

        // Movement — Grim safe
        modules.put("Speed", true);           // Strafe mode
        modules.put("Fly", true);             // Collision fly
        modules.put("Sprint", true);
        modules.put("NoSlowdown", true);      // Mixin-based
        modules.put("NoFall", true);          // Packet spoof
        modules.put("KeepSprint", true);
        modules.put("TargetStrafe", false);
        modules.put("AntiWeb", true);
        modules.put("Eagle", true);
        modules.put("AirStuck", false);

        // Visual
        modules.put("ESP", true);
        modules.put("NameTags", true);
        modules.put("FullBright", true);
        modules.put("XRay", false);
        modules.put("BlockOverlay", true);
        modules.put("StorageESP", true);
        modules.put("Tracers", true);
        modules.put("CameraNoClip", true);

        // Player
        modules.put("Scaffold", true);
        modules.put("NoJumpDelay", true);
        modules.put("FreeLook", true);
        modules.put("AutoTools", true);
        modules.put("FastPlace", true);
        modules.put("AutoPlace", false);
        modules.put("MLG", true);
        modules.put("Auto3rdPerson", true);
        modules.put("InvManager", true);

        // World
        modules.put("FPSBooster", true);
        modules.put("FullBright", true);
        modules.put("TimeChanger", false);
        modules.put("Privacy", true);
        modules.put("Disabler", false);
        modules.put("Blink", false);
        modules.put("FakeLag", false);

        // Misc
        modules.put("HUD", true);
        modules.put("ClickGUI", true);
        modules.put("Commands", true);
        modules.put("Friend", true);
        modules.put("LagDetector", true);
        config.put("modules", modules);

        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("killaura_range", 4.1);
        settings.put("killaura_cps", 9);
        settings.put("killaura_rotation", "gcd");
        settings.put("speed_mode", "grim_strafe");
        settings.put("speed_hops", true);
        settings.put("fly_mode", "grim_collision");
        settings.put("antikb_mode", "reduce_40");
        settings.put("velocity_horizontal", 0.4);
        settings.put("velocity_vertical", 0.4);
        settings.put("scaffold_tower", true);
        settings.put("scaffold_safe_rotate", true);
        settings.put("hud_color", "purple");
        settings.put("theme", "dark");
        settings.put("theme_color", "purple");
        settings.put("clickgui_columns", 5);
        settings.put("clickgui_scale", 1.0);
        config.put("settings", settings);

        saveConfig("official_grim", config);
        DeepseekClientMod.LOGGER.info("Official Grim config created");
    }

    private void saveOfficialVanillaConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("version", DeepseekClientMod.VERSION);
        config.put("config_name", "official_vanilla");
        config.put("description", "Official Vanilla-friendly config (minimal flags)");
        config.put("author", "DeepseekClient");
        config.put("server", "Vanilla/Paper");

        Map<String, Object> modules = new LinkedHashMap<>();
        modules.put("Sprint", true);
        modules.put("FullBright", true);
        modules.put("HUD", true);
        modules.put("ClickGUI", true);
        modules.put("Commands", true);
        modules.put("AutoClicker", true);
        modules.put("NoSlowdown", true);
        modules.put("NoJumpDelay", true);
        modules.put("AutoTools", true);
        modules.put("FastPlace", true);
        modules.put("InvManager", true);
        modules.put("FPSBooster", true);
        modules.put("AutoFish", true);
        modules.put("StorageESP", true);
        modules.put("BlockOverlay", true);
        modules.put("NameTags", true);
        modules.put("CameraNoClip", true);
        modules.put("KillAura", false);
        modules.put("Speed", false);
        modules.put("Fly", false);
        modules.put("AntiKnockback", false);
        modules.put("Scaffold", false);
        modules.put("XRay", false);
        config.put("modules", modules);

        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("killaura_cps", 12);
        settings.put("hud_color", "green");
        settings.put("theme", "light");
        config.put("settings", settings);

        saveConfig("official_vanilla", config);
        DeepseekClientMod.LOGGER.info("Official Vanilla config created");
    }
}
