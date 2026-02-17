package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void onHurtCam(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Render Tweaks");
        if (m != null && m.isEnabled() && ((com.anfimost.client.module.visuals.RenderTweaks)m).isHurtCamDisabled()) ci.cancel();
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void onBobView(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Render Tweaks");
        if (m != null && m.isEnabled() && ((com.anfimost.client.module.visuals.RenderTweaks)m).isViewBobbingDisabled()) ci.cancel();
    }
}
