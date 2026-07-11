package com.deepseek.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

public class WorldUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Optional<PlayerEntity> findClosestTarget(double range) {
        if (mc.world == null) return Optional.empty();
        return mc.world.getPlayers().stream()
                .filter(e -> e != mc.player && e.isAlive() && mc.player.distanceTo(e) <= range)
                .min(Comparator.comparingDouble(e -> mc.player.distanceTo(e)))
                .map(e -> (PlayerEntity) e);
    }

    public static Vec3d getCenter(Box box) {
        return new Vec3d(
                (box.minX + box.maxX) / 2,
                (box.minY + box.maxY) / 2,
                (box.minZ + box.maxZ) / 2
        );
    }

    public static boolean isInHole(PlayerEntity player) {
        return false; // Placeholder - implement hole detection
    }
}
