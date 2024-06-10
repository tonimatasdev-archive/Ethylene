package dev.tonimatas.ethylene.mixins.world.damagesource;

import dev.tonimatas.ethylene.link.world.damagesource.DamageSourceLink;
import dev.tonimatas.ethylene.link.world.damagesource.DamageSourcesLink;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements DamageSourcesLink {
    @Shadow public abstract DamageSource source(ResourceKey<DamageType> p_270957_);

    @Shadow public abstract DamageSource source(ResourceKey<DamageType> p_270076_, @Nullable Entity p_270656_, @Nullable Entity p_270242_);

    @Shadow @Final public Registry<DamageType> damageTypes;
    // CraftBukkit start
    @Unique private DamageSource melting;
    @Unique private DamageSource poison;
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void craftbukkit$init(RegistryAccess p_270740_, CallbackInfo ci) {
        this.melting = this.source(DamageTypes.ON_FIRE);
        this.poison = this.source(DamageTypes.MAGIC);
    }
    
    @Override
    @Unique
    public DamageSource melting() {
        return this.melting;
    }

    @Override
    @Unique
    public DamageSource poison() {
        return this.poison;
    }

    @Override
    @Unique
    public DamageSource explosion(@Nullable Entity entity, @Nullable Entity entity1, ResourceKey<DamageType> resourceKey) {
        return this.source(resourceKey, entity, entity1);
    }

    @Override
    @Unique
    public DamageSource badRespawnPointExplosion(Vec3 vec3d, BlockState blockState) {
        return ((DamageSourceLink) new DamageSource(this.damageTypes.getHolderOrThrow(DamageTypes.BAD_RESPAWN_POINT), vec3d)).directBlockState(blockState);
    }
    // CraftBukkit end
}
