package com.deepseek.client.module.impl.player;

import com.deepseek.client.module.*;

public class Scaffold extends Module {
    public Scaffold() { super("Scaffold", "自动搭路", Category.PLAYER, 0); }
    @Override public void onTick() {
        if (mc.player == null) return;
        mc.options.sneakKey.setPressed(true);
    }
    @Override public void onDisable() { if (mc.options != null) mc.options.sneakKey.setPressed(false); }
}
