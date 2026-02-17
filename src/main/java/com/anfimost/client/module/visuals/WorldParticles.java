package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

public class WorldParticles extends Module {
    private final Setting<Boolean> ambient=addSetting(new Setting<>("Ambient",true,Boolean.class));
    private final Setting<Integer> density=addSetting(new Setting<>("Density",3,1,20,Integer.class));
    private final Setting<Boolean> glow=addSetting(new Setting<>("Block Glow",true,Boolean.class));
    private int tick=0;

    public WorldParticles() { super("World Particles","Atmospheric particles",ModuleCategory.VISUALS); }

    @Override public void onTick() {
        if(mc.player==null||mc.world==null) return;
        tick++;
        if(ambient.getValue()&&tick%3==0) for(int i=0;i<density.getValue();i++)
            mc.world.addParticle(ParticleTypes.END_ROD,mc.player.getX()+(Math.random()-0.5)*32,mc.player.getY()+(Math.random()-0.5)*16,mc.player.getZ()+(Math.random()-0.5)*32,(Math.random()-0.5)*0.01,Math.random()*0.02,(Math.random()-0.5)*0.01);
        if(glow.getValue()&&tick%10==0) {
            BlockPos pp=mc.player.getBlockPos();
            for(int dx=-4;dx<=4;dx++) for(int dy=-2;dy<=2;dy++) for(int dz=-4;dz<=4;dz++) {
                BlockPos p=pp.add(dx,dy,dz); var b=mc.world.getBlockState(p).getBlock();
                if((b==Blocks.DIAMOND_ORE||b==Blocks.DEEPSLATE_DIAMOND_ORE||b==Blocks.EMERALD_ORE||b==Blocks.GLOWSTONE||b==Blocks.SEA_LANTERN)&&Math.random()<0.3)
                    mc.world.addParticle(ParticleTypes.ENCHANT,p.getX()+Math.random(),p.getY()+1+Math.random()*0.5,p.getZ()+Math.random(),0,0.05,0);
            }
        }
    }
}
