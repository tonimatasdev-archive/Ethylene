package dev.tonimatas.ethylene.mixins.world.item.trading;

import net.minecraft.world.item.trading.Merchant;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftMerchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Merchant.class)
public interface MerchantMixin {
    @Unique CraftMerchant getCraftMerchant(); // CraftBukkit
}
