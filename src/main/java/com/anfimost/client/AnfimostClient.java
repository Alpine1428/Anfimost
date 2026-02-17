package com.anfimost.client;

import com.anfimost.client.module.ModuleManager;
import com.anfimost.client.gui.ClickGui;
import com.anfimost.client.event.EventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnfimostClient implements ClientModInitializer {
    public static final String MOD_ID = "anfimost-client";
    public static final String MOD_NAME = "Anfimost Client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static AnfimostClient instance;
    private ModuleManager moduleManager;
    private EventBus eventBus;
    private ClickGui clickGui;
    private boolean guiKeyWasPressed = false;

    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing " + MOD_NAME + " v1.0.0");

        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        moduleManager.init();
        clickGui = new ClickGui();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            moduleManager.onTick();

            long handle = client.getWindow().getHandle();
            boolean pressed = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
            if (pressed && !guiKeyWasPressed && client.currentScreen == null) {
                client.setScreen(clickGui);
            }
            guiKeyWasPressed = pressed;
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            moduleManager.onRenderHud(drawContext, tickDelta);
        });

        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            moduleManager.onRenderWorld(context);
        });

        LOGGER.info(MOD_NAME + " initialized with " + moduleManager.getModules().size() + " modules!");
    }

    public static AnfimostClient getInstance() { return instance; }
    public ModuleManager getModuleManager() { return moduleManager; }
    public EventBus getEventBus() { return eventBus; }
    public ClickGui getClickGui() { return clickGui; }
    public static MinecraftClient mc() { return MinecraftClient.getInstance(); }
}
