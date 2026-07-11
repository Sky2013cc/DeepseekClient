package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.*;

public class NoFall extends Module {
    public NoFall() { super("NoFall", "无摔落伤害", Category.MOVEMENT, 0); }
    @Override public void onTick() {
        if (mc.player == null || mc.player.fallDistance <= 3) return;
        mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly(true));
        mc.player.fallDistance = 0;
    }
}
