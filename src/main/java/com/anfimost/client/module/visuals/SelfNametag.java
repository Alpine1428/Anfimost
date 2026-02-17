package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class SelfNametag extends Module {
    private final Setting<Boolean> hp=addSetting(new Setting<>("Health",true,Boolean.class));
    private final Setting<Boolean> armor=addSetting(new Setting<>("Armor",true,Boolean.class));
    private final Setting<Float> scale=addSetting(new Setting<>("Scale",1f,0.5f,3f,Float.class));
    private final Setting<Boolean> always=addSetting(new Setting<>("Always Visible",true,Boolean.class));

    public SelfNametag() { super("Self Nametag","Custom nametag",ModuleCategory.VISUALS); }

    public void renderCustomLabel(AbstractClientPlayerEntity p, MatrixStack mat, VertexConsumerProvider vcp, int light) {
        TextRenderer tr=mc.textRenderer;
        mat.push();
        mat.translate(0,p.getHeight()+0.5,0);
        mat.multiply(mc.getEntityRenderDispatcher().getRotation());
        float s=-0.025f*scale.getValue(); mat.scale(s,s,s);
        StringBuilder sb=new StringBuilder(p.getName().getString());
        if(hp.getValue()) { int h=(int)p.getHealth(); sb.append(" ").append(h>15?"\u00a7a":h>10?"\u00a7e":h>5?"\u00a76":"\u00a7c").append(h).append("\u00a7f\u2764"); }
        if(armor.getValue()&&p.getArmor()>0) sb.append(" \u00a7b").append(p.getArmor()).append("\u00a7f\u26E8");
        String text=sb.toString(); float x=-tr.getWidth(text)/2f;
        Matrix4f m=mat.peek().getPositionMatrix();
        var lt=always.getValue()?TextRenderer.TextLayerType.SEE_THROUGH:TextRenderer.TextLayerType.NORMAL;
        tr.draw(text,x,0,0x20FFFFFF,false,m,vcp,lt,0x40000000,light);
        tr.draw(text,x,0,-1,false,m,vcp,lt,0,light);
        mat.pop();
    }
}
