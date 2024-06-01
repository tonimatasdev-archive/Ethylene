package org.bukkit.craftbukkit.v1_20_R5.entity;

import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import org.bukkit.craftbukkit.v1_20_R5.CraftServer;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemStack;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;

public abstract class CraftThrowableProjectile extends CraftProjectile implements ThrowableProjectile {

    public CraftThrowableProjectile(CraftServer server, ThrowableItemProjectile entity) {
        super(server, entity);
    }

    @Override
    public ItemStack getItem() {
        if (getHandle().getItem().isEmpty()) {
            return CraftItemStack.asBukkitCopy(new net.minecraft.world.item.ItemStack(getHandle().getDefaultItemPublic()));
        } else {
            return CraftItemStack.asBukkitCopy(getHandle().getItem());
        }
    }

    @Override
    public void setItem(ItemStack item) {
        getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public ThrowableItemProjectile getHandle() {
        return (ThrowableItemProjectile) entity;
    }
}
