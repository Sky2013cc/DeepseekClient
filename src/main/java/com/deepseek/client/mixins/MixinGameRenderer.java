package com.deepseek.client.mixins;

import com.deepseek.client.gui.DeepseekSplashOverlay;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinGameRenderer {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc != null) {
            mc.setOverlay(new DeepseekSplashOverlay(mc));
        }
    }
}
