package dev.tonimatas.ethylene.mixins.advancements;

import dev.tonimatas.ethylene.link.advancements.AdvancementHolderLink;
import net.minecraft.advancements.AdvancementHolder;
import org.bukkit.advancement.Advancement;
import org.bukkit.craftbukkit.v1_20_R5.advancement.CraftAdvancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AdvancementHolder.class)
public class AdvancementHolderMixin implements AdvancementHolderLink {
    @Override
    @Unique
    public Advancement toBukkit() {
        return new CraftAdvancement((AdvancementHolder) (Object) this);
    }
}
