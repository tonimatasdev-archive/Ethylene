package dev.tonimatas.ethylene.mixins.world.damagesource;

import dev.tonimatas.ethylene.link.world.damagesource.DamageSourceLink;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_21_R1.block.CraftBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@SuppressWarnings("UnreachableCode")
@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceLink {
    @Shadow @Final private Holder<DamageType> type;
    @Shadow @Final @Nullable private Entity directEntity;
    @Shadow @Final @Nullable private Entity causingEntity;
    @Shadow @Final @Nullable private Vec3 damageSourcePosition;
    // CraftBukkit start
    @Nullable @Unique private Block directBlock; // The block that caused the damage. damageSourcePosition is not used for all block damages
    @Nullable @Unique private BlockState directBlockState; // The block state of the block relevant to this damage source
    @Unique private boolean sweep = false;
    @Unique private boolean melting = false;
    @Unique private boolean poison = false;
    @Unique private Entity customEntityDamager = null; // This field is a helper for when causing entity damage is not set by vanilla

    @Unique
    @Override
    public void setDirectBlock(@Nullable Block directBlock) {
        this.directBlock = directBlock;
    }

    @Unique
    @Override
    public void setDirectBlockState(@Nullable BlockState directBlockState) {
        this.directBlockState = directBlockState;
    }

    @Unique
    @Override
    public void setSweep(boolean sweep) {
        this.sweep = sweep;
    }

    @Unique
    @Override
    public void setPoison(boolean poison) {
        this.poison = poison;
    }

    @Unique
    @Override
    public void setMelting(boolean melting) {
        this.melting = melting;
    }

    @Override
    public void setCustomEntityDamager(Entity customEntityDamager) {
        this.customEntityDamager = customEntityDamager;
    }

    @Unique
    @Override
    public DamageSource sweep() {
        this.sweep = true;
        return (DamageSource) (Object) this;
    }

    @Unique
    @Override
    public boolean isSweep() {
        return this.sweep;
    }

    @Unique
    @Override
    public DamageSource melting() {
        this.melting = true;
        return (DamageSource) (Object) this;
    }

    @Unique
    @Override
    public boolean isMelting() {
        return this.melting;
    }

    @Unique
    @Override
    public DamageSource poison() {
        this.poison = true;
        return (DamageSource) (Object) this;
    }

    @Unique
    @Override
    public boolean isPoison() {
        return this.poison;
    }

    @Unique
    @Override
    public Entity getDamager() {
        return (this.customEntityDamager != null) ? this.customEntityDamager : this.directEntity;
    }

    @Unique
    @Override
    public DamageSource customEntityDamager(Entity entity) {
        // This method is not intended for change the causing entity if is already set
        // also is only necessary if the entity passed is not the direct entity or different from the current causingEntity
        if (this.customEntityDamager != null || this.directEntity == entity || this.causingEntity == entity) {
            return (DamageSource) (Object) this;
        }
        DamageSource damageSource = this.cloneInstance();
        ((DamageSourceLink) damageSource).setCustomEntityDamager(entity);
        return damageSource;
    }

    @Unique
    @Override
    public org.bukkit.block.Block getDirectBlock() {
        return this.directBlock;
    }

    @Unique
    @Override
    public DamageSource directBlock(Level world, BlockPos blockPosition) {
        if (blockPosition == null || world == null) {
            return (DamageSource) (Object) this;
        }
        return directBlock(CraftBlock.at(world, blockPosition));
    }

    @Unique
    @Override
    public DamageSource directBlock(org.bukkit.block.Block block) {
        if (block == null) {
            return (DamageSource) (Object) this;
        }
        // Cloning the instance lets us return unique instances of DamageSource without affecting constants defined in DamageSources
        DamageSource damageSource = this.cloneInstance();
        ((DamageSourceLink) damageSource).setDirectBlock(block);
        return damageSource;
    }

    @Unique
    @Override
    public org.bukkit.block.BlockState getDirectBlockState() {
        return this.directBlockState;
    }

    @Unique
    @Override
    public DamageSource directBlockState(org.bukkit.block.BlockState blockState) {
        if (blockState == null) {
            return (DamageSource) (Object) this;
        }
        // Cloning the instance lets us return unique instances of DamageSource without affecting constants defined in DamageSources
        DamageSource damageSource = this.cloneInstance();
        ((DamageSourceLink) damageSource).setDirectBlockState(blockState);
        return damageSource;
    }

    @SuppressWarnings("DataFlowIssue")
    @Unique
    @Override
    public DamageSource cloneInstance() {
        DamageSource damageSource = new DamageSource(this.type, this.directEntity, this.causingEntity, this.damageSourcePosition);
        ((DamageSourceLink) damageSource).setDirectBlock(this.getDirectBlock());
        ((DamageSourceLink) damageSource).setDirectBlockState(this.getDirectBlockState());
        ((DamageSourceLink) damageSource).setSweep(this.isSweep());
        ((DamageSourceLink) damageSource).setPoison(this.isPoison());
        ((DamageSourceLink) damageSource).setMelting(this.isMelting());
        return damageSource;
    }
    // CraftBukkit end
}
