package dev.tonimatas.ethylene.link.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface RecipeManagerLink {
    void addRecipe(RecipeHolder<?> irecipe);

    boolean removeRecipe(ResourceLocation mcKey);

    void clearRecipes();
}
