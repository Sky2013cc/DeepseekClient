package com.deepseek.client.module.impl.visual;

import com.deepseek.client.module.*;

public class FullBright extends Module {
    public FullBright() { super("FullBright", "全亮度", Category.VISUAL, 0); }
    private double oldGamma;
    @Override public void onEnable() {
        oldGamma = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(100.0);
    }
    @Override public void onDisable() {
        mc.options.getGamma().setValue(oldGamma);
    }
}
