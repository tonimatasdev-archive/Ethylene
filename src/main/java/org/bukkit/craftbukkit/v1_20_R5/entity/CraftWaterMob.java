package org.bukkit.craftbukkit.v1_20_R5.entity;

import net.minecraft.world.entity.animal.WaterAnimal;
import org.bukkit.craftbukkit.v1_20_R5.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob {

    public CraftWaterMob(CraftServer server, WaterAnimal entity) {
        super(server, entity);
    }

    @Override
    public WaterAnimal getHandle() {
        return (WaterAnimal) entity;
    }

    @Override
    public String toString() {
        return "CraftWaterMob";
    }
}
