package org.bukkit.craftbukkit.v1_20_R5.inventory;

import dev.tonimatas.ethylene.link.world.item.trading.MerchantLink;
import dev.tonimatas.ethylene.link.world.item.trading.MerchantOfferLink;
import net.minecraft.world.inventory.MerchantContainer;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {

    private final net.minecraft.world.item.trading.Merchant merchant;

    public CraftInventoryMerchant(net.minecraft.world.item.trading.Merchant merchant, MerchantContainer inventory) {
        super(inventory);
        this.merchant = merchant;
    }

    @Override
    public int getSelectedRecipeIndex() {
        return getInventory().selectionHint;
    }

    @Override
    public MerchantRecipe getSelectedRecipe() {
        net.minecraft.world.item.trading.MerchantOffer nmsRecipe = getInventory().getActiveOffer();
        return (nmsRecipe == null) ? null : ((MerchantOfferLink) nmsRecipe).asBukkit(); // Ethylene
    }

    @Override
    public MerchantContainer getInventory() {
        return (MerchantContainer) inventory;
    }

    @Override
    public Merchant getMerchant() {
        return ((MerchantLink) merchant).getCraftMerchant(); // Ethylene
    }
}
