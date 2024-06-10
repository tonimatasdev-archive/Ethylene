package dev.tonimatas.ethylene.link.world.damagesource;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface DamageSourcesLink {
    DamageSource melting();

    DamageSource poison();

    DamageSource explosion(@Nullable Entity entity, @Nullable Entity entity1, ResourceKey<DamageType> resourceKey);

    DamageSource badRespawnPointExplosion(Vec3 vec3d, org.bukkit.block.BlockState blockState);
}
