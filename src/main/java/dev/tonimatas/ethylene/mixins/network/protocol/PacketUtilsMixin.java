package dev.tonimatas.ethylene.mixins.network.protocol;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PacketUtils.class)
public class PacketUtilsMixin {
    @Inject(method = "lambda$ensureRunningOnSameThread$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketListener;shouldHandleMessage(Lnet/minecraft/network/protocol/Packet;)Z", shift = At.Shift.BEFORE), cancellable = true)
    private static void craftbukkit$ensureRunningOnSameThread$0(PacketListener p_131365_, Packet p_131364_, CallbackInfo ci) {
        if (p_131365_ instanceof ServerCommonPacketListenerImpl serverCommonPacketListener && serverCommonPacketListener.processedDisconnect) ci.cancel(); // CraftBukkit - Don't handle sync packets for kicked players
    }
}
