package com.deepseek.client.mixins;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext ctx, float tickDelta, CallbackInfo ci) {
        var mod = DeepseekClientMod.getInstance();
        var mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.debugEnabled) return;

        int y = 5;
        int color = 0xFFD0BCFF;
        ctx.drawText(mc.textRenderer, Text.literal("§d" + mod.CLIENT_NAME + " §7v" + mod.VERSION), 5, y, color, true);
        y += 12;

        var enabledModules = mod.moduleManager.getModules().stream()
                .filter(m -> m.isEnabled() && !m.getName().equals("HUD"))
                .toList();

        for (var m : enabledModules) {
            ctx.drawText(mc.textRenderer, Text.literal("§f" + m.getName()), 5, y, 0xFFFFFFFF, true);
            y += 11;
        }
    }
}
