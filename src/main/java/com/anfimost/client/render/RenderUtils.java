package com.anfimost.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.Color;
import java.util.List;

public class RenderUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void setup() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
    }

    public static void end() {
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void drawBox(MatrixStack matrices, Box box, Color color, float lineWidth) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        float x1 = (float)(box.minX - cam.x), y1 = (float)(box.minY - cam.y), z1 = (float)(box.minZ - cam.z);
        float x2 = (float)(box.maxX - cam.x), y2 = (float)(box.maxY - cam.y), z2 = (float)(box.maxZ - cam.z);
        float r = color.getRed()/255f, g = color.getGreen()/255f, b = color.getBlue()/255f, a = color.getAlpha()/255f;

        setup();
        RenderSystem.lineWidth(lineWidth);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

        buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next();

        buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next();

        buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next();

        tess.draw();
        end();
    }

    public static void drawFilledBox(MatrixStack matrices, Box box, Color color) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        float x1 = (float)(box.minX - cam.x), y1 = (float)(box.minY - cam.y), z1 = (float)(box.minZ - cam.z);
        float x2 = (float)(box.maxX - cam.x), y2 = (float)(box.maxY - cam.y), z2 = (float)(box.maxZ - cam.z);
        float r = color.getRed()/255f, g = color.getGreen()/255f, b = color.getBlue()/255f, a = color.getAlpha()/255f;

        setup();
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next();
        buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x1,y1,z2).color(r,g,b,a).next();
        buf.vertex(mat,x1,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x1,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y1,z1).color(r,g,b,a).next(); buf.vertex(mat,x2,y2,z1).color(r,g,b,a).next();
        buf.vertex(mat,x2,y2,z2).color(r,g,b,a).next(); buf.vertex(mat,x2,y1,z2).color(r,g,b,a).next();

        tess.draw();
        end();
    }

    public static void drawCircle3D(MatrixStack matrices, double x, double y, double z,
                                     float radius, int segments, Color color, float lineWidth) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        float cx = (float)(x-cam.x), cy = (float)(y-cam.y), cz = (float)(z-cam.z);
        float r = color.getRed()/255f, g = color.getGreen()/255f, b = color.getBlue()/255f, a = color.getAlpha()/255f;

        setup();
        RenderSystem.lineWidth(lineWidth);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float px = cx + (float)(Math.cos(angle) * radius);
            float pz = cz + (float)(Math.sin(angle) * radius);
            buf.vertex(mat, px, cy, pz).color(r, g, b, a).next();
        }
        tess.draw();
        end();
    }

    public static void drawFilledCircle3D(MatrixStack matrices, double x, double y, double z,
                                           float radius, int segments, Color color) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        float cx = (float)(x-cam.x), cy = (float)(y-cam.y), cz = (float)(z-cam.z);
        float r = color.getRed()/255f, g = color.getGreen()/255f, b = color.getBlue()/255f, a = color.getAlpha()/255f;

        setup();
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        buf.vertex(mat, cx, cy, cz).color(r, g, b, a).next();
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            buf.vertex(mat, cx+(float)(Math.cos(angle)*radius), cy, cz+(float)(Math.sin(angle)*radius)).color(r,g,b,a).next();
        }
        tess.draw();
        end();
    }

    public static void drawTrailLine(MatrixStack matrices, List<Vec3d> points, Color color, float lineWidth) {
        if (points.size() < 2) return;
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        float r = color.getRed()/255f, g = color.getGreen()/255f, b = color.getBlue()/255f;

        setup();
        RenderSystem.lineWidth(lineWidth);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < points.size(); i++) {
            Vec3d p = points.get(i);
            float alpha = (float)(i + 1) / points.size();
            buf.vertex(mat, (float)(p.x-cam.x), (float)(p.y-cam.y), (float)(p.z-cam.z)).color(r,g,b,alpha).next();
        }
        tess.draw();
        end();
    }

    public static Color getRainbowColor(long offset) {
        float hue = ((System.currentTimeMillis() + offset) % 3600) / 3600f;
        return Color.getHSBColor(hue, 0.7f, 1.0f);
    }

    public static void draw2DBox(DrawContext ctx, int x1, int y1, int x2, int y2, Color color, float lw) {
        int c = color.getRGB();
        int w = Math.max(1, (int) lw);
        ctx.fill(x1,y1,x2,y1+w,c); ctx.fill(x1,y2-w,x2,y2,c);
        ctx.fill(x1,y1,x1+w,y2,c); ctx.fill(x2-w,y1,x2,y2,c);
    }
}
