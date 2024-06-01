package org.bukkit.craftbukkit.v1_20_R5.attribute;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R5.util.ApiVersion;
import org.bukkit.craftbukkit.v1_20_R5.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.CraftRegistry;
import org.bukkit.craftbukkit.v1_20_R5.legacy.FieldRename;

import java.util.Locale;

public class CraftAttribute {

    public static Attribute minecraftToBukkit(net.minecraft.world.entity.ai.attributes.Attribute minecraft) {
        Preconditions.checkArgument(minecraft != null);

        net.minecraft.core.Registry<net.minecraft.world.entity.ai.attributes.Attribute> registry = CraftRegistry.getMinecraftRegistry(Registries.ATTRIBUTE);
        Attribute bukkit = Registry.ATTRIBUTE.get(CraftNamespacedKey.fromMinecraft(registry.getResourceKey(minecraft).orElseThrow().location()));

        Preconditions.checkArgument(bukkit != null);

        return bukkit;
    }

    public static Attribute minecraftHolderToBukkit(Holder<net.minecraft.world.entity.ai.attributes.Attribute> minecraft) {
        return minecraftToBukkit(minecraft.value());
    }

    public static Attribute stringToBukkit(String string) {
        Preconditions.checkArgument(string != null);

        // We currently do not have any version-dependent remapping, so we can use current version
        // First convert from when only the names where saved
        string = FieldRename.convertAttributeName(ApiVersion.CURRENT, string);
        string = string.toLowerCase(Locale.ROOT);
        NamespacedKey key = NamespacedKey.fromString(string);

        // Now also convert from when keys where saved
        return CraftRegistry.get(Registry.ATTRIBUTE, key, ApiVersion.CURRENT);
    }

    public static net.minecraft.world.entity.ai.attributes.Attribute bukkitToMinecraft(Attribute bukkit) {
        Preconditions.checkArgument(bukkit != null);

        return CraftRegistry.getMinecraftRegistry(Registries.ATTRIBUTE)
                .getOptional(CraftNamespacedKey.toMinecraft(bukkit.getKey())).orElseThrow();
    }

    public static Holder<net.minecraft.world.entity.ai.attributes.Attribute> bukkitToMinecraftHolder(Attribute bukkit) {
        Preconditions.checkArgument(bukkit != null);

        net.minecraft.core.Registry<net.minecraft.world.entity.ai.attributes.Attribute> registry = CraftRegistry.getMinecraftRegistry(Registries.ATTRIBUTE);

        if (registry.wrapAsHolder(bukkitToMinecraft(bukkit)) instanceof Holder.Direct<net.minecraft.world.entity.ai.attributes.Attribute> holder) {
            return holder;
        }

        throw new IllegalArgumentException("No Reference holder found for " + bukkit
                + ", this can happen if a plugin creates its own sound effect with out properly registering it.");
    }

    public static String bukkitToString(Attribute bukkit) {
        Preconditions.checkArgument(bukkit != null);

        return bukkit.getKey().toString();
    }
}
