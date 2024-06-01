package dev.tonimatas.ethylene.mixins.commands;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CommandSource.class)
public interface CommandSourceMixin {
    org.bukkit.command.CommandSender getBukkitSender(CommandSourceStack wrapper); // CraftBukkit
}
