package com.deepseek.client.mixins;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    // HitBox, MoreKB, AntiKnockback mixins
}
