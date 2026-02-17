package com.anfimost.client.module.visuals;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.event.AttackEntityEvent;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.minecraft.client.gui.DrawContext;
import java.awt.Color;

public class CrosshairModule extends Module {
    private final ColorSetting color = (ColorSetting)addSetting(new ColorSetting("Color",255,255,255,255));
    private final Setting<Boolean> outline = addSetting(new Setting<>("Outline",true,Boolean.class));
    private final ColorSetting outColor = (ColorSetting)addSetting(new ColorSetting("Out Color",0,0,0,200));
    private final Setting<Float> size = addSetting(new Setting<>("Size",6f,2f,20f,Float.class));
    private final Setting<Float> gap = addSetting(new Setting<>("Gap",3f,0f,10f,Float.class));
    private final Setting<Float> thick = addSetting(new Setting<>("Thickness",1f,1f,5f,Float.class));
    private final Setting<Boolean> dynamic = addSetting(new Setting<>("Dynamic",true,Boolean.class));
    private final Setting<Boolean> hitAnim = addSetting(new Setting<>("Hit Anim",true,Boolean.class));
    private long lastHit=0; private float dynGap=0;

    public CrosshairModule() { super("Crosshair","Custom crosshair",ModuleCategory.VISUALS); }

    @Override public void onEnable() { AnfimostClient.getInstance().getEventBus().register(AttackEntityEvent.class,e->{ if(isEnabled()) lastHit=System.currentTimeMillis(); }); }
    @Override public void onTick() { dynGap = dynamic.getValue()&&mc.player!=null ? (float)(mc.player.getVelocity().horizontalLength()*15) : 0; }

    @Override public void onRenderHud(DrawContext ctx, float td) {
        if(mc.player==null) return;
        int cx=mc.getWindow().getScaledWidth()/2, cy=mc.getWindow().getScaledHeight()/2;
        int s=(int)size.getValue(), g=(int)(gap.getValue()+dynGap), t=Math.max(1,(int)thick.getValue());
        boolean hit = hitAnim.getValue()&&(System.currentTimeMillis()-lastHit<200);
        int col = (hit?new Color(255,50,50):color.getColor()).getRGB();
        if(outline.getValue()) lines(ctx,cx,cy,s+1,Math.max(0,g-1),t+2,outColor.getColor().getRGB());
        lines(ctx,cx,cy,s,g,t,col);
        if(hit) ctx.fill(cx-1,cy-1,cx+2,cy+2,0xFFFF0000);
    }

    private void lines(DrawContext c,int cx,int cy,int s,int g,int t,int col) {
        int h=t/2;
        c.fill(cx-h,cy-g-s,cx+h+1,cy-g,col); c.fill(cx-h,cy+g+1,cx+h+1,cy+g+s+1,col);
        c.fill(cx-g-s,cy-h,cx-g,cy+h+1,col); c.fill(cx+g+1,cy-h,cx+g+s+1,cy+h+1,col);
    }
}
