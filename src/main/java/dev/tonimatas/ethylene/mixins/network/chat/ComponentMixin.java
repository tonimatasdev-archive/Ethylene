package dev.tonimatas.ethylene.mixins.network.chat;

import com.google.common.collect.Streams;
import dev.tonimatas.ethylene.link.network.chat.ComponentStreamLink;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;
import java.util.stream.Stream;

@Mixin(Component.class)
public class ComponentMixin implements Iterable<Component>, ComponentStreamLink<Component> { // CraftBukkit
    // CraftBukkit start
    @Unique
    public Stream<Component> stream() {
        return Streams.concat(Stream.of((Component) this), ((Component) this).getSiblings().stream().flatMap(o -> ((ComponentStreamLink<Component>)o).stream()));
    }

    @Override
    public @NotNull Iterator<Component> iterator() {
        return this.stream().iterator();
    }
    // CraftBukkit end
}
