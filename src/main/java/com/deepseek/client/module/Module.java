package com.deepseek.client.module;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.MinecraftClient;

public abstract class Module {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private int keyBind;
    private boolean enabled;

    public Module(String name, String description, Category category, int keyBind) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keyBind = keyBind;
        this.enabled = false;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public int getKeyBind() { return keyBind; }
    public boolean isEnabled() { return enabled; }
    public void setKeyBind(int keyBind) { this.keyBind = keyBind; }
}
