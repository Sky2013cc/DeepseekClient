package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Fly extends Module {
    public Fly() { super("Fly", "飞行模式", Category.MOVEMENT, 0); }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(0.05f);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().setFlySpeed(0.05f);
        }
    }
}
