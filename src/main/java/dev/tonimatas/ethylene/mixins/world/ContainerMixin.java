package dev.tonimatas.ethylene.mixins.world;

import dev.tonimatas.ethylene.link.world.ContainerLink;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Container.class)
public interface ContainerMixin extends ContainerLink {
}
