package com.deepseek.client.module.impl;

import com.deepseek.client.DeepseekClientMod;
import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

public class HUD extends Module {
    public HUD() { super("HUD", "屏幕信息显示", Category.MISC, 0); }

    // HUD rendering is handled by MixinInGameHud
}
