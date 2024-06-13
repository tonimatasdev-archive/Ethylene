package dev.tonimatas.ethylene.mixins;

import net.minecraft.CrashReport;
import net.minecraft.SystemReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Shadow @Final private SystemReport systemReport;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void craftbukkit$init(CallbackInfo ci) {
        this.systemReport.setDetail("CraftBukkit Information", new org.bukkit.craftbukkit.v1_21_R1.CraftCrashReport()); // CraftBukkit
    }
}
