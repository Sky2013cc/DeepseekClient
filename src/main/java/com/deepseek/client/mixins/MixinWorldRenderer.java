package com.deepseek.client.mixins;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    // ESP, Tracers, StorageESP render mixins
}
