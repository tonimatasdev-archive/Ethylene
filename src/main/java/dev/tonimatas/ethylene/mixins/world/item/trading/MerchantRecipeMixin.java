package dev.tonimatas.ethylene.mixins.world.item.trading;

import dev.tonimatas.ethylene.link.world.item.trading.MerchantOfferLink;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.bukkit.craftbukkit.v1_20_R5.inventory.CraftMerchantRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MerchantOffer.class)
public abstract class MerchantRecipeMixin implements MerchantOfferLink {
    @Shadow public abstract ItemStack getCostA();

    // CraftBukkit start
    @Unique private CraftMerchantRecipe bukkitHandle;

    @Override
    @Unique
    public CraftMerchantRecipe asBukkit() {
        return (bukkitHandle == null) ? bukkitHandle = new CraftMerchantRecipe((MerchantOffer) (Object) this) : bukkitHandle;
    }
    
    // TODO: Constructor
    //public MerchantRecipe(ItemCost baseCostA, Optional<ItemCost> costB, ItemStack result, int uses, int maxUses, int experience, float priceMultiplier, int demand, CraftMerchantRecipe bukkit) {
    //    this(baseCostA, costB, result, uses, maxUses, experience, priceMultiplier, demand);
    //    this.bukkitHandle = bukkit;
    //}
    
    @Redirect(method = "take", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void craftbukkit$take(ItemStack instance, int p_41775_) {
        if (!this.getCostA().isEmpty()) {
            instance.shrink(this.getCostA().getCount());
        }
    }
    // CraftBukkit end
}
