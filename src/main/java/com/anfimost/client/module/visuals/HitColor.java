package com.anfimost.client.module.visuals;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.event.AttackEntityEvent;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.ColorSetting;
import com.anfimost.client.setting.Setting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HitColor extends Module {
    private final ColorSetting color = (ColorSetting)addSetting(new ColorSetting("Color",255,50,50,100));
    private final Setting<Integer> dur = addSetting(new Setting<>("Duration",300,50,2000,Integer.class));
    private final Map<Integer,Long> hits = new ConcurrentHashMap<>();

    public HitColor() { super("Hit Color","Highlight hit entities",ModuleCategory.VISUALS); }

    @Override public void onEnable() {
        AnfimostClient.getInstance().getEventBus().register(AttackEntityEvent.class,e->{
            if(isEnabled()) hits.put(e.getTarget().getId(),System.currentTimeMillis()+dur.getValue());
        });
    }
    @Override public void onDisable() { hits.clear(); }

    public void onEntityRender(LivingEntity e, float td, MatrixStack m, VertexConsumerProvider v, int l) {
        Long end=hits.get(e.getId());
        if(end==null) return;
        if(System.currentTimeMillis()>end) { hits.remove(e.getId()); return; }
        e.hurtTime = Math.max(1,(int)(10f*(end-System.currentTimeMillis())/dur.getValue()));
    }
}
