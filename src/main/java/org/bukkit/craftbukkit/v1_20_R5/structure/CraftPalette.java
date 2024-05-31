package org.bukkit.craftbukkit.v1_20_R5.structure;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_20_R5.block.CraftBlockStates;
import org.bukkit.structure.Palette;

import java.util.ArrayList;
import java.util.List;

public class CraftPalette implements Palette {

    private final StructureTemplate.Palette palette;
    private final RegistryAccess registry;

    public CraftPalette(StructureTemplate.Palette palette, RegistryAccess registry) {
        this.palette = palette;
        this.registry = registry;
    }

    @Override
    public List<BlockState> getBlocks() {
        List<BlockState> blocks = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo blockInfo : palette.blocks()) {
            blocks.add(CraftBlockStates.getBlockState(registry, blockInfo.pos(), blockInfo.state(), blockInfo.nbt()));
        }
        return blocks;
    }

    @Override
    public int getBlockCount() {
        return palette.blocks().size();
    }
}
