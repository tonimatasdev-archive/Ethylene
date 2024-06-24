package org.bukkit.craftbukkit.v1_21_R1.block;

import dev.tonimatas.ethylene.mixins.world.block.entity.ContainerOpenersCounterLink;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftBarrel extends CraftLootable<BarrelBlockEntity> implements Barrel {

    public CraftBarrel(World world, BarrelBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    protected CraftBarrel(CraftBarrel state, Location location) {
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
    public void open() {
        requirePlaced();
        if (!((ContainerOpenersCounterLink) getTileEntity().openersCounter).getOpened()) {
            BlockState blockData = getTileEntity().getBlockState();
            boolean open = blockData.getValue(BarrelBlock.OPEN);

            if (!open) {
                getTileEntity().updateBlockState(blockData, true);
                if (getWorldHandle() instanceof net.minecraft.world.level.Level) {
                    getTileEntity().playSound(blockData, SoundEvents.BARREL_OPEN);
                }
            }
        }
        ((ContainerOpenersCounterLink)getTileEntity().openersCounter).setOpened(true);
    }

    @Override
    public void close() {
        requirePlaced();
        if (((ContainerOpenersCounterLink) getTileEntity().openersCounter).getOpened()) {
            BlockState blockData = getTileEntity().getBlockState();
            getTileEntity().updateBlockState(blockData, false);
            if (getWorldHandle() instanceof net.minecraft.world.level.Level) {
                getTileEntity().playSound(blockData, SoundEvents.BARREL_CLOSE);
            }
        }
        ((ContainerOpenersCounterLink) getTileEntity().openersCounter).setOpened(false);
    }

    @Override
    public CraftBarrel copy() {
        return new CraftBarrel(this, null);
    }

    @Override
    public CraftBarrel copy(Location location) {
        return new CraftBarrel(this, location);
    }
}
