package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;

public class NoFluid extends Module {
    private final Setting<Boolean> overlay=addSetting(new Setting<>("Remove Overlay",true,Boolean.class));
    private final Setting<Float> opacity=addSetting(new Setting<>("Opacity",0f,0f,1f,Float.class));
    private final Setting<Boolean> noFog=addSetting(new Setting<>("No Fog",true,Boolean.class));
    public NoFluid() { super("No Fluid","Remove fluid effects",ModuleCategory.VISUALS); }
    public boolean isRemoveOverlay() { return overlay.getValue(); }
    public float getOpacity() { return opacity.getValue(); }
    public boolean isNoFogInFluid() { return noFog.getValue(); }
}
