package dev.tonimatas.ethylene.mixins.world.item.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {
    @Inject(method = "doPostAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private void craftbukkit$doPostAttack(LivingEntity p_44638_, Entity p_44639_, int p_44640_, CallbackInfo ci) {
        p_44638_.addEffectCause(EntityPotionEffectEvent.Cause.ATTACK); // CraftBukkit
    }
}
