package com.deepseek.client.module.impl.combat;

import com.deepseek.client.module.Module;
import com.deepseek.client.module.Category;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AntiKnockback extends Module {
    public AntiKnockback() {
        super("AntiKnockback", "Grim-bypass velocity", Category.COMBAT, 0);
    }

    // Use mixin to reduce velocity to 40% instead of 0% (Grim checks velocity 0)
    // This is handled in MixinEntity.java
}
