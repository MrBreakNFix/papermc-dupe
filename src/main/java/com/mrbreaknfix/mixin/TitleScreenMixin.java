package com.mrbreaknfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(at = @At("RETURN"), method = "render")
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Text message = Text.literal("Happy April Fools, " + System.getProperty("user.name") + "!");
        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;
        centerY -= context.getScaledWindowHeight() / 3;

        context.getMatrices().scale(3.0F, 3.0F, 3.0F);

        centerX /= 3;
        centerY /= 3;

        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, message, centerX, centerY, 0xFF0000);
    }
}
