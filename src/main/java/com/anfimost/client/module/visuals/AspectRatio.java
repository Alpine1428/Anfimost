package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import net.minecraft.client.gui.DrawContext;

public class AspectRatio extends Module {
    public enum Mode { NATIVE, CUSTOM }
    private final Setting<Mode> mode = addSetting(new Setting<>("Mode", Mode.NATIVE, Mode.class));
    private final Setting<Float> ratioW = addSetting(new Setting<>("Width", 16f,1f,32f,Float.class).withVisibility(()->mode.getValue()==Mode.CUSTOM));
    private final Setting<Float> ratioH = addSetting(new Setting<>("Height", 9f,1f,32f,Float.class).withVisibility(()->mode.getValue()==Mode.CUSTOM));
    private final Setting<Boolean> bars = addSetting(new Setting<>("Black Bars", true, Boolean.class).withVisibility(()->mode.getValue()==Mode.CUSTOM));

    public AspectRatio() { super("Aspect Ratio","Changes aspect ratio",ModuleCategory.VISUALS); }

    @Override public void onRenderHud(DrawContext ctx, float td) {
        if (mode.getValue()!=Mode.CUSTOM||!bars.getValue()) return;
        int sw=mc.getWindow().getScaledWidth(), sh=mc.getWindow().getScaledHeight();
        float target=ratioW.getValue()/ratioH.getValue(), current=(float)sw/sh;
        if (Math.abs(target-current)<0.01f) return;
        if (target<current) { int bw=(sw-(int)(sh*target))/2; ctx.fill(0,0,bw,sh,0xFF000000); ctx.fill(sw-bw,0,sw,sh,0xFF000000); }
        else { int bh=(sh-(int)(sw/target))/2; ctx.fill(0,0,sw,bh,0xFF000000); ctx.fill(0,sh-bh,sw,sh,0xFF000000); }
    }
}
