package com.deepseek.client.mixins;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
    // AntiKnockback: reduce velocity to 40% (Grim flags 0%)
    @Inject(method = "setVelocityClient", at = @At("HEAD"), cancellable = true)
    private void onSetVelocity(double x, double y, double z, CallbackInfo ci) {
        var mod = DeepseekClientMod.getInstance();
        if (mod == null) return;
        var self = (Entity) (Object) this;
        if (self == null || mc.player == null) return;

        boolean antikb = mod.moduleManager.getModule("AntiKnockback").isEnabled();
        boolean kbBalance = mod.moduleManager.getModule("KBBalance").isEnabled();

        if (antikb && self == mc.player) {
            self.setVelocity(x * 0.4, y * 0.4, z * 0.4);
            ci.cancel();
        } else if (kbBalance && self == mc.player) {
            self.setVelocity(x * 1.2, y * 1.2, z * 1.2);
            ci.cancel();
        }
    }

    private static final MinecraftClient mc = MinecraftClient.getInstance();
}
