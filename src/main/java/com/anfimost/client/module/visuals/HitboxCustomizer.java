package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class HitboxCustomizer extends Module {
    private final Setting<Float> ex=addSetting(new Setting<>("Expand X",0f,-1f,2f,Float.class));
    private final Setting<Float> ey=addSetting(new Setting<>("Expand Y",0f,-1f,2f,Float.class));
    private final Setting<Float> ez=addSetting(new Setting<>("Expand Z",0f,-1f,2f,Float.class));
    private final ColorSetting color=(ColorSetting)addSetting(new ColorSetting("Color",255,255,255,180));
    private final Setting<Float> lw=addSetting(new Setting<>("Width",1.5f,0.5f,5f,Float.class));
    private final Setting<Boolean> only=addSetting(new Setting<>("Only Players",false,Boolean.class));

    public HitboxCustomizer() { super("Hitbox Customizer","Custom hitboxes",ModuleCategory.VISUALS); }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if(mc.world==null||mc.player==null) return;
        float td=ctx.tickDelta();
        for(Entity e:mc.world.getEntities()) {
            if(e==mc.player||!(e instanceof LivingEntity)) continue;
            if(only.getValue()&&!(e instanceof PlayerEntity)) continue;
            double x=e.prevX+(e.getX()-e.prevX)*td, y=e.prevY+(e.getY()-e.prevY)*td, z=e.prevZ+(e.getZ()-e.prevZ)*td;
            Box b=e.getBoundingBox().expand(ex.getValue(),ey.getValue(),ez.getValue()).offset(x-e.getX(),y-e.getY(),z-e.getZ());
            RenderUtils.drawBox(ctx.matrixStack(),b,color.getColor(),lw.getValue());
        }
    }
}
