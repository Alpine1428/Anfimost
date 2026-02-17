package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;

public class TimeChanger extends Module {
    public enum Preset { CUSTOM, DAY, SUNSET, NIGHT, SUNRISE }
    private final Setting<Preset> preset=addSetting(new Setting<>("Preset",Preset.DAY,Preset.class));
    private final Setting<Integer> time=addSetting(new Setting<>("Time",6000,0,24000,Integer.class).withVisibility(()->preset.getValue()==Preset.CUSTOM));
    private final Setting<Boolean> animate=addSetting(new Setting<>("Animate",false,Boolean.class));
    private float anim=0;

    public TimeChanger() { super("Time Changer","Client-side time",ModuleCategory.VISUALS); }
    @Override public void onTick() { if(animate.getValue()){anim+=50;if(anim>24000)anim=0;} }

    public int getClientTime() {
        if(animate.getValue()) return (int)anim;
        switch(preset.getValue()) { case DAY:return 6000; case SUNSET:return 12500; case NIGHT:return 18000; case SUNRISE:return 23000; case CUSTOM:return time.getValue(); }
        return 6000;
    }
}
