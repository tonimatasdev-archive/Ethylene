package org.bukkit.craftbukkit.v1_20_R5.entity;

import org.bukkit.craftbukkit.v1_20_R5.CraftServer;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemStack;
import org.bukkit.entity.OminousItemSpawner;
import org.bukkit.inventory.ItemStack;

public class CraftOminousItemSpawner extends CraftEntity implements OminousItemSpawner {

    public CraftOminousItemSpawner(CraftServer server, net.minecraft.world.entity.OminousItemSpawner entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.OminousItemSpawner getHandle() {
        return (net.minecraft.world.entity.OminousItemSpawner) entity;
    }

    @Override
    public String toString() {
        return "CraftOminousItemSpawner";
    }

    @Override
    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(getHandle().getItem());
    }

    @Override
    public void setItem(ItemStack item) {
        getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public long getSpawnItemAfterTicks() {
        return getHandle().spawnItemAfterTicks;
    }

    @Override
    public void setSpawnItemAfterTicks(long ticks) {
        getHandle().spawnItemAfterTicks = ticks;
    }
}
