package dev.tonimatas.ethylene.mixins.world.effect;

import net.minecraft.world.effect.HungerMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HungerMobEffect.class)
public class HungerMobEffectMixin {
    @Inject(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    public void applyEffectTick(LivingEntity p_296407_, int p_296356_, CallbackInfoReturnable<Boolean> cir) {
        p_296407_.setCauseFoodExhaustionCause(EntityExhaustionEvent.ExhaustionReason.HUNGER_EFFECT); // Ethylene // CraftBukkit - EntityExhaustionEvent
    }
}
