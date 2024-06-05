package dev.tonimatas.ethylene.mixins.world.effect;

import dev.tonimatas.ethylene.Variables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(MobEffectUtil.class)
public abstract class MobEffectUtilMixin {
    @Unique
    private static List<ServerPlayer> addEffectToPlayersAround(ServerLevel worldserver, @Nullable Entity entity, Vec3 vec3d, double d0, MobEffectInstance mobeffect, int i, EntityPotionEffectEvent.Cause cause) {
        Variables.ethylene$mobEffectUtil$cause.set(cause);
        return MobEffectUtil.addEffectToPlayersAround(worldserver, entity, vec3d, d0, mobeffect, i);
    }
    
    @Inject(method = "lambda$addEffectToPlayersAround$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private static void craftbukkit$lambda$addEffectToPlayersAround$1(MobEffectInstance p_216951_, Entity p_216948_, ServerPlayer p_238232_, CallbackInfo ci) {
        p_238232_.addEffectCause(Variables.ethylene$mobEffectUtil$cause.getAndSet(EntityPotionEffectEvent.Cause.UNKNOWN));
    }
}
