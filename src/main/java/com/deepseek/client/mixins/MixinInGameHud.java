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
        var mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.debugEnabled) return;
        var mod = DeepseekClientMod.getInstance();
        if (mod == null || !mod.moduleManager.getModule("HUD").isEnabled()) return;

        int y = 4;
        int color = 0xFFD0BCFF;

        // 标题
        ctx.drawText(mc.textRenderer, Text.literal("§d" + mod.CLIENT_NAME + " §7v" + mod.VERSION),
                4, y, color, true);
        y += 12;

        // 性能信息
        ctx.drawText(mc.textRenderer, Text.literal("§7FPS: §f" + mc.getCurrentFps()),
                4, y, 0xFFFFFFFF, true);
        y += 10;

        // 坐标
        var pos = mc.player.getPos();
        ctx.drawText(mc.textRenderer, Text.literal(String.format("§7XYZ: §f%.0f %.0f %.0f", pos.x, pos.y, pos.z)),
                4, y, 0xFFFFFFFF, true);
        y += 12;

        // 开启的模块列表
        var enabledModules = mod.moduleManager.getModules().stream()
                .filter(m -> m.isEnabled() && !m.getName().equals("HUD"))
                .toList();

        for (var m : enabledModules) {
            ctx.drawText(mc.textRenderer, Text.literal("§f" + m.getName()),
                    4, y, 0xFFFFFFFF, true);
            y += 10;
        }
    }
}
