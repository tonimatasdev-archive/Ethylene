package dev.tonimatas.ethylene.mixins.server.bossevents;

import dev.tonimatas.ethylene.link.server.bossevents.CustomBossEventLink;
import net.minecraft.server.bossevents.CustomBossEvent;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_21_R1.boss.CraftKeyedBossbar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CustomBossEvent.class)
public class CustomBossEventMixin implements CustomBossEventLink {
    // CraftBukkit start
    @Unique private KeyedBossBar bossBar;

    @Override
    @Unique
    public KeyedBossBar getBukkitEntity() {net.minecraft.server.players.StoredUserList
        if (bossBar == null) {
            bossBar = new CraftKeyedBossbar((CustomBossEvent) (Object) this);
        }
        return bossBar;
    }
    // CraftBukkit end
}
