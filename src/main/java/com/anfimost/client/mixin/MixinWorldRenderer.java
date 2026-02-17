package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onWeather(LightmapTextureManager mgr, float td, double cx, double cy, double cz, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Render Tweaks");
        if (m != null && m.isEnabled() && ((com.anfimost.client.module.visuals.RenderTweaks)m).isWeatherDisabled()) ci.cancel();
    }
}
