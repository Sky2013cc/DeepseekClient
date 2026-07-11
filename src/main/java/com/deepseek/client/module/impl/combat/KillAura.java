package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.*;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", "自动攻击周围实体", Category.COMBAT, 0);
    }
    @Override public void onTick() {
        if (mc.player == null || mc.world == null) return;
        mc.world.getEntities().forEach(e -> {
            if (e != mc.player && e.isAlive() && mc.player.distanceTo(e) <= 4.5f) {
                mc.interactionManager.attackEntity(mc.player, e);
                mc.player.swingHand(mc.player.getActiveHand());
            }
        });
    }
}
