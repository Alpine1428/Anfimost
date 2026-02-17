package com.anfimost.client.module.visuals;

import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;

public class CustomHand extends Module {
    private final Setting<Float> mainX=addSetting(new Setting<>("Main X",0f,-2f,2f,Float.class));
    private final Setting<Float> mainY=addSetting(new Setting<>("Main Y",0f,-2f,2f,Float.class));
    private final Setting<Float> mainZ=addSetting(new Setting<>("Main Z",0f,-2f,2f,Float.class));
    private final Setting<Float> mainS=addSetting(new Setting<>("Main Scale",1f,0.1f,3f,Float.class));
    private final Setting<Float> mainRX=addSetting(new Setting<>("Main RotX",0f,-180f,180f,Float.class));
    private final Setting<Float> mainRY=addSetting(new Setting<>("Main RotY",0f,-180f,180f,Float.class));
    private final Setting<Float> mainRZ=addSetting(new Setting<>("Main RotZ",0f,-180f,180f,Float.class));
    private final Setting<Float> offX=addSetting(new Setting<>("Off X",0f,-2f,2f,Float.class));
    private final Setting<Float> offY=addSetting(new Setting<>("Off Y",0f,-2f,2f,Float.class));
    private final Setting<Float> offZ=addSetting(new Setting<>("Off Z",0f,-2f,2f,Float.class));
    private final Setting<Float> offS=addSetting(new Setting<>("Off Scale",1f,0.1f,3f,Float.class));

    public CustomHand() { super("Custom Hand","Hand position/scale",ModuleCategory.VISUALS); }

    public void transformHand(MatrixStack mat, Hand hand) {
        if(hand==Hand.MAIN_HAND) {
            mat.translate(mainX.getValue(),mainY.getValue(),mainZ.getValue());
            float s=mainS.getValue(); mat.scale(s,s,s);
            if(mainRX.getValue()!=0) mat.multiply(RotationAxis.POSITIVE_X.rotationDegrees(mainRX.getValue()));
            if(mainRY.getValue()!=0) mat.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(mainRY.getValue()));
            if(mainRZ.getValue()!=0) mat.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(mainRZ.getValue()));
        } else {
            mat.translate(offX.getValue(),offY.getValue(),offZ.getValue());
            float s=offS.getValue(); mat.scale(s,s,s);
        }
    }
}
