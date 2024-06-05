package dev.tonimatas.ethylene.mixins.world.effect;

import net.minecraft.world.effect.SaturationMobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.bukkit.craftbukkit.v1_20_R5.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R5.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SaturationMobEffect.class)
public class SaturationMobEffectMixin {
    @Redirect(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
    private void craftbukkit$applyEffectTick(FoodData instance, int p_38708_, float p_38709_, LivingEntity livingEntity) {
        Player entityhuman = (Player) livingEntity;
        // CraftBukkit start
        int oldFoodLevel = entityhuman.getFoodData().foodLevel;
        FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, p_38708_ + 1 + oldFoodLevel);
        if (!event.isCancelled()) {
            entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, p_38709_);
        }

        ((CraftPlayer) entityhuman.getBukkitEntity()).sendHealthUpdate();
        // CraftBukkit end
    }
}
