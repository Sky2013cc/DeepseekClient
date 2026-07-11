package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class AutoSoup extends Module {
    public AutoSoup() { super("AutoSoup", "自动喝汤", Category.COMBAT, 0); }
    @Override public void onTick() {
        if (mc.player == null || mc.player.getHealth() > 10) return;
        for (int i = 0; i < 9; i++) {
            var stack = mc.player.getInventory().getStack(i);
            if (stack.getName().getString().contains("Mushroom Stew")) {
                mc.player.getInventory().selectedSlot = i;
                mc.options.useKey.setPressed(true);
                return;
            }
        }
    }
}
