package org.bukkit.craftbukkit.v1_21_R1.inventory;

import dev.tonimatas.ethylene.StaticMethods;
import dev.tonimatas.ethylene.link.world.item.crafting.RecipeHolderLink;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;
import java.util.Map;

public class RecipeIterator implements Iterator<Recipe> {
    private final Iterator<Map.Entry<RecipeType<?>, RecipeHolder<?>>> recipes;

    public RecipeIterator() {
        this.recipes = StaticMethods.getServer().getRecipeManager().byType.entries().iterator();
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
