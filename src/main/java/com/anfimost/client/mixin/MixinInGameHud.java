package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshair(DrawContext context, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Crosshair");
        if (m != null && m.isEnabled()) ci.cancel();
    }

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderOverlay(DrawContext context, Identifier texture, float opacity, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        String path = texture.getPath();
        Module rt = AnfimostClient.getInstance().getModuleManager().getByName("Render Tweaks");
        if (rt != null && rt.isEnabled()) {
            com.anfimost.client.module.visuals.RenderTweaks r = (com.anfimost.client.module.visuals.RenderTweaks) rt;
            if (path.contains("pumpkinblur") && r.isPumpkinDisabled()) { ci.cancel(); return; }
        }
        Module nf = AnfimostClient.getInstance().getModuleManager().getByName("No Fluid");
        if (nf != null && nf.isEnabled()) {
            if (((com.anfimost.client.module.visuals.NoFluid)nf).isRemoveOverlay() && (path.contains("underwater")||path.contains("water"))) ci.cancel();
        }
    }
}
