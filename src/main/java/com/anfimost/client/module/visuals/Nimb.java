package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import java.awt.Color;

public class Nimb extends Module {
    public enum CMode { STATIC, RAINBOW }
    private final Setting<CMode> cm=addSetting(new Setting<>("Color Mode",CMode.STATIC,CMode.class));
    private final ColorSetting color=(ColorSetting)addSetting(new ColorSetting("Color",255,215,0,150));
    private final Setting<Float> radius=addSetting(new Setting<>("Radius",0.55f,0.2f,1.5f,Float.class));
    private final Setting<Float> yOff=addSetting(new Setting<>("Y Offset",0.15f,-0.5f,1f,Float.class));
    private final Setting<Boolean> pulse=addSetting(new Setting<>("Pulse",true,Boolean.class));

    public Nimb() { super("Nimb","Halo above head",ModuleCategory.VISUALS); }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if(mc.player==null||mc.options.getPerspective().isFirstPerson()) return;
        float td=ctx.tickDelta();
        double x=mc.player.prevX+(mc.player.getX()-mc.player.prevX)*td;
        double y=mc.player.prevY+(mc.player.getY()-mc.player.prevY)*td+mc.player.getHeight()+yOff.getValue();
        double z=mc.player.prevZ+(mc.player.getZ()-mc.player.prevZ)*td;
        Color c=cm.getValue()==CMode.RAINBOW?new Color(RenderUtils.getRainbowColor(0).getRGB()&0xFFFFFF|0x96000000,true):color.getColor();
        float r=radius.getValue(); if(pulse.getValue()) r*=(float)(Math.sin(System.currentTimeMillis()/500.0)*0.05+1);
        RenderUtils.drawCircle3D(ctx.matrixStack(),x,y,z,r,40,c,2.5f);
        RenderUtils.drawCircle3D(ctx.matrixStack(),x,y+0.02,z,r*0.85f,40,new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(1,c.getAlpha()/2)),1.5f);
    }
}
