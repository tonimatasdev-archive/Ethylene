package org.bukkit.craftbukkit.v1_20_R5.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R5.CraftWorld;
import org.bukkit.inventory.Inventory;

public class CraftDropper extends CraftLootable<DropperBlockEntity> implements Dropper {

    public CraftDropper(World world, DropperBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftDropper(CraftDropper state, Location location) {
        super(state, location);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public void drop() {
        ensureNoWorldGeneration();
        Block block = getBlock();
        if (block.getType() == Material.DROPPER) {
            CraftWorld world = (CraftWorld) this.getWorld();
            DropperBlock drop = (DropperBlock) Blocks.DROPPER;

            drop.dispenseFrom(world.getHandle(), this.getHandle(), this.getPosition());
        }
    }

    @Override
    public CraftDropper copy() {
        return new CraftDropper(this, null);
    }

    @Override
    public CraftDropper copy(Location location) {
        return new CraftDropper(this, location);
    }
}
