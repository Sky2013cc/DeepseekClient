package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AimAssist extends Module {
    public AimAssist() { super("AimAssist", "平滑瞄准辅助", Category.COMBAT, 0); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        mc.world.getPlayers().stream()
            .filter(e -> e != mc.player && e.isAlive() && mc.player.distanceTo(e) <= 6.0)
            .findFirst().ifPresent(target -> {
                Vec3d diff = target.getPos().subtract(mc.player.getPos()).add(0, target.getEyeHeight(target.getPose()) * 0.5, 0);
                double dist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
                float yaw = (float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90);
                float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, dist));
                pitch = MathHelper.clamp(pitch, -90, 90);

                // 平滑插值
                mc.player.setYaw(mc.player.getYaw() + (yaw - mc.player.getYaw()) * 0.15f);
                mc.player.setPitch(mc.player.getPitch() + (pitch - mc.player.getPitch()) * 0.15f);
            });
    }
}
