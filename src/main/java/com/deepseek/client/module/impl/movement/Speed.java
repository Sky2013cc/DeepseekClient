package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class Speed extends Module {
    private int ticks = 0;

    public Speed() { super("Speed", "加速行走", Category.MOVEMENT, 0); }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        ticks++;

        if (mc.player.forwardSpeed == 0 && mc.player.sidewaysSpeed == 0) return;

        if (mc.player.isOnGround() && ticks % 4 == 0) {
            mc.player.jump();
        }

        double strafe = Math.sqrt(mc.player.sidewaysSpeed * mc.player.sidewaysSpeed
                + mc.player.forwardSpeed * mc.player.forwardSpeed);
        if (strafe > 0) {
            mc.player.setVelocity(
                mc.player.getVelocity().x * 1.05,
                mc.player.getVelocity().y,
                mc.player.getVelocity().z * 1.05
            );
        }
    }
}
