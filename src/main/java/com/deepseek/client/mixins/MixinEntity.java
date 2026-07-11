package com.deepseek.client.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class MixinEntity {
    // NoSlowdown, AntiWeb mixins
}
