package com.anfimost.client.gui;

import com.anfimost.client.AnfimostClient;
import com.anfimost.client.module.Module;
import com.anfimost.client.module.ModuleCategory;
import com.anfimost.client.setting.Setting;
import com.anfimost.client.setting.ColorSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.*;

public class ClickGui extends Screen {
    private final List<Panel> panels = new ArrayList<>();
    private Panel dragging = null;
    private int dragX, dragY;
    private Module expanded = null;
    private boolean hasInit = false;

    public ClickGui() { super(Text.literal("Anfimost")); }

    private void initPanels() {
        if (hasInit) return;
        hasInit = true;
        int x = 20;
        for (ModuleCategory c : ModuleCategory.values()) {
            panels.add(new Panel(c, x, 30));
            x += 140;
        }
    }

    @Override public boolean shouldPause() { return false; }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        initPanels();
        ctx.fill(0, 0, width, height, 0x88000000);

        long t = System.currentTimeMillis();
        for (int i = 0; i < width; i++) {
            float hue = ((t / 10 + i * 2) % 360) / 360f;
            ctx.fill(i, 0, i + 1, 2, java.awt.Color.HSBtoRGB(hue, 0.6f, 0.9f) | 0xFF000000);
        }

        String title = "\u00a7d\u00a7lAnfimost \u00a77Client";
        ctx.drawTextWithShadow(textRenderer, title, width/2 - textRenderer.getWidth(title)/2, 8, -1);

