package com.deepseek.client.mixins;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    // NoSlowdown: reduce item use slow
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        var mod = DeepseekClientMod.getInstance();
        if (mod == null) return;

        boolean noSlow = mod.moduleManager.getModule("NoSlowdown").isEnabled();
        if (noSlow && mc.player.isUsingItem()) {
            mc.player.setVelocity(mc.player.getVelocity().multiply(1.0 / 0.6));
        }
    }
}
