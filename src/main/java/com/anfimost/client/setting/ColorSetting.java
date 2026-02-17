package com.anfimost.client.setting;

import java.awt.Color;

public class ColorSetting extends Setting<Integer> {
    private boolean rainbow;

    public ColorSetting(String name, int r, int g, int b, int a) {
        super(name, new Color(r, g, b, a).getRGB(), Integer.class);
        this.rainbow = false;
    }

    public Color getColor() {
        if (rainbow) {
            float hue = (System.currentTimeMillis() % 3600) / 3600f;
            Color base = Color.getHSBColor(hue, 0.75f, 1.0f);
            return new Color(base.getRed(), base.getGreen(), base.getBlue(), getAlpha());
        }
        return new Color(getValue(), true);
    }

    public int getRed() { return getColor().getRed(); }
    public int getGreen() { return getColor().getGreen(); }
    public int getBlue() { return getColor().getBlue(); }
    public int getAlpha() { return (getValue() >> 24) & 0xFF; }
    public float getRedF() { return getRed() / 255f; }
    public float getGreenF() { return getGreen() / 255f; }
    public float getBlueF() { return getBlue() / 255f; }
    public float getAlphaF() { return getAlpha() / 255f; }
    public boolean isRainbow() { return rainbow; }
    public void setRainbow(boolean rainbow) { this.rainbow = rainbow; }
}
