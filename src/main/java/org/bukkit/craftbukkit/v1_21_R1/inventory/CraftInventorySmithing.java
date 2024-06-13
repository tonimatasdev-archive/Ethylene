package org.bukkit.craftbukkit.v1_21_R1.inventory;

import dev.tonimatas.ethylene.link.world.item.crafting.RecipeHolderLink;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;

public class CraftInventorySmithing extends CraftResultInventory implements SmithingInventory {

    private final Location location;

    public CraftInventorySmithing(Location location, Container inventory, ResultContainer resultInventory) {
        super(inventory, resultInventory);
        this.location = location;
    }

    @Override
    public ResultContainer getResultInventory() {
        return (ResultContainer) super.getResultInventory();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ItemStack getResult() {
        return getItem(3);
    }

    @Override
    public void setResult(ItemStack item) {
        setItem(3, item);
    }

    @Override
    public Recipe getRecipe() {
        RecipeHolder<?> recipe = getResultInventory().getRecipeUsed();
        return (recipe == null) ? null : ((RecipeHolderLink) (Object) recipe).toBukkitRecipe(); // Ethylene
    }
}
