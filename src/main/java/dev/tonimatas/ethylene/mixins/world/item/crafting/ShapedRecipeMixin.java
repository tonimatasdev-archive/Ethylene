package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftShapedRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("DataFlowIssue")
@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @Override
    @Unique
    public org.bukkit.inventory.ShapedRecipe toBukkitRecipe(NamespacedKey id) {
        CraftItemStack result = CraftItemStack.asCraftMirror(((ShapedRecipe) (Object) this).result);
        CraftShapedRecipe recipe = new CraftShapedRecipe(id, result, ((ShapedRecipe) (Object) this));
        recipe.setGroup(((ShapedRecipe) (Object) this).group);
        recipe.setCategory(CraftRecipe.getCategory(((ShapedRecipe) (Object) this).category()));

        switch (((ShapedRecipe) (Object) this).pattern.height()) {
            case 1:
                switch (((ShapedRecipe) (Object) this).pattern.width()) {
                    case 1:
                        recipe.shape("a");
                        break;
                    case 2:
                        recipe.shape("ab");
                        break;
                    case 3:
                        recipe.shape("abc");
                        break;
                }
                break;
            case 2:
                switch (((ShapedRecipe) (Object) this).pattern.width()) {
                    case 1:
                        recipe.shape("a","b");
                        break;
                    case 2:
                        recipe.shape("ab","cd");
                        break;
                    case 3:
                        recipe.shape("abc","def");
                        break;
                }
                break;
            case 3:
                switch (((ShapedRecipe) (Object) this).pattern.width()) {
                    case 1:
                        recipe.shape("a","b","c");
                        break;
                    case 2:
                        recipe.shape("ab","cd","ef");
                        break;
                    case 3:
                        recipe.shape("abc","def","ghi");
                        break;
                }
                break;
        }
        char c = 'a';
        for (Ingredient list : ((ShapedRecipe) (Object) this).pattern.ingredients()) {
            RecipeChoice choice = CraftRecipe.toBukkit(list);
            if (choice != null) {
                recipe.setIngredient(c, choice);
            }

            c++;
        }
        return recipe;
    }
    // CraftBukkit end
}
