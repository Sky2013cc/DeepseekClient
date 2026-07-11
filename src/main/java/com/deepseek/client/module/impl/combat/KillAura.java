package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class KillAura extends Module {
    private long lastAttack = 0;
    private float targetYaw, targetPitch;

    public KillAura() {
        super("KillAura", "自动攻击附近实体", Category.COMBAT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = null;
        double closest = 4.5;
        for (PlayerEntity e : mc.world.getPlayers()) {
            if (e == mc.player || !e.isAlive() || e.isInvisible()) continue;
            double dist = mc.player.distanceTo(e);
            if (dist < closest) {
                closest = dist;
                target = e;
            }
        }
        if (target == null) return;

        // 平滑旋转到目标
        Vec3d diff = target.getPos().subtract(mc.player.getPos()).add(0, target.getEyeHeight(target.getPose()) * 0.5, 0);
        double dist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        targetYaw = (float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90);
        targetPitch = (float) -Math.toDegrees(Math.atan2(diff.y, dist));
        targetPitch = MathHelper.clamp(targetPitch, -90, 90);

        mc.player.setYaw(targetYaw);
        mc.player.setPitch(targetPitch);

        long now = System.currentTimeMillis();
        if (now - lastAttack > 300) { // ~3 cps
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAttack = now;
        }
    }
}
