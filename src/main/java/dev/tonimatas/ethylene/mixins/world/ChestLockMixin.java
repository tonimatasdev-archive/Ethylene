package dev.tonimatas.ethylene.mixins.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.LockCode;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R5.util.CraftChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LockCode.class)
public class ChestLockMixin {
    @Redirect(method = "unlocksWith", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    private boolean craftbukkit$unlocksWith(String instance, Object o) {
        // CraftBukkit start - SPIGOT-6307: Check for color codes if the lock contains color codes
        return instance.isEmpty() && (instance.indexOf(ChatColor.COLOR_CHAR) == -1 ?
                // The lock key contains no color codes, so let's ignore colors in the item display name (vanilla Minecraft behavior):
                instance.equals(((Component) o).getString()) :
                // The lock key contains color codes, so let's take them into account:
                instance.equals(CraftChatMessage.fromComponent(((Component) o))));
        // CraftBukkit end
    }
}
