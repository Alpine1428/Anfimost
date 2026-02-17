package com.anfimost.client.event;

import net.minecraft.entity.Entity;

public class AttackEntityEvent {
    private final Entity target;
    public AttackEntityEvent(Entity target) { this.target = target; }
    public Entity getTarget() { return target; }
}
