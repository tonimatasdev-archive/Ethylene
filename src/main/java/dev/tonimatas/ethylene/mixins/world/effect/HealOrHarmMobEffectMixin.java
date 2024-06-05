package dev.tonimatas.ethylene.mixins.world.effect;

import net.minecraft.world.effect.HealOrHarmMobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HealOrHarmMobEffect.class)
public class HealOrHarmMobEffectMixin {
    @Inject(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V"))
    private void craftbukkit$applyEffectTick(LivingEntity p_295255_, int p_295147_, CallbackInfoReturnable<Boolean> cir) {
        p_295255_.setHealReason(EntityRegainHealthEvent.RegainReason.MAGIC); // Ethylene // CraftBukkit - EntityRegainHealthEvent
    }

    @Inject(method = "applyInstantenousEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V"))
    private void craftbukkit$applyInstantenousEffect(Entity p_294574_, Entity p_295692_, LivingEntity p_296483_, int p_296095_, double p_295178_, CallbackInfo ci) {
        p_296483_.setHealReason(EntityRegainHealthEvent.RegainReason.MAGIC); // Ethylene // CraftBukkit - EntityRegainHealthEvent
    }
}
