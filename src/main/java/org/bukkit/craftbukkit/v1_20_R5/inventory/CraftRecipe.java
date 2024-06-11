package org.bukkit.craftbukkit.v1_20_R5.inventory;

import com.google.common.base.Preconditions;
import dev.tonimatas.ethylene.link.world.item.crafting.IngredientLink;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.recipe.CookingBookCategory;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.util.ArrayList;
import java.util.List;

public interface CraftRecipe extends Recipe {

    void addToCraftingManager();

    default Ingredient toNMS(RecipeChoice bukkit, boolean requireNotEmpty) {
        Ingredient stack;

        if (bukkit == null) {
            stack = Ingredient.EMPTY;
        } else if (bukkit instanceof RecipeChoice.MaterialChoice) {
            stack = new Ingredient(((RecipeChoice.MaterialChoice) bukkit).getChoices().stream().map((mat) -> new net.minecraft.world.item.crafting.Ingredient.ItemValue(CraftItemStack.asNMSCopy(new ItemStack(mat)))));
        } else if (bukkit instanceof RecipeChoice.ExactChoice) {
            stack = new Ingredient(((RecipeChoice.ExactChoice) bukkit).getChoices().stream().map((mat) -> new net.minecraft.world.item.crafting.Ingredient.ItemValue(CraftItemStack.asNMSCopy(mat))));
            ((IngredientLink) (Object) stack).setExact(true); // Ethylene
        } else {
            throw new IllegalArgumentException("Unknown recipe stack instance " + bukkit);
        }

        stack.getItems();
        if (requireNotEmpty) {
            Preconditions.checkArgument(stack.itemStacks.length != 0, "Recipe requires at least one non-air choice");
        }

        return stack;
    }

    public static RecipeChoice toBukkit(Ingredient list) {
        list.getItems();

        if (list.itemStacks.length == 0) {
            return null;
        }

        if (((IngredientLink) (Object) list).getExact()) { // Ethylene
            List<org.bukkit.inventory.ItemStack> choices = new ArrayList<>(list.itemStacks.length);
            for (net.minecraft.world.item.ItemStack i : list.itemStacks) {
                choices.add(CraftItemStack.asBukkitCopy(i));
            }

            return new RecipeChoice.ExactChoice(choices);
        } else {

            List<org.bukkit.Material> choices = new ArrayList<>(list.itemStacks.length);
            for (net.minecraft.world.item.ItemStack i : list.itemStacks) {
                choices.add(CraftItemType.minecraftToBukkit(i.getItem()));
            }

            return new RecipeChoice.MaterialChoice(choices);
        }
    }

    public static net.minecraft.world.item.crafting.CraftingBookCategory getCategory(CraftingBookCategory bukkit) {
        return net.minecraft.world.item.crafting.CraftingBookCategory.valueOf(bukkit.name());
    }

    public static CraftingBookCategory getCategory(net.minecraft.world.item.crafting.CraftingBookCategory nms) {
        return CraftingBookCategory.valueOf(nms.name());
    }

    public static net.minecraft.world.item.crafting.CookingBookCategory getCategory(CookingBookCategory bukkit) {
        return net.minecraft.world.item.crafting.CookingBookCategory.valueOf(bukkit.name());
    }

    public static CookingBookCategory getCategory(net.minecraft.world.item.crafting.CookingBookCategory nms) {
        return CookingBookCategory.valueOf(nms.name());
    }
}
