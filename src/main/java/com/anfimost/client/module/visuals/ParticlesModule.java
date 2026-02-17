package com.anfimost.client.module.visuals;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.event.AttackEntityEvent;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import net.minecraft.particle.ParticleTypes;

public class ParticlesModule extends Module {
    private final Setting<Integer> amt=addSetting(new Setting<>("Amount",3,1,10,Integer.class));
    private final Setting<Boolean> sprint=addSetting(new Setting<>("Sprint",true,Boolean.class));
    private final Setting<Boolean> hit=addSetting(new Setting<>("Hit",true,Boolean.class));

    public ParticlesModule() { super("Particles","Enhanced particles",ModuleCategory.VISUALS); }

    @Override public void onEnable() {
        AnfimostClient.getInstance().getEventBus().register(AttackEntityEvent.class,e->{
            if(!isEnabled()||!hit.getValue()||mc.world==null) return;
            var t=e.getTarget();
            for(int i=0;i<amt.getValue();i++) mc.world.addParticle(ParticleTypes.CRIT,t.getX()+(Math.random()-0.5)*0.5,t.getY()+Math.random()*t.getHeight(),t.getZ()+(Math.random()-0.5)*0.5,0,0.1,0);
        });
    }

    @Override public void onTick() {
        if(mc.player==null||mc.world==null) return;
        if(sprint.getValue()&&mc.player.isSprinting()) for(int i=0;i<amt.getValue();i++)
            mc.world.addParticle(ParticleTypes.CLOUD,mc.player.getX()+(Math.random()-0.5)*0.4,mc.player.getY()+0.1,mc.player.getZ()+(Math.random()-0.5)*0.4,0,0.02,0);
    }
}
