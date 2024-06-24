package dev.tonimatas.ethylene.link.server;

import net.minecraft.server.MinecraftServer;

public interface MinecraftServerLink {
    default MinecraftServer getServer() {
        throw new RuntimeException("Not implemented yet");
    }
}
