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
            if (!getConfigPath("official_bedwars").toFile().exists()) {
                saveOfficialBedwarsConfig();
            }
            if (!getConfigPath("official_skywars").toFile().exists()) {
                saveOfficialSkywarsConfig();
            }
            if (!getConfigPath("official_vanilla").toFile().exists()) {
                saveOfficialVanillaConfig();
            }
            if (!getConfigPath("default").toFile().exists()) {
                saveConfig("default", createDefaultConfig());
            }
            // Load last used config
            loadConfig("official_bedwars");
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

    private void saveOfficialBedwarsConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("version", DeepseekClientMod.VERSION);
        config.put("config_name", "official_bedwars");
        config.put("description", "Official BedWars config — Grim optimized");
        config.put("author", "DeepseekClient");
        config.put("server", "GrimAC / BedWars");

        Map<String, Object> modules = new LinkedHashMap<>();
        // Combat — BedWars focused
        modules.put("KillAura", true);        // GCD, 4.1 range, 8cps
        modules.put("AimAssist", true);       // Smooth aim
        modules.put("AutoClicker", true);     // 10-12 cps
        modules.put("AntiBot", true);         // Ignore NPCs
        modules.put("AntiKnockback", true);   // 40% KB (Grim safe)
        modules.put("HitBox", false);         // ✗ Grim flags
        modules.put("AutoSoup", false);       // ✗ No soup in BW
        modules.put("AutoRod", false);        // ✗ Not needed
        modules.put("BedAura", true);         // ✔ Auto bed break
        modules.put("AutoTools", true);       // ✔ Switch to pick/sword

        // Movement
        modules.put("Speed", true);           // Strafe mode, short bursts
        modules.put("Fly", false);            // ✗ Banned in BW
        modules.put("Sprint", true);
        modules.put("NoSlowdown", true);      // Eat gaps while running
        modules.put("NoFall", true);          // Clutch safe
        modules.put("KeepSprint", true);
        modules.put("TargetStrafe", true);    // ✔ Circle enemies
        modules.put("AntiWeb", true);
        modules.put("Eagle", true);           // ✔ Safe bridging
        modules.put("AirStuck", false);

        // Visual
        modules.put("ESP", true);             // ✔ See players through walls
        modules.put("NameTags", true);        // ✔ Show stacked armor
        modules.put("FullBright", true);      // ✔ Night vision
        modules.put("StorageESP", true);      // ✔ Find chests/enders
        modules.put("Tracers", true);         // ✔ Locate enemies
        modules.put("BlockOverlay", true);    // ✔ Better block outline
        modules.put("CameraNoClip", true);    // ✔ Free look

        // Player — BedWars essentials
        modules.put("Scaffold", true);        // ✔ Fast bridging
        modules.put("NoJumpDelay", true);     // ✔ Instant jump
        modules.put("FreeLook", true);        // ✔ 360 look
        modules.put("FastPlace", true);       // ✔ Place blocks faster
        modules.put("AutoPlace", true);       // ✔ Auto place wool
        modules.put("MLG", true);             // ✔ Water clutch
        modules.put("InvManager", true);      // ✔ Sort inventory
        modules.put("ChestSteal", true);      // ✔ Quick steal
        modules.put("Refill", true);          // ✔ Auto refill hotbar

        // World
        modules.put("FPSBooster", true);      // ✔ Performance
        modules.put("Privacy", true);
        modules.put("Blink", false);          // ✗ Bannable
        modules.put("FakeLag", false);

        // HUD
        modules.put("HUD", true);
        modules.put("ClickGUI", true);
        modules.put("Commands", true);
        modules.put("Friend", true);
        modules.put("LagDetector", true);
        config.put("modules", modules);

        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("killaura_range", 4.0);
        settings.put("killaura_cps", 8);
        settings.put("killaura_rotation", "gcd");
        settings.put("speed_mode", "grim_strafe");
        settings.put("speed_hops", false);
        settings.put("antikb_mode", "reduce_40");
        settings.put("velocity_horizontal", 0.4);
        settings.put("velocity_vertical", 0.4);
        settings.put("scaffold_tower", true);
        settings.put("scaffold_angle", 180);
        settings.put("scaffold_sprint", true);
        settings.put("bedaura_range", 5.0);
        settings.put("bedaura_break_speed", 1.0);
        settings.put("autotools_prefer", "sword,pick,axe");
        settings.put("invmanager_sort", "tools,blocks,food");
        settings.put("cheststeal_delay", 50);
        settings.put("refill_slots", "1-3");
        settings.put("hud_color", "bedwars_red");
        settings.put("theme", "dark");
        settings.put("theme_color", "red");
        settings.put("clickgui_columns", 5);
        config.put("settings", settings);

        saveConfig("official_bedwars", config);
        DeepseekClientMod.LOGGER.info("Official BedWars config created");
    }

    private void saveOfficialSkywarsConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("version", DeepseekClientMod.VERSION);
        config.put("config_name", "official_skywars");
        config.put("description", "Official SkyWars config — Grim optimized");
        config.put("author", "DeepseekClient");
        config.put("server", "GrimAC / SkyWars");

        Map<String, Object> modules = new LinkedHashMap<>();
        // Combat — SkyWars is faster-paced
        modules.put("KillAura", true);
        modules.put("AimAssist", true);
        modules.put("AutoClicker", true);     // 12-14 cps
        modules.put("AntiBot", true);
        modules.put("AntiKnockback", true);   // 40%
        modules.put("AutoSoup", false);
        modules.put("BowAimBot", true);       // ✔ Bows are common in SW
        modules.put("AutoRod", false);

        // Movement — fast island rushing
        modules.put("Speed", true);           // Short speed bursts
        modules.put("Fly", false);            // ✗ Ban
        modules.put("Sprint", true);
        modules.put("NoSlowdown", true);      // Eat pearls while running
        modules.put("NoFall", true);          // Pearl clutch safe
        modules.put("KeepSprint", true);
        modules.put("TargetStrafe", true);    // ✔ PvP strafe
        modules.put("AntiWeb", true);
        modules.put("Eagle", true);           // ✔ Bridge safety

        // Visual
        modules.put("ESP", true);
        modules.put("NameTags", true);        // ✔ See names at range
        modules.put("FullBright", true);
        modules.put("StorageESP", true);      // ✔ Find chests
        modules.put("Tracers", true);
        modules.put("CameraNoClip", true);
        modules.put("BlockOverlay", true);

        // Player
        modules.put("Scaffold", true);        // ✔ Fast bridge
        modules.put("NoJumpDelay", true);
        modules.put("FreeLook", true);
        modules.put("AutoTools", true);       // ✔ Sword first
        modules.put("FastPlace", true);       // ✔ Block placement
        modules.put("MLG", true);             // ✔ Pearl clutch
        modules.put("Auto3rdPerson", true);
        modules.put("InvManager", true);      // ✔ Kit sorting
        modules.put("ChestSteal", true);      // ✔ Loot fast
        modules.put("Refill", true);

        // World
        modules.put("FPSBooster", true);
        modules.put("Privacy", true);

        // HUD
        modules.put("HUD", true);
        modules.put("ClickGUI", true);
        modules.put("Commands", true);
        modules.put("Friend", true);
        modules.put("LagDetector", true);
        config.put("modules", modules);

        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("killaura_range", 4.2);
        settings.put("killaura_cps", 10);
        settings.put("killaura_rotation", "gcd");
        settings.put("speed_mode", "grim_strafe");
        settings.put("speed_hops", true);
        settings.put("antikb_mode", "reduce_40");
        settings.put("velocity_horizontal", 0.4);
        settings.put("velocity_vertical", 0.4);
        settings.put("bowaimbot_predict", true);
        settings.put("bowaimbot_range", 50);
        settings.put("scaffold_tower", false);
        settings.put("scaffold_sprint", true);
        settings.put("autotools_prefer", "sword,bow,pick");
        settings.put("cheststeal_delay", 30);
        settings.put("refill_slots", "1-4");
        settings.put("hud_color", "skywars_blue");
        settings.put("theme", "dark");
        settings.put("theme_color", "blue");
        settings.put("clickgui_columns", 5);
        config.put("settings", settings);

        saveConfig("official_skywars", config);
        DeepseekClientMod.LOGGER.info("Official SkyWars config created");
    }
}
