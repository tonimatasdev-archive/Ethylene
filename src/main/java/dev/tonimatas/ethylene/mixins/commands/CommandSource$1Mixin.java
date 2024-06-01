package dev.tonimatas.ethylene.mixins.commands;

import dev.tonimatas.ethylene.link.commands.BukkitSenderLink;
import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "net.minecraft.commands.CommandSource$1")
public class CommandSource$1Mixin implements BukkitSenderLink {
    @Unique
    // CraftBukkit start
    @Override
    public org.bukkit.command.CommandSender getBukkitSender(CommandSourceStack wrapper) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // CraftBukkit end
}
