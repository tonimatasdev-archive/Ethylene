package dev.tonimatas.ethylene.mixins.core.component;

import dev.tonimatas.ethylene.link.core.component.DataComponentPatchBuilderLink;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(DataComponentPatch.Builder.class)
public class DataComponentPatchBuilderMixin implements DataComponentPatchBuilderLink {
    @Shadow @Final private Reference2ObjectMap<DataComponentType<?>, Optional<?>> map;

    // CraftBukkit start
    @Override
    @Unique
    public void copy(DataComponentPatch orig) {
        this.map.putAll(orig.map);
    }

    @Override
    @Unique
    public void clear(DataComponentType<?> type) {
        this.map.remove(type);
    }

    @Override
    @Unique
    public boolean isSet(DataComponentType<?> type) {
        return map.containsKey(type);
    }

    @Override
    @Unique
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    @Unique
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof DataComponentPatch.Builder patch) {
            return this.map.equals(patch.map);
        }

        return false;
    }

    @Override
    @Unique
    public int hashCode() {
        return this.map.hashCode();
    }
    // CraftBukkit end
}
