package com.mrbreaknfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        player.headYaw = (player.age + MinecraftClient.getInstance().getTickDelta()) % 360;
        player.prevYaw = (player.age + MinecraftClient.getInstance().getTickDelta()) % 360;
        player.prevHeadYaw = (player.age + MinecraftClient.getInstance().getTickDelta()) % 360;
    }
}
