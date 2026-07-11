package com.deepseek.client.module.impl.player;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public Scaffold() { super("Scaffold", "自动搭路 - 在脚下放方块", Category.PLAYER, 0); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // 检查脚下是否有方块
        BlockPos below = mc.player.getBlockPos().down();
        if (mc.world.getBlockState(below).isAir()) {
            // 找快捷栏里的方块
            int slot = -1;
            for (int i = 0; i < 9; i++) {
                var stack = mc.player.getInventory().getStack(i);
                if (stack.getItem() instanceof BlockItem) {
                    slot = i;
                    break;
                }
            }
            if (slot == -1) return;

            // 切换到方块
            mc.player.getInventory().selectedSlot = slot;

            // 找到合适的位置放方块
            BlockPos targetPos = below;
            Direction dir = Direction.UP;

            // 从脚下开始找可放置的面
            for (Direction d : Direction.values()) {
                BlockPos neighbor = below.offset(d);
                if (mc.world.getBlockState(neighbor).isSolidBlock(mc.world, neighbor)) {
                    targetPos = neighbor;
                    dir = d.getOpposite();
                    break;
                }
            }

            // 放方块
            var hitResult = new BlockHitResult(
                Vec3d.ofCenter(targetPos).add(Vec3d.of(dir.getVector()).multiply(0.5)),
                dir, targetPos, false
            );
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hitResult);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
