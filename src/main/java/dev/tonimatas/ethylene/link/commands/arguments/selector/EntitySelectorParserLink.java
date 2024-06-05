package dev.tonimatas.ethylene.link.commands.arguments.selector;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelector;

public interface EntitySelectorParserLink {
    EntitySelector parse(boolean overridePermissions) throws CommandSyntaxException;

    void parseSelector(boolean overridePermissions) throws CommandSyntaxException;
}
