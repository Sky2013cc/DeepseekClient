package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall() { super("NoFall", "防止摔落伤害", Category.MOVEMENT, 0); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.fallDistance <= 3) return;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        mc.player.fallDistance = 0;
    }
}
