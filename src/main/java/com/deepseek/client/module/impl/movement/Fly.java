package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Fly extends Module {
    private int timer = 0;

    public Fly() {
        super("Fly", "Grim-bypass collision fly", Category.MOVEMENT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        timer++;

        if (mc.player.isOnGround()) {
            mc.player.jump();
            return;
        }

        // Grim safe fly: use velocity packets, not abilities
        double y = 0;
        if (mc.options.jumpKey.isPressed()) y = 0.5;
        else if (mc.options.sneakKey.isPressed()) y = -0.5;

        mc.player.setVelocity(
            mc.player.getVelocity().x,
            y,
            mc.player.getVelocity().z
        );

        // Send minimal move packets to avoid flag
        if (timer % 3 == 0) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }
}
