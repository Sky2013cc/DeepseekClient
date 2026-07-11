package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class AutoClicker extends Module {
    private long lastClick = 0;
    public AutoClicker() { super("AutoClicker", "自动点击", Category.COMBAT, 0); }
    @Override public void onTick() {
        if (mc.player == null) return;
        long now = System.currentTimeMillis();
        if (now - lastClick > 80) { // ~12 cps
            mc.options.attackKey.setPressed(true);
            lastClick = now;
        }
    }
    @Override public void onDisable() { if (mc.options != null) mc.options.attackKey.setPressed(false); }
}
