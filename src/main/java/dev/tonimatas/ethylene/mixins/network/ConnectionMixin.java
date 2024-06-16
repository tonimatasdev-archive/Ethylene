package dev.tonimatas.ethylene.mixins.network;

import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Connection.class)
public class ConnectionMixin {
    @Unique public String hostname = ""; // CraftBukkit - add field
}
