package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class Sprint extends Module {
    public Sprint() { super("Sprint", "自动疾跑", Category.MOVEMENT, 0); }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.forwardSpeed > 0 && !mc.player.horizontalCollision && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }
}
