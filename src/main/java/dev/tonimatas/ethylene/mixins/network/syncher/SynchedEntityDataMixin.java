package dev.tonimatas.ethylene.mixins.network.syncher;

import dev.tonimatas.ethylene.link.network.syncher.SynchedEntityDataLink;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SynchedEntityData.class)
public abstract class SynchedEntityDataMixin implements SynchedEntityDataLink {
    @Shadow protected abstract <T> SynchedEntityData.DataItem<T> getItem(EntityDataAccessor<T> p_135380_);

    @Shadow private boolean isDirty;

    // CraftBukkit start - add method from above
    @Override
    @Unique
    public <T> void markDirty(EntityDataAccessor<T> datawatcherobject) {
        this.getItem(datawatcherobject).setDirty(true);
        this.isDirty = true;
    }
    // CraftBukkit end
}
