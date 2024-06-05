package dev.tonimatas.ethylene.mixins.world.effect;

import net.minecraft.world.effect.RegenerationMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegenerationMobEffect.class)
public class RegenerationMobEffectMixin {
    @Inject(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V"))
    private void craftbukkit$applyEffectTick(LivingEntity p_295924_, int p_296417_, CallbackInfoReturnable<Boolean> cir) {
        p_295924_.setHealCause(EntityRegainHealthEvent.RegainReason.MAGIC_REGEN);
    }
}
