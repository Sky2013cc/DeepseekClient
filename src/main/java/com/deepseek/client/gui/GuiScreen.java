package com.deepseek.client.gui;

import com.deepseek.client.DeepseekClientMod;
import com.deepseek.client.module.Category;
import com.deepseek.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class GuiScreen extends Screen {
    private static final int TAB_H = 30;
    private static final int TAB_PAD = 4;
    private static final int PANEL_W = 110;
    private static final int PANEL_H = 28;
    private static final int COLUMNS = 5;
    private Category selected = Category.COMBAT;

    private static final int BG = 0xCC141218;
    private static final int[] COLORS = {
        0xFF6750A4, 0xFF0288D1, 0xFF2E7D32, 0xFFE65100, 0xFFC62828, 0xFF6A1B9A
    };

    public GuiScreen() { super(Text.of(DeepseekClientMod.CLIENT_NAME)); }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        renderBackground(ctx);

        int tabX = 15;
        Category[] cats = Category.values();

        // 顶部分类标签
        for (int i = 0; i < cats.length; i++) {
            boolean sel = cats[i] == selected;
            int c = COLORS[i % COLORS.length];
            int tw = textRenderer.getWidth(cats[i].getIcon() + " " + cats[i].getName()) + 20;

            ctx.fill(tabX, 15, tabX + tw, 15 + TAB_H, sel ? c : 0x603B383E);
            if (sel) ctx.fill(tabX, 15 + TAB_H - 2, tabX + tw, 15 + TAB_H, c);
            ctx.drawText(textRenderer, cats[i].getIcon() + " " + cats[i].getName(),
                    tabX + 10, 15 + (TAB_H - textRenderer.fontHeight) / 2, 0xFFFFFFFF, true);
            tabX += tw + TAB_PAD;
        }

        // 模块网格
        var modules = DeepseekClientMod.getInstance().moduleManager.getModulesByCategory(selected);
        int gridX = 15;
        int gridY = 15 + TAB_H + 15;
        int catColor = COLORS[selected.ordinal() % COLORS.length];

        // 背景面板
        int rows = (modules.size() + COLUMNS - 1) / COLUMNS;
        ctx.fill(gridX - 2, gridY - 2, gridX + COLUMNS * (PANEL_W + 4) + 2,
                gridY + rows * (PANEL_H + 4) + 2, 0x80211F26);

        int idx = 0;
        for (Module m : modules) {
            int col = idx % COLUMNS;
            int row = idx / COLUMNS;
            int x = gridX + col * (PANEL_W + 4);
            int y = gridY + row * (PANEL_H + 4);
            boolean hover = mx >= x && mx <= x + PANEL_W && my >= y && my <= y + PANEL_H;

            int bg = m.isEnabled() ? catColor : (hover ? 0x803B383E : 0x40211F26);
            int border = m.isEnabled() ? catColor : 0x00000000;
            ctx.fill(x, y, x + PANEL_W, y + PANEL_H, bg);
            ctx.fill(x, y, x + PANEL_W, y + 1, border);
            ctx.fill(x, y + PANEL_H - 1, x + PANEL_W, y + PANEL_H, border);

            ctx.drawText(textRenderer, m.getName(), x + 8,
                    y + (PANEL_H - textRenderer.fontHeight) / 2,
                    m.isEnabled() ? 0xFFFFFFFF : 0xFFCAC4D0, true);
            idx++;
        }

        // 底部信息栏
        String info = String.format("%s v%s | %d/%d modules",
                DeepseekClientMod.CLIENT_NAME, DeepseekClientMod.VERSION,
                DeepseekClientMod.getInstance().moduleManager.getEnabledCount(),
                DeepseekClientMod.getInstance().moduleManager.getModules().size());
        ctx.fill(0, height - 24, width, height, BG);
        ctx.drawText(textRenderer, info, 10, height - 17, 0xFFE6E0E9, false);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int tabX = 15;
        Category[] cats = Category.values();
        for (Category cat : cats) {
            int tw = textRenderer.getWidth(cat.getIcon() + " " + cat.getName()) + 20;
            if (mx >= tabX && mx <= tabX + tw && my >= 15 && my <= 15 + TAB_H) {
                selected = cat;
                return true;
            }
            tabX += tw + TAB_PAD;
        }

        var modules = DeepseekClientMod.getInstance().moduleManager.getModulesByCategory(selected);
        int gridX = 15;
        int gridY = 15 + TAB_H + 15;
        int idx = 0;
        for (Module m : modules) {
            int col = idx % COLUMNS;
            int row = idx / COLUMNS;
            int x = gridX + col * (PANEL_W + 4);
            int y = gridY + row * (PANEL_H + 4);
            if (mx >= x && mx <= x + PANEL_W && my >= y && my <= y + PANEL_H) {
                m.toggle();
                return true;
            }
            idx++;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { close(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override public boolean shouldPause() { return false; }
}
