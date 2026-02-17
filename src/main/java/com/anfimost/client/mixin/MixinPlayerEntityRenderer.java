package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer {
    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD"), cancellable = true)
    private void onLabel(AbstractClientPlayerEntity player, Text text, MatrixStack mat, VertexConsumerProvider vcp, int light, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && player == mc.player) {
            Module m = AnfimostClient.getInstance().getModuleManager().getByName("Self Nametag");
            if (m != null && m.isEnabled()) {
                ci.cancel();
                ((com.anfimost.client.module.visuals.SelfNametag)m).renderCustomLabel(player, mat, vcp, light);
            }
        }
    }
}
