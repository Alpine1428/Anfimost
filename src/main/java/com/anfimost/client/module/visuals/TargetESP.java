package com.anfimost.client.module.visuals;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.event.AttackEntityEvent;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import java.awt.Color;

public class TargetESP extends Module {
    public enum Mode { BOX, GLOW }
    private final Setting<Mode> mode=addSetting(new Setting<>("Mode",Mode.BOX,Mode.class));
    private final ColorSetting color=(ColorSetting)addSetting(new ColorSetting("Color",255,50,50,180));
    private final Setting<Float> lw=addSetting(new Setting<>("Width",2f,0.5f,5f,Float.class));
    private final Setting<Integer> fade=addSetting(new Setting<>("Fade",3000,500,10000,Integer.class));
    private Entity target; private long hitTime;

    public TargetESP() { super("Target ESP","Highlight target",ModuleCategory.VISUALS); }

    @Override public void onEnable() { AnfimostClient.getInstance().getEventBus().register(AttackEntityEvent.class,e->{ if(isEnabled()){target=e.getTarget();hitTime=System.currentTimeMillis();} }); }
    @Override public void onDisable() { target=null; }
    @Override public void onTick() { if(target!=null&&(System.currentTimeMillis()-hitTime>fade.getValue()||!target.isAlive())) target=null; }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if(target==null||!(target instanceof LivingEntity)) return;
        float td=ctx.tickDelta();
        double x=target.prevX+(target.getX()-target.prevX)*td,y=target.prevY+(target.getY()-target.prevY)*td,z=target.prevZ+(target.getZ()-target.prevZ)*td;
        float p=Math.max(0,Math.min(1,1f-(float)(System.currentTimeMillis()-hitTime)/fade.getValue()));
        Color c=color.getColor(); Color dc=new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(1,(int)(c.getAlpha()*p)));
        Box b=target.getBoundingBox().offset(x-target.getX(),y-target.getY(),z-target.getZ());
        if(mode.getValue()==Mode.GLOW) RenderUtils.drawFilledBox(ctx.matrixStack(),b,new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(1,(int)(40*p))));
        RenderUtils.drawBox(ctx.matrixStack(),b,dc,lw.getValue());
    }
}
