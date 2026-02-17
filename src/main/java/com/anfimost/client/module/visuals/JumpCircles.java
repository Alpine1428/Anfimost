package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

public class JumpCircles extends Module {
    private final ColorSetting color=(ColorSetting)addSetting(new ColorSetting("Color",150,100,255,200));
    private final Setting<Integer> dur=addSetting(new Setting<>("Duration",500,100,2000,Integer.class));
    private final Setting<Float> sr=addSetting(new Setting<>("Start R",0.2f,0.1f,1f,Float.class));
    private final Setting<Float> er=addSetting(new Setting<>("End R",1.5f,0.5f,5f,Float.class));
    private final Setting<Float> lw=addSetting(new Setting<>("Width",2f,0.5f,5f,Float.class));
    private final Setting<Integer> cnt=addSetting(new Setting<>("Count",2,1,5,Integer.class));
    private final CopyOnWriteArrayList<Object[]> fx=new CopyOnWriteArrayList<>();
    private boolean wasGround=true;

    public JumpCircles() { super("Jump Circles","Circles on jump",ModuleCategory.VISUALS); }
    @Override public void onDisable() { fx.clear(); wasGround=true; }

    @Override public void onTick() {
        if(mc.player==null) return;
        boolean g=mc.player.isOnGround();
        if((wasGround&&!g)||(!wasGround&&g)) { long n=System.currentTimeMillis(); for(int i=0;i<cnt.getValue();i++) fx.add(new Object[]{mc.player.getPos(),n+i*80L}); }
        wasGround=g;
    }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        long now=System.currentTimeMillis();
        fx.removeIf(o->now-(long)o[1]>dur.getValue());
        for(Object[] o:fx) {
            if(now<(long)o[1]) continue;
            float p=(float)(now-(long)o[1])/dur.getValue();
            float r=sr.getValue()+(er.getValue()-sr.getValue())*p;
            Color c=color.getColor();
            RenderUtils.drawCircle3D(ctx.matrixStack(),((Vec3d)o[0]).x,((Vec3d)o[0]).y+0.02,((Vec3d)o[0]).z,r,32,new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(1,(int)((1-p)*c.getAlpha()))),lw.getValue());
        }
    }
}
