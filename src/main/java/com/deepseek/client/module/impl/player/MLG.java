package com.deepseek.client.module.impl.player;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class MLG extends Module {
    public MLG() { super("MLG", "落地水", Category.PLAYER, 0); }
    @Override public void onTick() {
        if (mc.player == null || mc.player.fallDistance < 5) return;
        for (int i = 0; i < 9; i++) {
            var s = mc.player.getInventory().getStack(i);
            if (s.getName().getString().contains("Water Bucket")) {
                mc.player.getInventory().selectedSlot = i;
                mc.options.useKey.setPressed(true);
                return;
            }
        }
    }
}
