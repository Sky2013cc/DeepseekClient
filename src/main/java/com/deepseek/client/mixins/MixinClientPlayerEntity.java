package com.deepseek.client.mixins;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    // Anti-Knockback, No-Fall, Speed, Fly mixins
}
