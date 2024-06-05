package dev.tonimatas.ethylene;

import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.concurrent.atomic.AtomicReference;

public class Variables {
    public static AtomicReference<EntityPotionEffectEvent.Cause> ethylene$mobEffectUtil$cause = new AtomicReference<>(EntityPotionEffectEvent.Cause.UNKNOWN);
}
