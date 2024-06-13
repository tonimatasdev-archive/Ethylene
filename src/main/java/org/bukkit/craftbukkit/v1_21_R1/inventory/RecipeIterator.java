package org.bukkit.craftbukkit.v1_21_R1.inventory;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeHolderLink;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;
import java.util.Map;

public class RecipeIterator implements Iterator<Recipe> {
    private final Iterator<Map.Entry<net.minecraft.world.item.crafting.Recipe<?>, RecipeHolder<?>>> recipes;

    public RecipeIterator() {
        this.recipes = MinecraftServer.getServer().getRecipeManager().byType.entries().iterator();
    }

    @Override
    public boolean hasNext() {
        return recipes.hasNext();
    }

    @Override
    public Recipe next() {
        return ((RecipeHolderLink) (Object) recipes.next().getValue()).toBukkitRecipe(); // Ethylene
    }

    @Override
    public void remove() {
        recipes.remove();
    }
}
