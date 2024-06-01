package dev.tonimatas.ethylene.mixins.commands;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.tree.CommandNode;
import dev.tonimatas.ethylene.link.commands.BukkitSenderLink;
import dev.tonimatas.ethylene.link.commands.CommandSourceStackLink;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.bukkit.command.CommandSender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandSourceStack.class)
public abstract class CommandSourceStackMixin implements CommandSourceStackLink {
    @Shadow public abstract ServerLevel getLevel();

    @Shadow @Final private int permissionLevel;
    @Shadow @Final public CommandSource source;
    @Unique public volatile CommandNode currentCommand; // CraftBukkit

    @Override
    @Unique
    public void setCurrentCommand(CommandNode currentCommand) {
        this.currentCommand = currentCommand;
    }

    @Override
    @Unique
    // CraftBukkit start
    public boolean hasPermission(int i, String bukkitPermission) {
        // World is null when loading functions
        return ((getLevel() == null || !getLevel().getCraftServer().ignoreVanillaPermissions) && this.permissionLevel >= i) || getBukkitSender().hasPermission(bukkitPermission);
    }
    // CraftBukkit end

    @Inject(method = "hasPermission", at = @At("HEAD"), cancellable = true)
    public void hasPermission(int p_81370_, CallbackInfoReturnable<Boolean> cir) {
        // CraftBukkit start
        CommandNode currentCommand = this.currentCommand;
        if (currentCommand != null) {
            cir.setReturnValue(hasPermission(p_81370_, org.bukkit.craftbukkit.v1_20_R5.command.VanillaCommandWrapper.getPermission(currentCommand)));
        }
        // CraftBukkit end
    }
    
    @Redirect(method = "broadcastToAdmins", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;isOp(Lcom/mojang/authlib/GameProfile;)Z"))
    public boolean craftbukkit$broadcastToAdmins$1(PlayerList instance, GameProfile p_11304_, @Local ServerPlayer serverplayer) {
        return serverplayer.getBukkitEntity().hasPermission("minecraft.admin.command_feedback");
    }

    @Override
    public CommandSender getBukkitSender() {
        return ((BukkitSenderLink) source).getBukkitSender((CommandSourceStack) (Object) this);
    }
}
