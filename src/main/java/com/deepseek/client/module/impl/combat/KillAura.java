package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class KillAura extends Module {
    private long lastAttack = 0;
    private int targetId = -1;
    private float lastYaw, lastPitch;

    public KillAura() {
        super("KillAura", "Grim-bypass auto attack", Category.COMBAT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // Find closest valid target within 4.0 blocks (Grim-safe range)
        PlayerEntity target = null;
        double closest = 4.2;
        for (PlayerEntity e : mc.world.getPlayers()) {
            if (e == mc.player || !e.isAlive() || e.isInvisible()) continue;
            double dist = mc.player.distanceTo(e);
            if (dist < closest) {
                closest = dist;
                target = e;
            }
        }
        if (target == null) return;

        // Grim-safe rotation: use GCD fix
        float[] rotations = getRotations(target);
        float yaw = rotations[0];
        float pitch = MathHelper.clamp(rotations[1], -90, 90);

        // Apply GCD fix to avoid Grim flag
        float gcd = (Math.round(yaw / 0.5f) * 0.5f);
        yaw = gcd;

        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);

        // Attack with randomized delay (400-600ms = 2-3 cps, Grim safe)
        long now = System.currentTimeMillis();
        if (now - lastAttack > 450 + (int)(Math.random() * 150)) {
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAttack = now;
            targetId = target.getId();
        }
    }

    private float[] getRotations(PlayerEntity target) {
        Vec3d diff = target.getPos().subtract(mc.player.getPos()).add(0, target.getEyeHeight(mc.player.getPose()) * 0.5, 0);
        double dist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        float yaw = (float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90);
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, dist));
        return new float[]{yaw, pitch};
    }
}
