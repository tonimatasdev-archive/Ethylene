package dev.tonimatas.ethylene.link.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelector;

public interface EntityArgumentLink {
    EntitySelector parse(StringReader stringreader, boolean overridePermissions) throws CommandSyntaxException;
}
