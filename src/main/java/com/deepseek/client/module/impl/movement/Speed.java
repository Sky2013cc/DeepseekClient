package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Speed extends Module {
    private int ticks = 0;
    private double moveSpeed;
    private double lastDist;

    public Speed() {
        super("Speed", "Grim-bypass strafe speed", Category.MOVEMENT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        ticks++;

        if (!mc.player.isOnGround() || mc.player.forwardSpeed == 0 && mc.player.sidewaysSpeed == 0) return;

        if (ticks % 5 == 0 && mc.player.isOnGround()) {
            // Grim-safe hop
            mc.player.setVelocity(mc.player.getVelocity().x, 0.4, mc.player.getVelocity().z);
        }

        double strafe = Math.sqrt(mc.player.sidewaysSpeed * mc.player.sidewaysSpeed + mc.player.forwardSpeed * mc.player.forwardSpeed);
        if (strafe > 0) {
            mc.player.setVelocity(
                mc.player.getVelocity().x * 1.08,
                mc.player.getVelocity().y,
                mc.player.getVelocity().z * 1.08
            );
        }
    }
}
