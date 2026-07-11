package com.deepseek.client.gui;

import com.deepseek.client.DeepseekClientMod;
import com.deepseek.client.module.Category;
import com.deepseek.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.awt.Color;

public class GuiScreen extends Screen {
    private static final int PANEL_W = 120;
    private static final int PANEL_H = 22;
    private static final int COLUMNS = 5;
    private static final int PADDING = 4;

    private int panelX = 20, panelY = 20;
    private Category selectedCategory = Category.COMBAT;
    private final int[] colors = {0xFF6750A4, 0xFF0288D1, 0xFF2E7D32, 0xFFE65100, 0xFFC62828, 0xFF6A1B9A, 0xFF00695C};

    public GuiScreen() {
        super(Text.of(DeepseekClientMod.CLIENT_NAME));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx);
        int catY = panelY;

        // Category buttons
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++) {
            int color = colors[i % colors.length];
            drawButton(ctx, panelX, catY, PANEL_W, PANEL_H, cats[i].getIcon() + " " + cats[i].getName(),
                    cats[i] == selectedCategory, color, mouseX, mouseY);
            catY += PANEL_H + PADDING;
        }

        // Module grid
        var modules = DeepseekClientMod.getInstance().moduleManager.getModulesByCategory(selectedCategory);
        int gridX = panelX + PANEL_W + 10;
        int gridY = panelY;

        ctx.fill(gridX - 2, gridY - 2,
                gridX + COLUMNS * (PANEL_W + PADDING) + 2,
                gridY + ((modules.size() + COLUMNS - 1) / COLUMNS) * (PANEL_H + PADDING) + 2,
                0x80000000);

        int idx = 0;
        for (Module m : modules) {
            int col = idx % COLUMNS;
            int row = idx / COLUMNS;
            int x = gridX + col * (PANEL_W + PADDING);
            int y = gridY + row * (PANEL_H + PADDING);
            int moduleColor = colors[selectedCategory.ordinal() % colors.length];
            drawButton(ctx, x, y, PANEL_W, PANEL_H, m.getName(), m.isEnabled(), moduleColor, mouseX, mouseY);
            idx++;
        }

        // Info bar
        String info = String.format("%s v%s | Modules: %d/%d",
                DeepseekClientMod.CLIENT_NAME, DeepseekClientMod.VERSION,
                DeepseekClientMod.getInstance().moduleManager.getEnabledCount(),
                DeepseekClientMod.getInstance().moduleManager.getModules().size());
        ctx.fill(0, height - 30, width, height, 0xCC141218);
        ctx.drawText(textRenderer, info, 10, height - 20, 0xFFE6E0E9, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int catY = panelY;
        Category[] cats = Category.values();
        for (Category cat : cats) {
            if (isHovered(panelX, catY, PANEL_W, PANEL_H, mouseX, mouseY)) {
                selectedCategory = cat;
                return true;
            }
            catY += PANEL_H + PADDING;
        }

        var modules = DeepseekClientMod.getInstance().moduleManager.getModulesByCategory(selectedCategory);
        int gridX = panelX + PANEL_W + 10;
        int gridY = panelY;
        int idx = 0;
        for (Module m : modules) {
            int col = idx % COLUMNS;
            int row = idx / COLUMNS;
            int x = gridX + col * (PANEL_W + PADDING);
            int y = gridY + row * (PANEL_H + PADDING);
            if (isHovered(x, y, PANEL_W, PANEL_H, mouseX, mouseY)) {
                m.toggle();
                return true;
            }
            idx++;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 256) { // Enter or Escape
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void drawButton(DrawContext ctx, int x, int y, int w, int h, String text, boolean active, int color, int mx, int my) {
        boolean hover = isHovered(x, y, w, h, mx, my);
        int bg = active ? color : (hover ? 0x803B383E : 0x40211F26);
        int border = active ? color : (hover ? 0x60938F99 : 0x3049454F);
        ctx.fill(x, y, x + w, y + h, bg);
        ctx.fill(x, y, x + w, y + 1, border);
        ctx.fill(x, y + h - 1, x + w, y + h, border);
        ctx.fill(x, y, x + 1, y + h, border);
        ctx.fill(x + w - 1, y, x + w, y + h, border);
        int textColor = active ? 0xFFFFFFFF : 0xFFE6E0E9;
        ctx.drawTextWithShadow(textRenderer, text,
                x + (w - textRenderer.getWidth(text)) / 2,
                y + (h - textRenderer.fontHeight) / 2 + 1,
                textColor);
    }

    private boolean isHovered(int x, int y, int w, int h, double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public boolean shouldPause() { return false; }
}
