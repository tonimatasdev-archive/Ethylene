package org.bukkit.craftbukkit.v1_20_R5.tag;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemType;

import java.util.Set;
import java.util.stream.Collectors;

public class CraftItemTag extends CraftTag<Item, Material> {

    public CraftItemTag(Registry<Item> registry, TagKey<Item> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Material item) {
        Item minecraft = CraftItemType.bukkitToMinecraft(item);

        // SPIGOT-6952: A Material is not necessary an item, in this case return false
        if (minecraft == null) {
            return false;
        }

        return minecraft.builtInRegistryHolder().is(tag);
    }

    @Override
    public Set<Material> getValues() {
        return getHandle().stream().map((item) -> CraftItemType.minecraftToBukkit(item.value())).collect(Collectors.toUnmodifiableSet());
    }
}
