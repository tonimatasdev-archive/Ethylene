package dev.tonimatas.ethylene.mixins.commands.arguments.selector;

import dev.tonimatas.ethylene.link.commands.CommandSourceStackLink;
import net.minecraft.commands.SharedSuggestionProvider;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommonHooks.class)
public class EntitySelectorMixin {
    @Redirect(method = "canUseEntitySelectors", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/SharedSuggestionProvider;hasPermission(I)Z"))
    private static boolean craftbukkit$checkPermissions(SharedSuggestionProvider instance, int i) {
        return ((CommandSourceStackLink) (Object) instance).hasPermission(2, "minecraft.command.selector");
    }
}
