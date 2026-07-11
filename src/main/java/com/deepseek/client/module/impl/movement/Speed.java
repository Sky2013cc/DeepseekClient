package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.*;

public class Speed extends Module {
    public Speed() { super("Speed", "加速行走", Category.MOVEMENT, 0); }
    @Override public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isOnGround() && mc.player.forwardSpeed > 0) {
            mc.player.setVelocity(mc.player.getVelocity().x * 1.3, mc.player.getVelocity().y, mc.player.getVelocity().z * 1.3);
        }
    }
}
