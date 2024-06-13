package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @SuppressWarnings("DataFlowIssue")
    @Override
    @Unique
    public org.bukkit.inventory.ShapelessRecipe toBukkitRecipe(NamespacedKey id) {
        CraftItemStack result = CraftItemStack.asCraftMirror(((ShapelessRecipe) (Object) this).result);
        CraftShapelessRecipe recipe = new CraftShapelessRecipe(id, result, ((ShapelessRecipe) (Object) this));
        recipe.setGroup(((ShapelessRecipe) (Object) this).group);
        recipe.setCategory(CraftRecipe.getCategory(((ShapelessRecipe) (Object) this).category()));

        for (Ingredient list : ((ShapelessRecipe) (Object) this).ingredients) {
            recipe.addIngredient(CraftRecipe.toBukkit(list));
        }
        return recipe;
    }
    // CraftBukkit end
}
