package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void onRender(AbstractClientPlayerEntity player, float td, float pitch, Hand hand,
                           float swing, ItemStack item, float equip, MatrixStack mat,
                           VertexConsumerProvider vcp, int light, CallbackInfo ci) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Custom Hand");
        if (m != null && m.isEnabled()) ((com.anfimost.client.module.visuals.CustomHand)m).transformHand(mat, hand);
    }
}
