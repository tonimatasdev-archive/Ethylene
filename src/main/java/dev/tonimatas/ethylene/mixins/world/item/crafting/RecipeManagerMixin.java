package dev.tonimatas.ethylene.mixins.world.item.crafting;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import dev.tonimatas.ethylene.link.world.ContainerLink;
import dev.tonimatas.ethylene.link.world.item.crafting.RecipeManagerLink;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.*;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin implements RecipeManagerLink {
    @Shadow public Multimap<RecipeType<?>, RecipeHolder<?>> byType;

    @Shadow public Map<ResourceLocation, RecipeHolder<?>> byName;

    @Shadow protected abstract <C extends Container, T extends Recipe<C>> Collection<RecipeHolder<T>> byType(RecipeType<T> p_44055_);

    @Shadow @Nullable protected abstract <T extends Recipe<?>> RecipeHolder<T> byKeyTyped(RecipeType<T> p_341695_, ResourceLocation p_341666_);

    // CraftBukkit start - mutable
    @Redirect(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;byType:Lcom/google/common/collect/Multimap;", opcode = Opcodes.PUTFIELD))
    private void craftbukkit$apply(RecipeManager instance, Multimap<RecipeType<?>, RecipeHolder<?>> value) {
        instance.byType = LinkedHashMultimap.create(value);
    }

    @Redirect(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;byName:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    private void craftbukkit$apply(RecipeManager instance, Map<ResourceLocation, RecipeHolder<?>> value) {
        instance.byName = Maps.newHashMap(value);
    }

    @Redirect(method = "replaceRecipes", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;byType:Lcom/google/common/collect/Multimap;", opcode = Opcodes.PUTFIELD))
    private void craftbukkit$replaceRecipes(RecipeManager instance, Multimap<RecipeType<?>, RecipeHolder<?>> value) {
        instance.byType = LinkedHashMultimap.create(value);
    }

    @Redirect(method = "replaceRecipes", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;byName:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    private void craftbukkit$replaceRecipes(RecipeManager instance, Map<ResourceLocation, RecipeHolder<?>> value) {
        instance.byName = Maps.newHashMap(value);
    }
    // CraftBukkit end

    // CraftBukkit start
    @Override
    @Unique
    public void addRecipe(RecipeHolder<?> irecipe) {
        Collection<RecipeHolder<?>> map = this.byType.get(irecipe.value().getType()); // CraftBukkit

        if (byName.containsKey(irecipe.id())) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + irecipe.id());
        } else {
            map.add(irecipe);
            byName.put(irecipe.id(), irecipe);
        }
    }
    // CraftBukkit end
    
    /**
     * @author TonimatasDEV
     * @reason Implement CraftBukkit
     */
    @Overwrite
    public <C extends Container, T extends Recipe<C>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> p_44016_, C p_44017_, Level p_44018_) {
        // CraftBukkit start
        List<RecipeHolder<T>> list = this.byType(p_44016_).stream().filter((recipeholder) -> {
            return recipeholder.value().matches(p_44017_, p_44018_);
        }).toList();
        Optional<RecipeHolder<T>> recipe = (list.isEmpty()) ? Optional.empty() : Optional.of(list.getLast()); // CraftBukkit - SPIGOT-4638: last recipe gets priority
        ((ContainerLink) p_44017_).setCurrentRecipe(recipe.orElse(null)); // CraftBukkit - Clear recipe when no recipe is found
        return recipe;
        // CraftBukkit end
    }
    
    /**
     * @author TonimatasDEV
     * @reason Implement CraftBukkit
     */
    @Overwrite
    public <C extends Container, T extends Recipe<C>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> p_220249_, C p_220250_, Level p_220251_, @Nullable ResourceLocation p_220252_) {
        if (p_220252_ != null) {
            RecipeHolder<T> recipeholder = this.byKeyTyped(p_220249_, p_220252_);
            if (recipeholder != null && recipeholder.value().matches(p_220250_, p_220251_)) {
                return Optional.of(recipeholder);
            }
        }
        
        // CraftBukkit start
        List<RecipeHolder<T>> list = this.byType(p_220249_).stream().filter((recipeholder1) -> {
            return recipeholder1.value().matches(p_220250_, p_220251_);
        }).toList();
        Optional<RecipeHolder<T>> recipe = (list.isEmpty()) ? Optional.empty() : Optional.of(list.getLast()); // CraftBukkit - SPIGOT-4638: last recipe gets priority
        ((ContainerLink) p_220250_).setCurrentRecipe(recipe.orElse(null)); // CraftBukkit - Clear recipe when no recipe is found
        return recipe;
        // CraftBukkit end
    }

    @Override
    @Unique
    // CraftBukkit start
    public boolean removeRecipe(ResourceLocation mcKey) {
        Iterator<RecipeHolder<?>> iter = byType.values().iterator();
        while (iter.hasNext()) {
            RecipeHolder<?> recipe = iter.next();
            if (recipe.id().equals(mcKey)) {
                iter.remove();
            }
        }

        return byName.remove(mcKey) != null;
    }

    @Override
    @Unique
    public void clearRecipes() {
        this.byType = LinkedHashMultimap.create();
        this.byName = Maps.newHashMap();
    }
    // CraftBukkit end
}
