package org.bukkit.craftbukkit.generator;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.WorldChunkManager;

import java.util.function.Function;

// Do not implement functions to this class, add to NormalChunkGenerator
public abstract class InternalChunkGenerator extends net.minecraft.world.level.chunk.ChunkGenerator {

    public InternalChunkGenerator(WorldChunkManager worldchunkmanager, Function<Holder<BiomeBase>, BiomeSettingsGeneration> function) {
        super(worldchunkmanager, function);
    }
}
