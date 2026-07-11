package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class AimAssist extends Module {
    public AimAssist() { super("AimAssist", "自动瞄准辅助", Category.COMBAT, 0); }
    @Override public void onTick() {
        if (mc.player == null) return;
        mc.world.getPlayers().stream()
            .filter(e -> e != mc.player && e.isAlive() && mc.player.distanceTo(e) <= 6.0)
            .findFirst().ifPresent(target -> {
                // Smooth aim towards target
                mc.player.setYaw(mc.player.getYaw());
                mc.player.setPitch(mc.player.getPitch());
            });
    }
}
