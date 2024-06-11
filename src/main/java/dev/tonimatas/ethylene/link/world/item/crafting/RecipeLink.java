package dev.tonimatas.ethylene.link.world.item.crafting;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

public interface RecipeLink {
    Recipe toBukkitRecipe(NamespacedKey id);
}
