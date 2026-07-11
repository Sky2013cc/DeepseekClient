package com.deepseek.client.command;

import com.deepseek.client.DeepseekClientMod;
import com.deepseek.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public CommandManager() {
        commands.add(new Command("toggle", "t", "Toggle a module on/off") {
            @Override public void execute(String[] args) {
                if (args.length < 2) { msg("Usage: .toggle <module>"); return; }
                Module m = DeepseekClientMod.getInstance().moduleManager.getModule(args[1]);
                if (m == null) { msg("Module not found: " + args[1]); return; }
                m.toggle();
                msg("§a" + m.getName() + " §7→ " + (m.isEnabled() ? "§aON" : "§cOFF"));
            }
        });

        commands.add(new Command("bind", "b", "Set keybind for module") {
            @Override public void execute(String[] args) {
                if (args.length < 3) { msg("Usage: .bind <module> <key>"); return; }
                String moduleName = args[1];
                String keyName = args[2].toUpperCase();
                Module m = DeepseekClientMod.getInstance().moduleManager.getModule(moduleName);
                if (m == null) { msg("§cModule not found: " + moduleName); return; }
                int keyCode = switch (keyName) {
                    case "F6" -> GLFW.GLFW_KEY_F6;
                    case "F7" -> GLFW.GLFW_KEY_F7;
                    case "F8" -> GLFW.GLFW_KEY_F8;
                    case "F9" -> GLFW.GLFW_KEY_F9;
                    case "F10" -> GLFW.GLFW_KEY_F10;
                    case "F11" -> GLFW.GLFW_KEY_F11;
                    case "F12" -> GLFW.GLFW_KEY_F12;
                    case "RSHIFT" -> GLFW.GLFW_KEY_RIGHT_SHIFT;
                    case "LSHIFT" -> GLFW.GLFW_KEY_LEFT_SHIFT;
                    case "LCONTROL", "LCTRL" -> GLFW.GLFW_KEY_LEFT_CONTROL;
                    case "RCONTROL", "RCTRL" -> GLFW.GLFW_KEY_RIGHT_CONTROL;
                    case "TAB" -> GLFW.GLFW_KEY_TAB;
                    case "GRAVE", "`" -> GLFW.GLFW_KEY_GRAVE_ACCENT;
                    case "R" -> GLFW.GLFW_KEY_R;
                    case "C" -> GLFW.GLFW_KEY_C;
                    case "X" -> GLFW.GLFW_KEY_X;
                    case "P" -> GLFW.GLFW_KEY_P;
                    default -> {
                        if (keyName.length() == 1) {
                            char c = keyName.charAt(0);
                            if (c >= 'A' && c <= 'Z') {
                                yield GLFW.GLFW_KEY_A + (c - 'A');
                            }
                        }
                        yield -1;
                    }
                };
                if (keyCode == -1) { msg("§cUnknown key: " + keyName); return; }
                m.setKeyBind(keyCode);
                msg("§aBound " + m.getName() + " to " + keyName);
            }
        });

        commands.add(new Command("help", "h", "Show all commands") {
            @Override public void execute(String[] args) {
                msg("§6=== " + DeepseekClientMod.CLIENT_NAME + " Commands ===");
                for (Command c : commands) {
                    msg("§e." + c.getName() + " §7(" + c.getAlias() + ") §f- " + c.getDescription());
                }
                msg("§7Use .toggle <module> to toggle modules");
            }
        });

        commands.add(new Command("friend", "f", "Manage friends") {
            @Override public void execute(String[] args) {
                if (args.length < 3) { msg("Usage: .friend <add/remove/list> <name>"); return; }
                Module friendModule = DeepseekClientMod.getInstance().moduleManager.getModule("Friend");
                if (friendModule instanceof com.deepseek.client.module.impl.Friend f) {
                    switch (args[1].toLowerCase()) {
                        case "add" -> { f.add(args[2]); msg("§aAdded friend: " + args[2]); }
                        case "remove" -> { f.remove(args[2]); msg("§cRemoved friend: " + args[2]); }
                        case "list" -> msg("§eFriends: " + String.join(", ", f.getFriends()));
                    }
                }
            }
        });

        commands.add(new Command("modules", "mods", "List all modules") {
            @Override public void execute(String[] args) {
                msg("§6=== Modules (" + DeepseekClientMod.getInstance().moduleManager.getModules().size() + ") ===");
                for (Module m : DeepseekClientMod.getInstance().moduleManager.getModules()) {
                    String status = m.isEnabled() ? "§a[ON]" : "§7[OFF]";
                    msg(status + " §f" + m.getName());
                }
            }
        });

        commands.add(new Command("config", "c", "Configuration commands") {
            @Override public void execute(String[] args) {
                if (args.length < 2) { msg("Usage: .config save/load/list/official"); return; }
                var cm = DeepseekClientMod.getInstance().configManager;
                switch (args[1].toLowerCase()) {
                    case "save" -> {
                        if (args.length > 2) cm.saveConfig(args[2], new java.util.HashMap<>());
                        msg("§aConfig saved as: " + cm.getCurrentConfig());
                    }
                    case "load" -> {
                        if (args.length < 3) { msg("Usage: .config load <name>"); return; }
                        cm.loadConfig(args[2]);
                        msg("§eLoaded config: " + args[2]);
                    }
                    case "list" -> msg("§eConfigs: " + String.join(", ", cm.listConfigs()));
                    case "official" -> {
                        cm.loadConfig("official_grim");
                        msg("§aLoaded official Grim config! All settings applied.");
                    }
                    default -> msg("§cUnknown: " + args[1]);
                }
            }
        });
    }

    public boolean execute(String message) {
        if (!message.startsWith(".")) return false;
        String[] parts = message.substring(1).split(" ");
        String cmdName = parts[0].toLowerCase();

        for (Command cmd : commands) {
            if (cmd.getName().equals(cmdName) || cmd.getAlias().equals(cmdName)) {
                cmd.execute(parts);
                return true;
            }
        }
        msg("§cUnknown command. Use .help");
        return true;
    }

    public void msg(String text) {
        if (mc.player != null) mc.player.sendMessage(Text.of("§7[§d" + DeepseekClientMod.CLIENT_NAME + "§7] §r" + text), false);
    }

    public abstract static class Command {
        private final String name, alias, description;
        public Command(String name, String alias, String description) {
            this.name = name; this.alias = alias; this.description = description;
        }
        public abstract void execute(String[] args);
        public String getName() { return name; }
        public String getAlias() { return alias; }
        public String getDescription() { return description; }
    }
}
