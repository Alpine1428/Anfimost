package com.anfimost.client.module.visuals;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.event.AttackEntityEvent;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

public class HitBubble extends Module {
    private final ColorSetting color = (ColorSetting)addSetting(new ColorSetting("Color",100,200,255,180));
    private final Setting<Integer> dur = addSetting(new Setting<>("Duration",400,100,2000,Integer.class));
    private final Setting<Float> sr = addSetting(new Setting<>("Start R",0.3f,0.1f,2f,Float.class));
    private final Setting<Float> er = addSetting(new Setting<>("End R",1.5f,0.5f,5f,Float.class));
    private final Setting<Float> lw = addSetting(new Setting<>("Width",2f,0.5f,5f,Float.class));
    private final CopyOnWriteArrayList<long[]> fx = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Vec3d> pos = new CopyOnWriteArrayList<>();

    public HitBubble() { super("Hit Bubble","Circle on hit",ModuleCategory.VISUALS); }

    @Override public void onEnable() {
        AnfimostClient.getInstance().getEventBus().register(AttackEntityEvent.class,e->{
            if(!isEnabled()) return;
            var t=e.getTarget(); pos.add(t.getPos().add(0,t.getHeight()/2,0)); fx.add(new long[]{System.currentTimeMillis()});
        });
    }
    @Override public void onDisable() { fx.clear(); pos.clear(); }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        long now=System.currentTimeMillis();
        for(int i=fx.size()-1;i>=0;i--) {
            float prog=(float)(now-fx.get(i)[0])/dur.getValue();
            if(prog>1) { fx.remove(i); pos.remove(i); continue; }
            float r=sr.getValue()+(er.getValue()-sr.getValue())*prog;
            Color c=color.getColor();
            RenderUtils.drawCircle3D(ctx.matrixStack(),pos.get(i).x,pos.get(i).y,pos.get(i).z,r,36,new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(1,(int)((1f-prog)*c.getAlpha()))),lw.getValue());
        }
    }
}
