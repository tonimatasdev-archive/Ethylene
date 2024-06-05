package dev.tonimatas.ethylene.link.world.damagesource;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface DamageSourceLink {
    DamageSource sweep();

    boolean isSweep();

    DamageSource melting();

    boolean isMelting();

    DamageSource poison();

    boolean isPoison();

    Entity getDamager();

    DamageSource customEntityDamager(Entity entity);

    org.bukkit.block.Block getDirectBlock();

    DamageSource directBlock(Level world, BlockPos blockPosition);

    DamageSource directBlock(org.bukkit.block.Block block);

    org.bukkit.block.BlockState getDirectBlockState();

    DamageSource directBlockState(org.bukkit.block.BlockState blockState);

    DamageSource cloneInstance();
    
    void setDirectBlock(org.bukkit.block.Block block);
    
    void setDirectBlockState(org.bukkit.block.BlockState blockState);
    
    void setSweep(boolean value);
    
    void setPoison(boolean value);
    
    void setMelting(boolean value);
    
    void setCustomEntityDamager(Entity entity);
}
