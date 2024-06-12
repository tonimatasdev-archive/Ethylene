package dev.tonimatas.ethylene.mixins.world;

import dev.tonimatas.ethylene.link.world.ContainerLink;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R5.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(CompoundContainer.class)
public abstract class CompoundContainerMixin implements ContainerLink {
    @Shadow @Final public Container container1;
    @Shadow @Final public Container container2;

    @Shadow public abstract ItemStack getItem(int p_18920_);

    @Shadow public abstract int getContainerSize();

    // CraftBukkit start - add fields and methods
    @Unique public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();

    @Unique
    public List<ItemStack> getContents() {
        List<ItemStack> result = new ArrayList<ItemStack>(this.getContainerSize());
        for (int i = 0; i < this.getContainerSize(); i++) {
            result.add(this.getItem(i));
        }
        return result;
    }

    @Unique
    public void onOpen(CraftHumanEntity who) {
        ((ContainerLink) this.container1).onOpen(who);
        ((ContainerLink) this.container2).onOpen(who);
        transaction.add(who);
    }

    @Unique
    public void onClose(CraftHumanEntity who) {
        ((ContainerLink) this.container1).onClose(who);
        ((ContainerLink) this.container2).onClose(who);
        transaction.remove(who);
    }

    @Unique
    public List<HumanEntity> getViewers() {
        return transaction;
    }

    @Unique
    public org.bukkit.inventory.InventoryHolder getOwner() {
        return null; // This method won't be called since CraftInventoryDoubleChest doesn't defer to here
    }

    @Unique
    public void setMaxStackSize(int size) {
        ((ContainerLink) this.container1).setMaxStackSize(size);
        ((ContainerLink) this.container2).setMaxStackSize(size);
    }

    @Override
    @Unique
    public Location getLocation() {
        return ((ContainerLink) container1).getLocation(); // TODO: right?
    }
    // CraftBukkit end
    
    @Redirect(method = "getMaxStackSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;getMaxStackSize()I"))
    private int craftbukkit$getMaxStackSize(Container container) {
        return Math.min(this.container1.getMaxStackSize(), this.container2.getMaxStackSize()); // CraftBukkit - check both sides
    }
}
