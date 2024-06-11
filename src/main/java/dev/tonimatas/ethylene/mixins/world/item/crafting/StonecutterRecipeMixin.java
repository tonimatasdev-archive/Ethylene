package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftStonecuttingRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StonecutterRecipe.class)
public class StonecutterRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @Override
    @Unique
    public Recipe toBukkitRecipe(NamespacedKey id) {
        CraftItemStack result = CraftItemStack.asCraftMirror(((StonecutterRecipe) (Object) this).result);

        CraftStonecuttingRecipe recipe = new CraftStonecuttingRecipe(id, result, CraftRecipe.toBukkit(((StonecutterRecipe) (Object) this).ingredient));
        recipe.setGroup(((StonecutterRecipe) (Object) this).group);

        return recipe;
    }
    // CraftBukkit end
}
