package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SmeltingRecipe.class)
public class SmeltingRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @Override
    @Unique
    public Recipe toBukkitRecipe(NamespacedKey id) {
        CraftItemStack result = CraftItemStack.asCraftMirror(((SmeltingRecipe) (Object) this).result);

        CraftFurnaceRecipe recipe = new CraftFurnaceRecipe(id, result, CraftRecipe.toBukkit(((SmeltingRecipe) (Object) this).ingredient), ((SmeltingRecipe) (Object) this).experience, ((SmeltingRecipe) (Object) this).cookingTime);
        recipe.setGroup(((SmeltingRecipe) (Object) this).group);
        recipe.setCategory(CraftRecipe.getCategory(((SmeltingRecipe) (Object) this).category()));

        return recipe;
    }
    // CraftBukkit end
}
