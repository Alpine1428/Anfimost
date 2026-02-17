package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinFogRenderer {
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void onFog(Camera cam, BackgroundRenderer.FogType ft, float vd, boolean thick, float td, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module nf = AnfimostClient.getInstance().getModuleManager().getByName("No Fluid");
        if (nf != null && nf.isEnabled() && ((com.anfimost.client.module.visuals.NoFluid)nf).isNoFogInFluid()
            && cam.getSubmersionType() != CameraSubmersionType.NONE) {
            RenderSystem.setShaderFogStart(-8f);
            RenderSystem.setShaderFogEnd(1000000f);
        }
        Module wc = AnfimostClient.getInstance().getModuleManager().getByName("World Customizer");
        if (wc != null && wc.isEnabled()) {
            java.awt.Color fc = ((com.anfimost.client.module.visuals.WorldCustomizer)wc).getFogColor();
            if (fc != null) RenderSystem.setShaderFogColor(fc.getRed()/255f, fc.getGreen()/255f, fc.getBlue()/255f);
        }
    }
}
