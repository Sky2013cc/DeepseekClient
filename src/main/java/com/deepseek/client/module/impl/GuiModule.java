package com.deepseek.client.module.impl;

import com.deepseek.client.DeepseekClientMod;
import com.deepseek.client.gui.GuiScreen;
import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class GuiModule extends Module {
    public GuiModule() { super("ClickGUI", "打开图形界面", Category.MISC, 0); }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.setScreen(new GuiScreen());
        }
        setEnabled(false); // 立即关闭，不让它保持开启状态
    }
}
