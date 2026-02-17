package com.anfimost.client.module;

public enum ModuleCategory {
    VISUALS("Visuals", 0xFF7B68EE),
    COMBAT("Combat", 0xFFFF4444),
    MOVEMENT("Movement", 0xFF44FF44),
    PLAYER("Player", 0xFFFFAA00),
    MISC("Misc", 0xFFAAAAAA);

    private final String displayName;
    private final int color;

    ModuleCategory(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() { return displayName; }
    public int getColor() { return color; }
}
