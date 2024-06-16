package dev.tonimatas.ethylene.mixins.network.chat;

import dev.tonimatas.ethylene.annotations.NewConstructor;
import dev.tonimatas.ethylene.annotations.SelfConstructorStub;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(TextColor.class)
public class TextColorMixin {
    @Inject(method = "lambda$static$0", at = @At("HEAD"))
    private static void ethylene$cinit$1(ChatFormatting p_237301_, CallbackInfoReturnable<TextColor> cir){
        ethylene$format = p_237301_;
    }
    
    @Unique public ChatFormatting format = null;
    @Unique private static @Nullable ChatFormatting ethylene$format = null;

    @Unique
    private static String wrap(String passthrough, @Nullable ChatFormatting format){
        ethylene$format = format;
        return passthrough;
    }
    
    @SelfConstructorStub
    private void init(int p_131263_, String p_131264_){}
    
    @NewConstructor
    private void init(int p_131263_, String p_131264_, @Nullable ChatFormatting format) {
        init(p_131263_, wrap(p_131264_, format));
    }

    @Inject(method = "<init>(ILjava/lang/String;)V", at = @At("TAIL"))
    private void ethylene$init(int pValue, String pName, CallbackInfo ci){
        this.format = ethylene$format;
        ethylene$format = null;
    }

    @Inject(method = "<init>(I)V", at = @At("TAIL"))
    private void ethylene$init(int pValue, CallbackInfo ci){
        this.format = null;
    }
}
