package org.bukkit.craftbukkit.v1_21_R1.command;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import dev.tonimatas.ethylene.link.commands.CommandsLink;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftMinecartCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.CommandMinecart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VanillaCommandWrapper extends BukkitCommand {

    private final Commands dispatcher;
    public final CommandNode<CommandSourceStack> vanillaCommand;

    public VanillaCommandWrapper(Commands dispatcher, CommandNode<CommandSourceStack> vanillaCommand) {
        super(vanillaCommand.getName(), "A Mojang provided command.", vanillaCommand.getUsageText(), Collections.EMPTY_LIST);
        this.dispatcher = dispatcher;
        this.vanillaCommand = vanillaCommand;
        this.setPermission(getPermission(vanillaCommand));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;

        CommandSourceStack icommandlistener = getListener(sender);
        ((CommandsLink) dispatcher).performPrefixedCommand(icommandlistener, toDispatcher(args, getName()), toDispatcher(args, commandLabel));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        Preconditions.checkArgument(sender != null, "Sender cannot be null");
        Preconditions.checkArgument(args != null, "Arguments cannot be null");
        Preconditions.checkArgument(alias != null, "Alias cannot be null");

        CommandSourceStack icommandlistener = getListener(sender);
        ParseResults<CommandSourceStack> parsed = dispatcher.getDispatcher().parse(toDispatcher(args, getName()), icommandlistener);

        List<String> results = new ArrayList<>();
        dispatcher.getDispatcher().getCompletionSuggestions(parsed).thenAccept((suggestions) -> {
            suggestions.getList().forEach((s) -> results.add(s.getText()));
        });

        return results;
    }

    public static CommandSourceStack getListener(CommandSender sender) {
        if (sender instanceof Entity) {
            if (sender instanceof CommandMinecart) {
                return ((MinecartCommandBlock) ((CraftMinecartCommand) sender).getHandle()).getCommandBlock().createCommandSourceStack();
            }

            return ((CraftEntity) sender).getHandle().createCommandSourceStack();
        }
        if (sender instanceof BlockCommandSender) {
            return ((CraftBlockCommandSender) sender).getWrapper();
        }
        if (sender instanceof RemoteConsoleCommandSender) {
            return ((CraftRemoteConsoleCommandSender) sender).getListener().createCommandSourceStack();
        }
        if (sender instanceof ConsoleCommandSender) {
            return ((CraftServer) sender.getServer()).getServer().createCommandSourceStack();
        }
        if (sender instanceof ProxiedCommandSender) {
            return ((ProxiedNativeCommandSender) sender).getHandle();
        }

        throw new IllegalArgumentException("Cannot make " + sender + " a vanilla command listener");
    }

    public static String getPermission(CommandNode<CommandSourceStack> vanillaCommand) {
        return "minecraft.command." + ((vanillaCommand.getRedirect() == null) ? vanillaCommand.getName() : vanillaCommand.getRedirect().getName());
    }

    private String toDispatcher(String[] args, String name) {
        return name + ((args.length > 0) ? " " + Joiner.on(' ').join(args) : "");
    }
}
