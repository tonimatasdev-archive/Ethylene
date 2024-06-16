package dev.tonimatas.ethylene.link.core.component;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;

public interface DataComponentPatchBuilderLink {
    void copy(DataComponentPatch orig);

    void clear(DataComponentType<?> type);

    boolean isSet(DataComponentType<?> type);

     boolean isEmpty();

    boolean equals(Object object);

    int hashCode();
}
