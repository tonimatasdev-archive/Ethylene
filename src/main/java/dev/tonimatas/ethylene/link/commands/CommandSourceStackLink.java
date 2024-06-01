package dev.tonimatas.ethylene.link.commands;

import com.mojang.brigadier.tree.CommandNode;

public interface CommandSourceStackLink {
    void setCurrentCommand(CommandNode currentCommand);

    boolean hasPermission(int i, String bukkitPermission);

    org.bukkit.command.CommandSender getBukkitSender();
}
