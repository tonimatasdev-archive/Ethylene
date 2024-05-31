package org.bukkit.craftbukkit.v1_20_R5.entity;

import net.minecraft.world.entity.monster.Monster;
import org.bukkit.entity.Enemy;

public interface CraftEnemy extends Enemy {

    Monster getHandle();
}
