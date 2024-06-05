package dev.tonimatas.ethylene.link.commands;

import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;

public interface CommandsLink {
    void dispatchServerCommand(CommandSourceStack sender, String command);

    void performPrefixedCommand(CommandSourceStack commandlistenerwrapper, String s, String label);

    void performCommand(ParseResults<CommandSourceStack> parseresults, String s, String label);
}
