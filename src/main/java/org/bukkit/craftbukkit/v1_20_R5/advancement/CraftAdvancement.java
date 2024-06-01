package org.bukkit.craftbukkit.v1_20_R5.advancement;

import net.minecraft.advancements.AdvancementHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.craftbukkit.v1_20_R5.util.CraftNamespacedKey;

import java.util.Collection;
import java.util.Collections;

public class CraftAdvancement implements org.bukkit.advancement.Advancement {

    private final AdvancementHolder handle;

    public CraftAdvancement(AdvancementHolder handle) {
        this.handle = handle;
    }

    public AdvancementHolder getHandle() {
        return handle;
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(handle.id());
    }

    @Override
    public Collection<String> getCriteria() {
        return Collections.unmodifiableCollection(handle.value().criteria().keySet());
    }

    @Override
    public AdvancementDisplay getDisplay() {
        if (handle.value().display().isEmpty()) {
            return null;
        }

        return new CraftAdvancementDisplay(handle.value().display().get());
    }
}
