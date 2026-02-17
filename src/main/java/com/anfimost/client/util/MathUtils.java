package com.anfimost.client.util;

public class MathUtils {
    public static float lerp(float a,float b,float t){return a+(b-a)*t;}
    public static float clamp(float v,float mn,float mx){return Math.max(mn,Math.min(mx,v));}
}
