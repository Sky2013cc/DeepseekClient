package com.deepseek.client.module.impl;

import com.deepseek.client.module.*;
import net.minecraft.client.gui.DrawContext;

public class HUD extends Module {
    public HUD() { super("HUD", "屏幕信息显示", Category.MISC, 0); }
    @Override public void onTick() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;
        // HUD rendering is handled by mixin in InGameHud
    }
}
