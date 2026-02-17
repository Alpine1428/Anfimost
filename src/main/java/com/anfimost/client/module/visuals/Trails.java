package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.render.RenderUtils;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import java.util.*;

public class Trails extends Module {
    public enum CMode { STATIC, RAINBOW }
    private final Setting<CMode> cm=addSetting(new Setting<>("Color",CMode.STATIC,CMode.class));
    private final ColorSetting color=(ColorSetting)addSetting(new ColorSetting("Color",150,50,255,200));
    private final Setting<Float> w=addSetting(new Setting<>("Width",2f,0.5f,5f,Float.class));
    private final Setting<Integer> max=addSetting(new Setting<>("Points",100,20,500,Integer.class));
    private final LinkedList<Vec3d> pts=new LinkedList<>();

    public Trails() { super("Trails","Trail behind player",ModuleCategory.VISUALS); }
    @Override public void onDisable() { pts.clear(); }

    @Override public void onTick() {
        if(mc.player==null) return;
        Vec3d p=mc.player.getPos().add(0,0.1,0);
        if(pts.isEmpty()||pts.getLast().distanceTo(p)>0.05) pts.add(p);
        while(pts.size()>max.getValue()) pts.removeFirst();
    }

    @Override public void onRenderWorld(WorldRenderContext ctx) {
        if(pts.size()<2) return;
        Color c=cm.getValue()==CMode.RAINBOW?new Color(RenderUtils.getRainbowColor(0).getRGB()&0xFFFFFF|0xC8000000,true):color.getColor();
        RenderUtils.drawTrailLine(ctx.matrixStack(),new ArrayList<>(pts),c,w.getValue());
    }
}
