package dev.tonimatas.ethylene.link.network.syncher;

import net.minecraft.network.syncher.EntityDataAccessor;

public interface SynchedEntityDataLink {
    <T> void markDirty(EntityDataAccessor<T> datawatcherobject);
}
