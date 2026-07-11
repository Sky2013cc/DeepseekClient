package com.deepseek.client.gui;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class DeepseekSplashOverlay extends Overlay {
    private static final Text TITLE = Text.of(DeepseekClientMod.CLIENT_NAME);
    private static final Text SUBTITLE = Text.of("Loading...");
    private final MinecraftClient client;
    private long startTime = -1;

    public DeepseekSplashOverlay(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        if (startTime == -1) startTime = Util.getMeasuringTimeMs();

        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        // 背景 — Material 3 渐变
        int bgTop = 0xFF141218;
        int bgBottom = 0xFF1D192B;
        ctx.fill(0, 0, w, h, bgTop);

        // Logo 区域 — 大号深色发光圆
        int cx = w / 2;
        int cy = h / 2 - 40;
        int r = 48;

        // 外圈发光
        ctx.fill(cx - r - 4, cy - r - 4, cx + r + 4, cy + r + 4, 0x306750A4);
        // 内圈
        ctx.fill(cx - r, cy - r, cx + r, cy + r, 0xFF6750A4);
        // 中间文字
        ctx.drawText(client.textRenderer, "DS", cx - 8, cy - 4, 0xFFFFFFFF, true);

        // 标题
        int titleW = client.textRenderer.getWidth(TITLE);
        ctx.drawText(client.textRenderer, TITLE, (w - titleW) / 2, cy + r + 20, 0xFFE6E0E9, true);

        // 副标题
        int subW = client.textRenderer.getWidth(SUBTITLE);
        ctx.drawText(client.textRenderer, SUBTITLE, (w - subW) / 2, cy + r + 40, 0xFF938F99, true);

        // 进度条背景
        int barW = 200;
        int barH = 4;
        int barX = (w - barW) / 2;
        int barY = cy + r + 60;
        ctx.fill(barX, barY, barX + barW, barY + barH, 0x3049454F);

        // 进度条动画
        long elapsed = Util.getMeasuringTimeMs() - startTime;
        float progress = Math.min(1.0f, (elapsed % 3000) / 3000f);
        int fillW = (int) (barW * progress);
        ctx.fill(barX, barY, barX + fillW, barY + barH, 0xFFD0BCFF);

        // 底部信息
        String bottom = "v" + DeepseekClientMod.VERSION + " | Fabric 1.20.1";
        int botW = client.textRenderer.getWidth(bottom);
        ctx.drawText(client.textRenderer, bottom, (w - botW) / 2, h - 30, 0xFF49454F, false);
    }

    @Override
    public boolean pausesGame() { return true; }
}
