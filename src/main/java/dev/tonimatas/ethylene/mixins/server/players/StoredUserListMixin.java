package dev.tonimatas.ethylene.mixins.server.players;

import net.minecraft.server.players.StoredUserList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StoredUserList.class)
public class StoredUserListMixin {
    @Inject(method = "contains", at = @At("HEAD"))
    private <K> void contains(K p_11397_, CallbackInfoReturnable<Boolean> cir) {
        ((StoredUserList) (Object) this).removeExpired(); // CraftBukkit - SPIGOT-7589: Consistently remove expired entries to mirror .get(...)
    }
}
