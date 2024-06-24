package org.bukkit.craftbukkit.v1_21_R1.inventory;

import dev.tonimatas.ethylene.StaticMethods;
import dev.tonimatas.ethylene.link.world.item.crafting.RecipeManagerLink;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class CraftCampfireRecipe extends CampfireRecipe implements CraftRecipe {
    public CraftCampfireRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
    }

    public static CraftCampfireRecipe fromBukkitRecipe(CampfireRecipe recipe) {
        if (recipe instanceof CraftCampfireRecipe) {
            return (CraftCampfireRecipe) recipe;
        }
        CraftCampfireRecipe ret = new CraftCampfireRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime());
        ret.setGroup(recipe.getGroup());
        ret.setCategory(recipe.getCategory());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        ((RecipeManagerLink) StaticMethods.getServer().getRecipeManager()).addRecipe(new RecipeHolder<>(CraftNamespacedKey.toMinecraft(this.getKey()), new net.minecraft.world.item.crafting.CampfireCookingRecipe(this.getGroup(), CraftRecipe.getCategory(this.getCategory()), toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result), getExperience(), getCookingTime()))); // Ethylene
    }
}
