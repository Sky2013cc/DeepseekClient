package com.deepseek.client;

import com.deepseek.client.command.CommandManager;
import com.deepseek.client.config.ConfigManager;
import com.deepseek.client.gui.GuiScreen;
import com.deepseek.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DeepseekClientMod implements ClientModInitializer {
    public static final String MOD_ID = "deepseekclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String CLIENT_NAME = "DeepseekClient";
    public static final String VERSION = "1.0.0";

    private static DeepseekClientMod INSTANCE;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public KeyBinding guiKeyBind;
    private final Map<String, KeyBinding> customBinds = new HashMap<>();

    public static DeepseekClientMod getInstance() { return INSTANCE; }

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Starting " + CLIENT_NAME + " v" + VERSION);

        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();

        guiKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.deepseekclient.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, CLIENT_NAME
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (guiKeyBind.wasPressed()) {
                client.setScreen(new GuiScreen());
            }
            moduleManager.onTick(client);
        });

        LOGGER.info(CLIENT_NAME + " initialized successfully!");
    }
}
