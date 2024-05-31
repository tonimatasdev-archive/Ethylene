package org.bukkit.craftbukkit.v1_20_R5.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Salmon;

public class CraftSalmon extends CraftFish implements Salmon {

    public CraftSalmon(CraftServer server, net.minecraft.world.entity.animal.Salmon entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.animal.Salmon getHandle() {
        return (net.minecraft.world.entity.animal.Salmon) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftSalmon";
    }
}
