package com.mrbreaknfix.mixin;

import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;

@Mixin(ResourceTexture.class)
public class ResourceTextureTextureDataMixin {
    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/ResourceTexture;loadTextureData(Lnet/minecraft/resource/ResourceManager;)Lnet/minecraft/client/texture/ResourceTexture$TextureData;"))
    private ResourceTexture.TextureData redirectLoad(ResourceTexture resourceTexture, ResourceManager resourceManager) {
        try {
            Resource resource = resourceManager.getResourceOrThrow(new Identifier("textures/gui/title/background/panorama_1.png"));
            InputStream inputStream = resource.getInputStream();

            NativeImage nativeImage;
            try {
                nativeImage = NativeImage.read(inputStream);
            } catch (Throwable var9) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var7) {
                        var9.addSuppressed(var7);
                    }
                }

                throw var9;
            }

            if (inputStream != null) {
                inputStream.close();
            }

            TextureResourceMetadata textureResourceMetadata = null;

            try {
                textureResourceMetadata = (TextureResourceMetadata)resource.getMetadata().decode(TextureResourceMetadata.READER).orElse(null);
            } catch (RuntimeException var8) {
                System.out.println("yes texture is failing stuff");
            }

            return new ResourceTexture.TextureData(textureResourceMetadata, nativeImage);
        } catch (IOException var10) {
            return new ResourceTexture.TextureData(var10);
        }
    }
}
