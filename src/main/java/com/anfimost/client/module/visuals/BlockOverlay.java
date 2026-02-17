package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import java.awt.Color;

public class BlockOverlay extends Module {
    public enum Mode { OUTLINE, FILLED, FLAT }
    private final Setting<Mode> mode = addSetting(new Setting<>("Mode",Mode.OUTLINE,Mode.class));
    private final ColorSetting color = (ColorSetting)addSetting(new ColorSetting("Color",100,100,255,120));
    private final Setting<Float> lineWidth = addSetting(new Setting<>("Width",2f,0.5f,5f,Float.class));
    private final Setting<Boolean> animated = addSetting(new Setting<>("Animated",true,Boolean.class));

    public BlockOverlay() { super("Block Overlay","Highlights targeted block",ModuleCategory.VISUALS); }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if (mc.crosshairTarget==null||mc.crosshairTarget.getType()!=HitResult.Type.BLOCK) return;
        var pos = ((BlockHitResult)mc.crosshairTarget).getBlockPos();
        Color c = color.getColor();
        if (animated.getValue()) { float p=(float)(Math.sin(System.currentTimeMillis()/300.0)*0.3+0.7); c=new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(10,(int)(c.getAlpha()*p))); }
        Box box = new Box(pos);
        switch(mode.getValue()) {
            case OUTLINE: RenderUtils.drawBox(ctx.matrixStack(),box,c,lineWidth.getValue()); break;
            case FILLED: RenderUtils.drawFilledBox(ctx.matrixStack(),box,c); RenderUtils.drawBox(ctx.matrixStack(),box,new Color(c.getRed(),c.getGreen(),c.getBlue(),255),lineWidth.getValue()); break;
            case FLAT: RenderUtils.drawFilledBox(ctx.matrixStack(),new Box(pos.getX(),pos.getY()+1.002,pos.getZ(),pos.getX()+1,pos.getY()+1.002,pos.getZ()+1),c); break;
        }
    }
}
