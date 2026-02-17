package com.anfimost.client.mixin;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    private void onTime(CallbackInfoReturnable<Long> cir) {
        if (AnfimostClient.getInstance() == null) return;
        Module m = AnfimostClient.getInstance().getModuleManager().getByName("Time Changer");
        if (m != null && m.isEnabled()) cir.setReturnValue((long)((com.anfimost.client.module.visuals.TimeChanger)m).getClientTime());
    }
}
