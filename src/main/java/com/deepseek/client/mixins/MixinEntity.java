package com.deepseek.client.mixins;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "setVelocityClient", at = @At("HEAD"), cancellable = true)
    private void onSetVelocity(double x, double y, double z, CallbackInfo ci) {
        var self = (Entity) (Object) this;
        if (self != mc.player) return;
        var mod = DeepseekClientMod.getInstance();
        if (mod == null) return;

        if (mod.moduleManager.getModule("AntiKnockback").isEnabled()) {
            mc.player.setVelocity(x * 0.3, y * 0.3, z * 0.3);
            ci.cancel();
        } else if (mod.moduleManager.getModule("KBBalance").isEnabled()) {
            mc.player.setVelocity(x * 1.3, y * 1.3, z * 1.3);
            ci.cancel();
        }
    }
}
