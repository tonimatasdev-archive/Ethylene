package dev.tonimatas.ethylene.mixins.world.item.enchantment;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.craftbukkit.v1_20_R5.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FrostWalkerEnchantment.class)
public class FrostWalkerEnchantmentMixin {
    // CraftBukkit Start - Call EntityBlockFormEvent for Frost Walker
    @Redirect(method = "onEntityMoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean craftbukkit$onEntityMoved(Level instance, BlockPos p_46598_, BlockState p_46599_) {
        return false;
    }

    @WrapWithCondition(method = "onEntityMoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"))
    private static boolean craftbukkit$onEntityMoved(Level instance, BlockPos blockPos, Block block, int i, LivingEntity livingEntity) {
        return CraftEventFactory.handleBlockFormEvent(instance, blockPos, Blocks.FROSTED_ICE.defaultBlockState(), livingEntity);
    }
    // CraftBukkit End
}
