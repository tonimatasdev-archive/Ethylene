package dev.tonimatas.ethylene.mixins.commands.arguments.selector;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.tonimatas.ethylene.link.commands.arguments.selector.EntitySelectorParserLink;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(EntitySelectorParser.class)
public abstract class EntitySelectorParserMixin implements EntitySelectorParserLink {
    @Shadow public abstract EntitySelector parse() throws CommandSyntaxException;

    @Shadow protected abstract void parseSelector() throws CommandSyntaxException;

    @Shadow private boolean usesSelectors;
    @Unique public AtomicBoolean overridePermissions = new AtomicBoolean(false);
    
    @Override
    @Unique
    public EntitySelector parse(boolean overridePermissions) throws CommandSyntaxException {
        this.overridePermissions.set(overridePermissions);
        return parse();
    }

    @Override
    @Unique
    public void parseSelector(boolean overridePermissions) throws CommandSyntaxException {
        this.overridePermissions.set(overridePermissions);
        parseSelector();
    }
    
    @Redirect(method = "parse", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/selector/EntitySelectorParser;parseSelector()V"))
    public void craftbukkit$parse(EntitySelectorParser instance) throws CommandSyntaxException {
        parseSelector(overridePermissions.get());
    }
    
    @ModifyExpressionValue(method = "parseSelector", at = @At(value = "FIELD", target = "Lnet/minecraft/commands/arguments/selector/EntitySelectorParser;usesSelectors:Z", opcode = Opcodes.PUTFIELD))
    public void craftbukkit$parseSelector() {
        usesSelectors = !overridePermissions.getAndSet(false);
    }
}
