package org.bukkit.craftbukkit.v1_21_R1.inventory.components;

import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.JukeboxPlayable;
import org.bukkit.JukeboxSong;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_21_R1.CraftJukeboxSong;
import org.bukkit.craftbukkit.v1_21_R1.CraftRegistry;
import org.bukkit.craftbukkit.v1_21_R1.inventory.SerializableMeta;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@SerializableAs("JukeboxPlayable")
public final class CraftJukeboxComponent implements JukeboxPlayableComponent {

    private JukeboxPlayable handle;

    public CraftJukeboxComponent(JukeboxPlayable jukebox) {
        this.handle = jukebox;
    }

    public CraftJukeboxComponent(CraftJukeboxComponent jukebox) {
        this.handle = jukebox.handle;
    }

    public CraftJukeboxComponent(Map<String, Object> map) {
        String song = SerializableMeta.getObject(String.class, map, "song", false);
        Boolean showTooltip = SerializableMeta.getObject(boolean.class, map, "show-in-tooltip", true);

        this.handle = new JukeboxPlayable(new EitherHolder<>(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.parse(song))), (showTooltip != null) ? showTooltip : true);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("song", getSongKey().toString());
        result.put("show-in-tooltip", isShowInTooltip());
        return result;
    }

    public JukeboxPlayable getHandle() {
        return handle;
    }

    @Override
    public JukeboxSong getSong() {
        Optional<Holder<net.minecraft.world.item.JukeboxSong>> song = handle.song().unwrap(CraftRegistry.getMinecraftRegistry());
        return (song.isPresent()) ? CraftJukeboxSong.minecraftHolderToBukkit(song.get()) : null;
    }

    @Override
    public NamespacedKey getSongKey() {
        return CraftNamespacedKey.fromMinecraft(handle.song().key().location());
    }

    @Override
    public void setSong(JukeboxSong song) {
        Preconditions.checkArgument(song != null, "song cannot be null");

        handle = new JukeboxPlayable(new EitherHolder<>(CraftJukeboxSong.bukkitToMinecraftHolder(song)), handle.showInTooltip());
    }

    @Override
    public void setSongKey(NamespacedKey song) {
        Preconditions.checkArgument(song != null, "song cannot be null");

        handle = new JukeboxPlayable(new EitherHolder<>(ResourceKey.create(Registries.JUKEBOX_SONG, CraftNamespacedKey.toMinecraft(song))), handle.showInTooltip());
    }

    @Override
    public boolean isShowInTooltip() {
        return handle.showInTooltip();
    }

    @Override
    public void setShowInTooltip(boolean show) {
        handle = new JukeboxPlayable(handle.song(), show);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.handle);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CraftJukeboxComponent other = (CraftJukeboxComponent) obj;
        return Objects.equals(this.handle, other.handle);
    }

    @Override
    public String toString() {
        return "CraftJukeboxComponent{" + "handle=" + handle + '}';
    }
}