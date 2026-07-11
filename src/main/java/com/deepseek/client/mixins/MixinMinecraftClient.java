package com.deepseek.client.mixins;

import com.deepseek.client.DeepseekClientMod;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class MixinMinecraftClient {

    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> ci) {
        if (keyCode == 257 || keyCode == 335) { // Enter key
            String text = this.chatField.getText();
            if (text != null && text.startsWith(".")) {
                DeepseekClientMod.getInstance().commandManager.execute(text);
                ci.setReturnValue(true);
            }
        }
    }
}
