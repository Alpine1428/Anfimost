package com.anfimost.client.module;

import com.anfimost.client.setting.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.*;

public abstract class Module {
    protected final String name;
    protected final String description;
    protected final ModuleCategory category;
    protected boolean enabled;
    protected final List<Setting<?>> settings = new ArrayList<>();
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRenderWorld(WorldRenderContext context) {}
    public void onRenderHud(DrawContext drawContext, float tickDelta) {}

    protected <T> Setting<T> addSetting(Setting<T> setting) {
        settings.add(setting);
        return setting;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ModuleCategory getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { if (this.enabled != enabled) toggle(); }
    public List<Setting<?>> getSettings() { return settings; }
}
