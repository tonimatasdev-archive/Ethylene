package dev.tonimatas.ethylene.mixins.stats;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.bukkit.craftbukkit.v1_20_R5.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin {
    @Redirect(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Recipe;isSpecial()Z"))
    private boolean craftbukkit$addRecipes(Recipe instance, Collection<RecipeHolder<?>> p_12792_, ServerPlayer p_12793_, @Local ResourceLocation resourcelocation) {
        return instance.isSpecial() && CraftEventFactory.handlePlayerRecipeListUpdateEvent(p_12793_, resourcelocation); // CraftBukkit
    }
    
    @SuppressWarnings("ConstantValue")
    @Inject(method = "sendRecipes", at = @At("HEAD"), cancellable = true)
    private void craftbukkit$sendRecipes(ClientboundRecipePacket.State p_12802_, ServerPlayer p_12803_, List<ResourceLocation> p_12804_, CallbackInfo ci) {
        if (p_12803_.connection == null) ci.cancel(); // SPIGOT-4478 during PlayerLoginEvent
    }
}
