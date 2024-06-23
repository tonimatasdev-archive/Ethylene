package org.bukkit.craftbukkit.v1_21_R1.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.authlib.GameProfile;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R1.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaSkull extends CraftMetaItem implements SkullMeta {

    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKeyType<ResolvableProfile> SKULL_PROFILE = new ItemMetaKeyType<>(DataComponents.PROFILE, "SkullProfile");

    static final ItemMetaKey SKULL_OWNER = new ItemMetaKey("skull-owner");

    @ItemMetaKey.Specific(ItemMetaKey.Specific.To.NBT)
    static final ItemMetaKey BLOCK_ENTITY_TAG = new ItemMetaKey("BlockEntityTag");
    static final ItemMetaKeyType<ResourceLocation> NOTE_BLOCK_SOUND = new ItemMetaKeyType<>(DataComponents.NOTE_BLOCK_SOUND, "note_block_sound");
    static final int MAX_OWNER_LENGTH = 16;

    private GameProfile profile;
    private ResourceLocation noteBlockSound;

    CraftMetaSkull(CraftMetaItem meta) {
        super(meta);
        if (!(meta instanceof CraftMetaSkull)) {
            return;
        }
        CraftMetaSkull skullMeta = (CraftMetaSkull) meta;
        this.setProfile(skullMeta.profile);
        this.noteBlockSound = skullMeta.noteBlockSound;
    }

    CraftMetaSkull(DataComponentPatch tag) {
        super(tag);

        getOrEmpty(tag, SKULL_PROFILE).ifPresent((resolvableProfile) -> {
            this.setProfile(resolvableProfile.gameProfile());
        });

        getOrEmpty(tag, NOTE_BLOCK_SOUND).ifPresent((minecraftKey) -> {
            this.noteBlockSound = minecraftKey;
        });
    }

    CraftMetaSkull(Map<String, Object> map) {
        super(map);
        if (profile == null) {
            Object object = map.get(SKULL_OWNER.BUKKIT);
            if (object instanceof PlayerProfile) {
                setOwnerProfile((PlayerProfile) object);
            } else {
                setOwner(SerializableMeta.getString(map, SKULL_OWNER.BUKKIT, true));
            }
        }

        if (noteBlockSound == null) {
            Object object = map.get(NOTE_BLOCK_SOUND.BUKKIT);
            if (object != null) {
                setNoteBlockSound(NamespacedKey.fromString(object.toString()));
            }
        }
    }

    @Override
    void deserializeInternal(CompoundTag tag, Object context) {
        super.deserializeInternal(tag, context);

        if (tag.contains(SKULL_PROFILE.NBT, CraftMagicNumbers.NBT.TAG_COMPOUND)) {
            CompoundTag skullTag = tag.getCompound(SKULL_PROFILE.NBT);
            // convert type of stored Id from String to UUID for backwards compatibility
            if (skullTag.contains("Id", CraftMagicNumbers.NBT.TAG_STRING)) {
                UUID uuid = UUID.fromString(skullTag.getString("Id"));
                skullTag.putUUID("Id", uuid);
            }

            this.setProfile(ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, skullTag).result().get().gameProfile());
        }

        if (tag.contains(BLOCK_ENTITY_TAG.NBT, CraftMagicNumbers.NBT.TAG_COMPOUND)) {
            CompoundTag CompoundTag = tag.getCompound(BLOCK_ENTITY_TAG.NBT).copy();
            if (CompoundTag.contains(NOTE_BLOCK_SOUND.NBT, 8)) {
                this.noteBlockSound = ResourceLocation.tryParse(CompoundTag.getString(NOTE_BLOCK_SOUND.NBT));
            }
        }
    }

    private void setProfile(GameProfile profile) {
        this.profile = profile;
    }

    @Override
    void applyToItem(CraftMetaItem.Applicator tag) {
        super.applyToItem(tag);

        if (profile != null) {
            // SPIGOT-6558: Set initial textures
            tag.put(SKULL_PROFILE, new ResolvableProfile(profile));
            // Fill in textures
            PlayerProfile ownerProfile = new CraftPlayerProfile(profile); // getOwnerProfile may return null
            if (ownerProfile.getTextures().isEmpty()) {
                ownerProfile.update().thenAccept((filledProfile) -> {
                    setOwnerProfile(filledProfile);
                    tag.put(SKULL_PROFILE, new ResolvableProfile(profile));
                });
            }
        }

        if (noteBlockSound != null) {
            tag.put(NOTE_BLOCK_SOUND, this.noteBlockSound);
        }
    }

    @Override
    boolean isEmpty() {
        return super.isEmpty() && isSkullEmpty();
    }

    boolean isSkullEmpty() {
        return profile == null && noteBlockSound == null;
    }

    @Override
    public CraftMetaSkull clone() {
        return (CraftMetaSkull) super.clone();
    }

    @Override
    public boolean hasOwner() {
        return profile != null && !profile.getName().isEmpty();
    }

    @Override
    public String getOwner() {
        return hasOwner() ? profile.getName() : null;
    }

    @Override
    public OfflinePlayer getOwningPlayer() {
        if (hasOwner()) {
            if (!profile.getId().equals(Util.NIL_UUID)) {
                return Bukkit.getOfflinePlayer(profile.getId());
            }

            if (!profile.getName().isEmpty()) {
                return Bukkit.getOfflinePlayer(profile.getName());
            }
        }

        return null;
    }

    @Override
    public boolean setOwner(String name) {
        if (name != null && name.length() > MAX_OWNER_LENGTH) {
            return false;
        }

        if (name == null) {
            setProfile(null);
        } else {
            setProfile(new GameProfile(Util.NIL_UUID, name));
        }

        return true;
    }

    @Override
    public boolean setOwningPlayer(OfflinePlayer owner) {
        if (owner == null) {
            setProfile(null);
        } else if (owner instanceof CraftPlayer) {
            setProfile(((CraftPlayer) owner).getProfile());
        } else {
            setProfile(new GameProfile(owner.getUniqueId(), owner.getName()));
        }

        return true;
    }

    @Override
    public PlayerProfile getOwnerProfile() {
        if (!hasOwner()) {
            return null;
        }

        return new CraftPlayerProfile(profile);
    }

    @Override
    public void setOwnerProfile(PlayerProfile profile) {
        if (profile == null) {
            setProfile(null);
        } else {
            setProfile(CraftPlayerProfile.validateSkullProfile(((CraftPlayerProfile) profile).buildGameProfile()));
        }
    }

    @Override
    public void setNoteBlockSound(NamespacedKey noteBlockSound) {
        if (noteBlockSound == null) {
            this.noteBlockSound = null;
        } else {
            this.noteBlockSound = CraftNamespacedKey.toMinecraft(noteBlockSound);
        }
    }

    @Override
    public NamespacedKey getNoteBlockSound() {
        return (this.noteBlockSound == null) ? null : CraftNamespacedKey.fromMinecraft(this.noteBlockSound);
    }

    @Override
    int applyHash() {
        final int original;
        int hash = original = super.applyHash();
        if (hasOwner()) {
            hash = 61 * hash + profile.hashCode();
        }
        if (this.noteBlockSound != null) {
            hash = 61 * hash + noteBlockSound.hashCode();
        }
        return original != hash ? CraftMetaSkull.class.hashCode() ^ hash : hash;
    }

    @Override
    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaSkull) {
            CraftMetaSkull that = (CraftMetaSkull) meta;

            // SPIGOT-5403: equals does not check properties
            return (this.profile != null ? that.profile != null && this.profile.equals(that.profile) && this.profile.getProperties().equals(that.profile.getProperties()) : that.profile == null) && Objects.equals(this.noteBlockSound, that.noteBlockSound);
        }
        return true;
    }

    @Override
    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaSkull || isSkullEmpty());
    }

    @Override
    Builder<String, Object> serialize(Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.profile != null) {
            builder.put(SKULL_OWNER.BUKKIT, new CraftPlayerProfile(this.profile));
        }
        NamespacedKey namespacedKeyNB = this.getNoteBlockSound();
        if (namespacedKeyNB != null) {
            builder.put(NOTE_BLOCK_SOUND.BUKKIT, namespacedKeyNB.toString());
        }
        return builder;
    }
}
