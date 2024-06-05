package dev.tonimatas.ethylene.mixins.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.tonimatas.ethylene.link.commands.arguments.EntityArgumentLink;
import dev.tonimatas.ethylene.link.commands.arguments.selector.EntitySelectorParserLink;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(EntityArgument.class)
public abstract class EntityArgumentMixin implements EntityArgumentLink {
    @Shadow public abstract EntitySelector parse(StringReader p_91451_) throws CommandSyntaxException;

    @Unique public AtomicBoolean overridePermissions = new AtomicBoolean(false);
    
    @Override
    @Unique
    public EntitySelector parse(StringReader stringreader, boolean overridePermissions) throws CommandSyntaxException {
        this.overridePermissions.set(overridePermissions);
        return parse(stringreader);
    }
    
    @Redirect(method = "parse(Lcom/mojang/brigadier/StringReader;)Lnet/minecraft/commands/arguments/selector/EntitySelector;", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/selector/EntitySelectorParser;parse()Lnet/minecraft/commands/arguments/selector/EntitySelector;"))
    private EntitySelector craftbukkit$parse(EntitySelectorParser instance) throws CommandSyntaxException {
        return ((EntitySelectorParserLink) instance).parse(overridePermissions.getAndSet(false));
    }
}
