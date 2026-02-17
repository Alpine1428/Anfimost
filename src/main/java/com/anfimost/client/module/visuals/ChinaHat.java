package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import java.awt.Color;

public class ChinaHat extends Module {
    public enum Target { SELF, ALL }
    public enum CMode { STATIC, RAINBOW }
    private final Setting<Target> target = addSetting(new Setting<>("Target",Target.SELF,Target.class));
    private final Setting<CMode> cmode = addSetting(new Setting<>("Color Mode",CMode.STATIC,CMode.class));
    private final ColorSetting color = (ColorSetting)addSetting(new ColorSetting("Color",120,50,255,150));
    private final Setting<Float> radius = addSetting(new Setting<>("Radius",0.7f,0.2f,2f,Float.class));
    private final Setting<Float> yOff = addSetting(new Setting<>("Y Offset",0.1f,-0.5f,1f,Float.class));
    private final Setting<Integer> segs = addSetting(new Setting<>("Segments",32,8,64,Integer.class));

    public ChinaHat() { super("China Hat","Disc above heads",ModuleCategory.VISUALS); }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if (mc.world==null||mc.player==null) return;
        float td = ctx.tickDelta();
        if (target.getValue()==Target.SELF) { if(!mc.options.getPerspective().isFirstPerson()) hat(mc.player,td,ctx); }
        else { for(var p:mc.world.getPlayers()) { if(p==mc.player&&mc.options.getPerspective().isFirstPerson()) continue; hat(p,td,ctx); } }
    }

    private void hat(PlayerEntity p, float td, WorldRenderContext ctx) {
        double x=p.prevX+(p.getX()-p.prevX)*td, y=p.prevY+(p.getY()-p.prevY)*td+p.getHeight()+yOff.getValue(), z=p.prevZ+(p.getZ()-p.prevZ)*td;
        Color c = cmode.getValue()==CMode.RAINBOW ? new Color(RenderUtils.getRainbowColor(0).getRGB()&0xFFFFFF|0x96000000,true) : color.getColor();
        RenderUtils.drawFilledCircle3D(ctx.matrixStack(),x,y,z,radius.getValue(),segs.getValue(),c);
        RenderUtils.drawCircle3D(ctx.matrixStack(),x,y,z,radius.getValue(),segs.getValue(),new Color(c.getRed(),c.getGreen(),c.getBlue(),255),2f);
    }
}
