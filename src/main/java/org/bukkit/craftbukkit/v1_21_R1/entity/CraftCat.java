package org.bukkit.craftbukkit.v1_21_R1.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.animal.CatVariant;
import org.bukkit.DyeColor;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.CraftRegistry;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;
import org.bukkit.entity.Cat;

public class CraftCat extends CraftTameableAnimal implements Cat {

    public CraftCat(CraftServer server, net.minecraft.world.entity.animal.Cat entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.animal.Cat getHandle() {
        return (net.minecraft.world.entity.animal.Cat) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftCat";
    }

    @Override
    public Type getCatType() {
        return CraftType.minecraftHolderToBukkit(getHandle().getVariant());
    }

    @Override
    public void setCatType(Type type) {
        Preconditions.checkArgument(type != null, "Cannot have null Type");

        getHandle().setVariant(CraftType.bukkitToMinecraftHolder(type));
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) getHandle().getCollarColor().getId());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        getHandle().setCollarColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }

    public static class CraftType {

        public static Type minecraftToBukkit(CatVariant minecraft) {
            Preconditions.checkArgument(minecraft != null);

            net.minecraft.core.Registry<CatVariant> registry = CraftRegistry.getMinecraftRegistry(Registries.CAT_VARIANT);

            return Registry.CAT_VARIANT.get(CraftNamespacedKey.fromMinecraft(registry.getKey(minecraft)));
        }

        public static Type minecraftHolderToBukkit(Holder<CatVariant> minecraft) {
            return minecraftToBukkit(minecraft.value());
        }

        public static CatVariant bukkitToMinecraft(Type bukkit) {
            Preconditions.checkArgument(bukkit != null);

            net.minecraft.core.Registry<CatVariant> registry = CraftRegistry.getMinecraftRegistry(Registries.CAT_VARIANT);

            return registry.get(CraftNamespacedKey.toMinecraft(bukkit.getKey()));
        }

        public static Holder<CatVariant> bukkitToMinecraftHolder(Type bukkit) {
            Preconditions.checkArgument(bukkit != null);

            net.minecraft.core.Registry<CatVariant> registry = CraftRegistry.getMinecraftRegistry(Registries.CAT_VARIANT);

            if (registry.wrapAsHolder(bukkitToMinecraft(bukkit)) instanceof Holder.Direct<CatVariant> holder) {
                return holder;
            }

            throw new IllegalArgumentException("No Reference holder found for " + bukkit
                    + ", this can happen if a plugin creates its own cat variant with out properly registering it.");
        }
    }
}
