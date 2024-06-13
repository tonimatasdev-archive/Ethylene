package org.bukkit.craftbukkit.v1_21_R1.packs;

import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftChatMessage;
import org.bukkit.packs.ResourcePack;

import java.util.UUID;

public class CraftResourcePack implements ResourcePack {

    private final MinecraftServer.ServerResourcePackInfo handle;

    public CraftResourcePack(MinecraftServer.ServerResourcePackInfo handle) {
        this.handle = handle;
    }

    @Override
    public UUID getId() {
        return this.handle.id();
    }

    @Override
    public String getUrl() {
        return this.handle.url();
    }

    @Override
    public String getHash() {
        return this.handle.hash();
    }

    @Override
    public String getPrompt() {
        return (this.handle.prompt() == null) ? "" : CraftChatMessage.fromComponent(this.handle.prompt());
    }

    @Override
    public boolean isRequired() {
        return this.handle.isRequired();
    }

    @Override
    public String toString() {
        return "CraftResourcePack{id=" + this.getId() + ",url=" + this.getUrl() + ",hash=" + this.getHash() + ",prompt=" + this.getPrompt() + ",required=" + this.isRequired() + "}";
    }
}
