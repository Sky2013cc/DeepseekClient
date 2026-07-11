package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.*;

public class Sprint extends Module {
    public Sprint() { super("Sprint", "自动疾跑", Category.MOVEMENT, 0); }
    @Override public void onTick() {
        if (mc.player == null) return;
        mc.player.setSprinting(mc.player.forwardSpeed > 0 && !mc.player.horizontalCollision);
    }
}
