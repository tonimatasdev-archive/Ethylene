package dev.tonimatas.ethylene.mixins.world.item.crafting;

import dev.tonimatas.ethylene.link.world.item.crafting.IngredientLink;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements IngredientLink {
    @Shadow @Nullable private ICustomIngredient customIngredient;

    @Shadow public abstract boolean isEmpty();

    @Shadow public abstract ItemStack[] getItems();

    @Unique public boolean exact; // CraftBukkit

    @Override
    public boolean getExact() {
        return exact;
    }

    @Override
    public void setExact(boolean exact) {
        this.exact = exact;
    }

    /**
     * @author TonimatasDEV
     * @reason Implement CraftBukkit patches
     */
    @Overwrite
    public boolean test(@Nullable ItemStack p_43914_) {
        if (p_43914_ == null) {
            return false;
        } else if (this.customIngredient != null) {
            return this.customIngredient.test(p_43914_);
        } else if (this.isEmpty()) {
            return p_43914_.isEmpty();
        } else {
            ItemStack[] var2 = this.getItems();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                ItemStack itemstack = var2[var4];
                
                // CraftBukkit start
                if (exact) {
                    if (p_43914_.getItem() == itemstack.getItem() && ItemStack.isSameItemSameComponents(itemstack, p_43914_)) {
                        return true;
                    }

                    continue;
                }
                // CraftBukkit end
                
                if (itemstack.is(p_43914_.getItem())) {
                    return true;
                }
            }

            return false;
        }
    }
}
