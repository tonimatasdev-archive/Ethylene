package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeLink;
import net.minecraft.world.item.crafting.CustomRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftComplexRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CustomRecipe.class)
public class CustomRecipeMixin implements RecipeLink {
    // CraftBukkit start
    @Override
    @Unique
    public Recipe toBukkitRecipe(NamespacedKey id) {
        return new CraftComplexRecipe(id, (CustomRecipe) (Object) this);
    }
    // CraftBukkit end
}
