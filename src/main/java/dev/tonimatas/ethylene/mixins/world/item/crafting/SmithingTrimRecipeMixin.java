package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftSmithingTrimRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmithingTrimRecipe.class)
public class SmithingTrimRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @Override
    public Recipe toBukkitRecipe(NamespacedKey id) {
        return new CraftSmithingTrimRecipe(id, CraftRecipe.toBukkit(((SmithingTrimRecipe) (Object) this).template), CraftRecipe.toBukkit(((SmithingTrimRecipe) (Object) this).base), CraftRecipe.toBukkit(((SmithingTrimRecipe) (Object) this).addition));
    }
    // CraftBukkit end
}
