package org.bukkit.craftbukkit.v1_21_R1.inventory;

import dev.tonimatas.ethylene.StaticMethods;
import dev.tonimatas.ethylene.link.world.item.crafting.RecipeManagerLink;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;

public class CraftSmokingRecipe extends SmokingRecipe implements CraftRecipe {
    public CraftSmokingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
    }

    public static CraftSmokingRecipe fromBukkitRecipe(SmokingRecipe recipe) {
        if (recipe instanceof CraftSmokingRecipe) {
            return (CraftSmokingRecipe) recipe;
        }
        CraftSmokingRecipe ret = new CraftSmokingRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime());
        ret.setGroup(recipe.getGroup());
        ret.setCategory(recipe.getCategory());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        ((RecipeManagerLink) StaticMethods.getServer().getRecipeManager()).addRecipe(new RecipeHolder<>(CraftNamespacedKey.toMinecraft(this.getKey()), new net.minecraft.world.item.crafting.SmokingRecipe(this.getGroup(), CraftRecipe.getCategory(this.getCategory()), toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result), getExperience(), getCookingTime()))); // Ethylene
    }
}
