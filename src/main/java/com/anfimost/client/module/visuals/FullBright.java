package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import net.minecraft.client.option.SimpleOption;
import java.lang.reflect.Field;

public class FullBright extends Module {
    private double prev = 1.0;
    public FullBright() { super("Full Bright","Max brightness",ModuleCategory.VISUALS); }

    @Override public void onEnable() { if(mc.options!=null) { prev=mc.options.getGamma().getValue(); setGamma(16); } }
    @Override public void onDisable() { if(mc.options!=null) setGamma(prev); }
    @Override public void onTick() { if(mc.options!=null) setGamma(16); }

    private void setGamma(double v) {
        try { Field f=SimpleOption.class.getDeclaredField("value"); f.setAccessible(true); f.set(mc.options.getGamma(),v); }
        catch(Exception e) { try{mc.options.getGamma().setValue(Math.min(v,2));}catch(Exception ignored){} }
    }
}
