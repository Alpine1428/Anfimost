package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import java.awt.Color;

public class WorldCustomizer extends Module {
    public enum Preset { CUSTOM, SOFT, VIBRANT, COLD, WARM }
    private final Setting<Preset> preset=addSetting(new Setting<>("Preset",Preset.CUSTOM,Preset.class));
    private final ColorSetting fog=(ColorSetting)addSetting(new ColorSetting("Fog Color",180,200,255,255));

    public WorldCustomizer() { super("World Customizer","World colors",ModuleCategory.VISUALS); }

    public Color getFogColor() {
        switch(preset.getValue()) { case SOFT:return new Color(200,210,220); case VIBRANT:return new Color(100,180,255); case COLD:return new Color(180,200,240); case WARM:return new Color(255,200,150); case CUSTOM:return fog.getColor(); }
        return null;
    }
}
