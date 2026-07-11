package com.deepseek.client.module.impl.movement;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;

public class NoSlowdown extends Module {
    public NoSlowdown() { super("NoSlowdown", "使用物品时不减速", Category.MOVEMENT, 0); }
}
