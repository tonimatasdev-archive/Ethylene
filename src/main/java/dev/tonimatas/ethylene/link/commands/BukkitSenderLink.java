package dev.tonimatas.ethylene.link.commands;

import net.minecraft.commands.CommandSourceStack;

public interface BukkitSenderLink {
    org.bukkit.command.CommandSender getBukkitSender(CommandSourceStack wrapper);
}
