package com.anfimost.client.module;

import com.anfimost.client.module.visuals.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gui.DrawContext;
import java.util.*;
import java.util.stream.Collectors;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        register(new AspectRatio());
        register(new BlockOverlay());
        register(new ChinaHat());
        register(new CrosshairModule());
        register(new CustomHand());
        register(new FullBright());
        register(new HitBubble());
        register(new HitColor());
        register(new HitboxCustomizer());
        register(new JumpCircles());
        register(new Nimb());
        register(new NoFluid());
        register(new ParticlesModule());
        register(new RenderTweaks());
        register(new SelfNametag());
        register(new TargetESP());
        register(new TimeChanger());
        register(new Trails());
        register(new WorldCustomizer());
        register(new WorldParticles());
    }

    private void register(Module module) { modules.add(module); }

    public void onTick() {
        for (Module m : modules) {
            if (m.isEnabled()) { try { m.onTick(); } catch (Exception e) { e.printStackTrace(); } }
        }
    }

    public void onRenderWorld(WorldRenderContext ctx) {
        for (Module m : modules) {
            if (m.isEnabled()) { try { m.onRenderWorld(ctx); } catch (Exception e) { e.printStackTrace(); } }
        }
    }

    public void onRenderHud(DrawContext ctx, float tickDelta) {
        for (Module m : modules) {
            if (m.isEnabled()) { try { m.onRenderHud(ctx, tickDelta); } catch (Exception e) { e.printStackTrace(); } }
        }
    }

    public List<Module> getModules() { return modules; }

    public List<Module> getByCategory(ModuleCategory cat) {
        return modules.stream().filter(m -> m.getCategory() == cat).collect(Collectors.toList());
    }

    public Module getByName(String name) {
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
