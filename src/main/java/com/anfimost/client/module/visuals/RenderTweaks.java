package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;

public class RenderTweaks extends Module {
    private final Setting<Boolean> fire=addSetting(new Setting<>("No Fire",true,Boolean.class));
    private final Setting<Boolean> hurt=addSetting(new Setting<>("No Hurt Cam",true,Boolean.class));
    private final Setting<Boolean> bob=addSetting(new Setting<>("No View Bob",false,Boolean.class));
    private final Setting<Boolean> pump=addSetting(new Setting<>("No Pumpkin",true,Boolean.class));
    private final Setting<Boolean> nausea=addSetting(new Setting<>("No Nausea",true,Boolean.class));
    private final Setting<Boolean> weather=addSetting(new Setting<>("No Weather",false,Boolean.class));

    public RenderTweaks() { super("Render Tweaks","Visual QoL",ModuleCategory.VISUALS); }
    public boolean isFireOverlayDisabled() { return fire.getValue(); }
    public boolean isHurtCamDisabled() { return hurt.getValue(); }
    public boolean isViewBobbingDisabled() { return bob.getValue(); }
    public boolean isPumpkinDisabled() { return pump.getValue(); }
    public boolean isNauseaDisabled() { return nausea.getValue(); }
    public boolean isWeatherDisabled() { return weather.getValue(); }
}
