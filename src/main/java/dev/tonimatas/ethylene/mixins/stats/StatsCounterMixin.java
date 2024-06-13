package dev.tonimatas.ethylene.mixins.stats;

import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.player.Player;
import org.bukkit.craftbukkit.v1_21_R1.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatsCounter.class)
public abstract class StatsCounterMixin {
    @Shadow public abstract int getValue(Stat<?> p_13016_);

    @Inject(method = "increment", at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/StatsCounter;setValue(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/stats/Stat;I)V"), cancellable = true)
    public void increment(Player p_13024_, Stat<?> p_13025_, int p_13026_, CallbackInfo ci) {
        // CraftBukkit start - fire Statistic events
        org.bukkit.event.Cancellable cancellable = CraftEventFactory.handleStatisticsIncrease(p_13024_, p_13025_, this.getValue(p_13025_), p_13026_);
        if (cancellable != null && cancellable.isCancelled()) {
            ci.cancel();
        }
        // CraftBukkit end
    }
}