        for (Panel p : panels) drawPanel(ctx, p, mx, my);
    }

    private void drawPanel(DrawContext ctx, Panel p, int mx, int my) {
        int x = p.x, y = p.y, w = 130, hh = 24;
        int cc = p.cat.getColor(), dc = dark(cc, 0.35f);

        ctx.fill(x+2, y+2, x+w+2, y+hh+2, 0x40000000);
        ctx.fillGradient(x, y, x+w, y+hh, cc, dc);

        String nm = "\u00a7l" + p.cat.getDisplayName();
        ctx.drawTextWithShadow(textRenderer, nm, x+w/2-textRenderer.getWidth(nm)/2, y+8, -1);

        List<Module> mods = AnfimostClient.getInstance().getModuleManager().getByCategory(p.cat);
        int cy = y + hh;
        for (Module m : mods) {
            int mh = 20;
            boolean hov = mx>=x && mx<=x+w && my>=cy && my<cy+mh;
            int bg = m.isEnabled() ? alpha(cc, hov?160:130) : (hov ? 0x50FFFFFF : 0xA0111111);
            ctx.fill(x, cy, x+w, cy+mh, bg);
            if (m.isEnabled()) ctx.fill(x, cy, x+3, cy+mh, cc);
            ctx.fill(x, cy+mh-1, x+w, cy+mh, 0x20FFFFFF);
            ctx.drawTextWithShadow(textRenderer, m.getName(), x+7, cy+6, m.isEnabled() ? -1 : 0xFFAAAAAA);
            if (!m.getSettings().isEmpty()) {
                ctx.drawTextWithShadow(textRenderer, expanded==m?"\u25BC":"\u25B6", x+w-12, cy+6, 0xFF888888);
            }
            cy += mh;

            if (expanded == m) {
                for (Setting<?> s : m.getSettings()) {
                    if (!s.isVisible()) continue;
                    int sh = 16;
                    boolean sHov = mx>=x+3 && mx<=x+w-3 && my>=cy && my<cy+sh;
                    ctx.fill(x+3, cy, x+w-3, cy+sh, sHov?0x40FFFFFF:0x50000000);
                    ctx.fill(x+3, cy, x+5, cy+sh, alpha(cc, 100));
                    ctx.drawTextWithShadow(textRenderer, fmtSetting(s), x+10, cy+4, 0xFFCCCCCC);
                    if (s.getMin()!=null && s.getValue() instanceof Number) {
                        double v=((Number)s.getValue()).doubleValue();
                        double mn=((Number)s.getMin()).doubleValue(), mx2=((Number)s.getMax()).doubleValue();
                        double ratio=(v-mn)/(mx2-mn);
                        int bw=w-20, bx=x+10, by=cy+sh-4;
                        ctx.fill(bx,by,bx+bw,by+2,0x60FFFFFF);
                        ctx.fill(bx,by,bx+(int)(bw*ratio),by+2,cc);
                        ctx.fill(bx+(int)(bw*ratio)-1,by-1,bx+(int)(bw*ratio)+1,by+3,-1);
                    }
                    cy += sh;
                }
            }
        }
        ctx.fill(x, cy, x+w, cy+2, dc);
        p.h = cy - y + 2;
    }

    private String fmtSetting(Setting<?> s) {
        Object v = s.getValue();
        if (v instanceof Boolean) return s.getName()+": "+((Boolean)v?"\u00a7aON":"\u00a7cOFF");
        if (v instanceof Enum) return s.getName()+": \u00a7e"+v;
        if (v instanceof Float) return s.getName()+": \u00a7b"+String.format("%.1f",v);
        if (v instanceof Double) return s.getName()+": \u00a7b"+String.format("%.1f",v);
        if (v instanceof Integer) {
            if (s instanceof ColorSetting) return s.getName()+": \u00a7dColor";
            return s.getName()+": \u00a7b"+v;
        }
        return s.getName()+": "+v;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int btn) {
        initPanels();
        int imx=(int)mouseX, imy=(int)mouseY;
        for (Panel p : panels) {
            int x=p.x,y=p.y,w=130,hh=24;
            if (imx>=x&&imx<=x+w&&imy>=y&&imy<y+hh&&btn==0) {
                dragging=p; dragX=imx-x; dragY=imy-y; return true;
            }
            List<Module> mods = AnfimostClient.getInstance().getModuleManager().getByCategory(p.cat);
            int cy=y+hh;
            for (Module m : mods) {
                int mh=20;
                if (imx>=x&&imx<=x+w&&imy>=cy&&imy<cy+mh) {
                    if (btn==0) { m.toggle(); return true; }
                    if (btn==1&&!m.getSettings().isEmpty()) { expanded=(expanded==m)?null:m; return true; }
                }
                cy+=mh;
                if (expanded==m) {
                    for (Setting<?> s : m.getSettings()) {
                        if (!s.isVisible()) continue;
                        int sh=16;
                        if (imx>=x+3&&imx<=x+w-3&&imy>=cy&&imy<cy+sh&&btn==0) {
                            clickSetting(s,imx,x,w); return true;
                        }
                        cy+=sh;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX,mouseY,btn);
    }

    @SuppressWarnings("unchecked")
    private void clickSetting(Setting<?> s, int mx, int px, int pw) {
        if (s.getValue() instanceof Boolean || s.getValue() instanceof Enum) { s.increment(); return; }
        if (s.getMin()!=null && s.getValue() instanceof Number) {
            int bx=px+10, bw=pw-20;
            double ratio=Math.max(0,Math.min(1,(double)(mx-bx)/bw));
            double mn=((Number)s.getMin()).doubleValue(), mx2=((Number)s.getMax()).doubleValue();
            double val=mn+ratio*(mx2-mn);
            if (s.getValue() instanceof Integer) ((Setting<Integer>)s).setValue((int)Math.round(val));
            else if (s.getValue() instanceof Float) ((Setting<Float>)s).setValue((float)val);
            else if (s.getValue() instanceof Double) ((Setting<Double>)s).setValue(val);
        }
    }

    @Override
    public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (dragging!=null&&btn==0) { dragging.x=(int)mx-dragX; dragging.y=(int)my-dragY; return true; }
        if (btn==0&&expanded!=null) {
            int imx=(int)mx, imy=(int)my;
            for (Panel p : panels) {
                List<Module> mods = AnfimostClient.getInstance().getModuleManager().getByCategory(p.cat);
                int cy=p.y+24;
                for (Module m : mods) {
                    cy+=20;
                    if (expanded==m) {
                        for (Setting<?> s : m.getSettings()) {
                            if (!s.isVisible()) continue;
                            int sh=16;
                            if (imx>=p.x+3&&imx<=p.x+130-3&&imy>=cy&&imy<cy+sh) {
                                if (s.getMin()!=null&&s.getValue() instanceof Number) {
                                    clickSetting(s,imx,p.x,130); return true;
                                }
                            }
                            cy+=sh;
                        }
                    }
                }
            }
        }
        return super.mouseDragged(mx,my,btn,dx,dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int btn) { dragging=null; return super.mouseReleased(mx,my,btn); }

    static int alpha(int c,int a){return(a<<24)|(c&0xFFFFFF);}
    static int dark(int c,float f){return 0xFF000000|((int)(((c>>16)&0xFF)*f)<<16)|((int)(((c>>8)&0xFF)*f)<<8)|(int)((c&0xFF)*f);}

    static class Panel { ModuleCategory cat; int x,y,h; Panel(ModuleCategory c,int x,int y){cat=c;this.x=x;this.y=y;h=24;} }
}
