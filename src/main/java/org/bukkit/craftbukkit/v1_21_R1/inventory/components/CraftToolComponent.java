package org.bukkit.craftbukkit.v1_21_R1.inventory.components;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_21_R1.block.CraftBlockType;
import org.bukkit.craftbukkit.v1_21_R1.inventory.SerializableMeta;
import org.bukkit.craftbukkit.v1_21_R1.tag.CraftBlockTag;
import org.bukkit.inventory.meta.components.ToolComponent;

import java.util.*;
import java.util.stream.Collectors;

@SerializableAs("Tool")
public final class CraftToolComponent implements ToolComponent {

    private Tool handle;

    public CraftToolComponent(Tool tool) {
        this.handle = tool;
    }

    public CraftToolComponent(CraftToolComponent tool) {
        this.handle = tool.handle;
    }

    public CraftToolComponent(Map<String, Object> map) {
        Float speed = SerializableMeta.getObject(Float.class, map, "default-mining-speed", false);
        Integer damage = SerializableMeta.getObject(Integer.class, map, "damage-per-block", false);

        ImmutableList.Builder<ToolRule> rules = ImmutableList.builder();
        Iterable<?> rawRuleList = SerializableMeta.getObject(Iterable.class, map, "rules", true);
        if (rawRuleList != null) {
            for (Object obj : rawRuleList) {
                Preconditions.checkArgument(obj instanceof ToolRule, "Object (%s) in rule list is not valid", obj.getClass());

                CraftToolRule rule = new CraftToolRule((ToolRule) obj);
                if (rule.handle.blocks().size() > 0) {
                    rules.add(rule);
                }
            }
        }

        this.handle = new Tool(rules.build().stream().map(CraftToolRule::new).map(CraftToolRule::getHandle).toList(), speed, damage);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("default-mining-speed", getDefaultMiningSpeed());
        result.put("damage-per-block", getDamagePerBlock());
        result.put("rules", getRules());
        return result;
    }

    public Tool getHandle() {
        return handle;
    }

    @Override
    public float getDefaultMiningSpeed() {
        return handle.defaultMiningSpeed();
    }

    @Override
    public void setDefaultMiningSpeed(float speed) {
        handle = new Tool(handle.rules(), speed, handle.damagePerBlock());
    }

    @Override
    public int getDamagePerBlock() {
        return handle.damagePerBlock();
    }

    @Override
    public void setDamagePerBlock(int damage) {
        Preconditions.checkArgument(damage >= 0, "damage must be >= 0, was %d", damage);
        handle = new Tool(handle.rules(), handle.defaultMiningSpeed(), damage);
    }

    @Override
    public List<ToolRule> getRules() {
        return handle.rules().stream().map(CraftToolRule::new).collect(Collectors.toList());
    }

    @Override
    public void setRules(List<ToolRule> rules) {
        Preconditions.checkArgument(rules != null, "rules must not be null");
        handle = new Tool(rules.stream().map(CraftToolRule::new).map(CraftToolRule::getHandle).toList(), handle.defaultMiningSpeed(), handle.damagePerBlock());
    }

    @Override
    public ToolRule addRule(Material block, Float speed, Boolean correctForDrops) {
        Preconditions.checkArgument(block != null, "block must not be null");
        Preconditions.checkArgument(block.isBlock(), "block must be a block type, given %s", block.getKey());

        Holder.Reference<Block> nmsBlock = CraftBlockType.bukkitToMinecraft(block).builtInRegistryHolder();
        return addRule(HolderSet.direct(nmsBlock), speed, correctForDrops);
    }

    @Override
    public ToolRule addRule(Collection<Material> blocks, Float speed, Boolean correctForDrops) {
        List<Holder.Reference<Block>> nmsBlocks = new ArrayList<>(blocks.size());

        for (Material material : blocks) {
            Preconditions.checkArgument(material.isBlock(), "blocks contains non-block type: %s", material.getKey());
            nmsBlocks.add(CraftBlockType.bukkitToMinecraft(material).builtInRegistryHolder());
        }

        return addRule(HolderSet.direct(nmsBlocks), speed, correctForDrops);
    }

    @Override
    public ToolRule addRule(Tag<Material> tag, Float speed, Boolean correctForDrops) {
        Preconditions.checkArgument(tag instanceof CraftBlockTag, "tag must be a block tag");
        return addRule(((CraftBlockTag) tag).getHandle(), speed, correctForDrops);
    }

    private ToolRule addRule(HolderSet<Block> blocks, Float speed, Boolean correctForDrops) {
        Tool.Rule rule = new Tool.Rule(blocks, Optional.ofNullable(speed), Optional.ofNullable(correctForDrops));

        List<Tool.Rule> rules = new ArrayList<>(handle.rules().size() + 1);
        rules.addAll(handle.rules());
        rules.add(rule);

        handle = new Tool(rules, handle.defaultMiningSpeed(), handle.damagePerBlock());
        return new CraftToolRule(rule);
    }

    @Override
    public boolean removeRule(ToolRule rule) {
        Preconditions.checkArgument(rule != null, "rule must not be null");

        List<Tool.Rule> rules = new ArrayList<>(handle.rules());
        boolean removed = rules.remove(((CraftToolRule) rule).handle);
        handle = new Tool(rules, handle.defaultMiningSpeed(), handle.damagePerBlock());

        return removed;
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
        final CraftToolComponent other = (CraftToolComponent) obj;
        return Objects.equals(this.handle, other.handle);
    }

    @Override
    public String toString() {
        return "CraftToolComponent{" + "handle=" + handle + '}';
    }

    @SerializableAs("ToolRule")
    public static class CraftToolRule implements ToolRule {

        private Tool.Rule handle;

        public CraftToolRule(Tool.Rule handle) {
            this.handle = handle;
        }

        public CraftToolRule(ToolRule bukkit) {
            Tool.Rule toCopy = ((CraftToolRule) bukkit).handle;
            this.handle = new Tool.Rule(toCopy.blocks(), toCopy.speed(), toCopy.correctForDrops());
        }

        public CraftToolRule(Map<String, Object> map) {
            Float speed = SerializableMeta.getObject(Float.class, map, "speed", false);
            Boolean correct = SerializableMeta.getObject(Boolean.class, map, "correct-for-drops", false);

            HolderSet<Block> blocks = null;
            Object blocksObject = SerializableMeta.getObject(Object.class, map, "blocks", false);
            if (blocksObject instanceof String blocksString && blocksString.startsWith("#")) { // Tag
                blocksString = blocksString.substring(1);
                ResourceLocation key = ResourceLocation.tryParse(blocksString);
                if (key != null) {
                    blocks = BuiltInRegistries.BLOCK.getTag(TagKey.create(Registries.BLOCK, key)).orElse(null);
                }
            } else if (blocksObject instanceof List blocksList) { // List of blocks
                List<Holder.Reference<Block>> blockHolders = new ArrayList<>(blocksList.size());

                for (Object entry : blocksList) {
                    ResourceLocation key = ResourceLocation.tryParse(entry.toString());
                    if (key == null) {
                        continue;
                    }

                    BuiltInRegistries.BLOCK.getHolder(key).ifPresent(blockHolders::add);
                }

                blocks = HolderSet.direct(blockHolders);
            } else {
                throw new IllegalArgumentException("blocks" + "(" + blocksObject + ") is not a valid String or List");
            }

            if (blocks == null) {
                blocks = HolderSet.empty();
            }

            this.handle = new Tool.Rule(blocks, Optional.ofNullable(speed), Optional.ofNullable(correct));
        }

        @Override
        public Map<String, Object> serialize() {
            Map<String, Object> result = new LinkedHashMap<>();

            handle.blocks().unwrap()
                    .ifLeft(key -> result.put("blocks", "#" + key.location().toString())) // Tag
                    .ifRight(blocks -> result.put("blocks", blocks.stream().map((block) -> block.unwrapKey().orElseThrow().location().toString()).toList())); // List of blocks

            Float speed = getSpeed();
            if (speed != null) {
                result.put("speed", speed);
            }

            Boolean correct = isCorrectForDrops();
            if (correct != null) {
                result.put("correct-for-drops", correct);
            }

            return result;
        }

        public Tool.Rule getHandle() {
            return handle;
        }

        @Override
        public Collection<Material> getBlocks() {
            return handle.blocks().stream().map(Holder::value).map(CraftBlockType::minecraftToBukkit).collect(Collectors.toList());
        }

        @Override
        public void setBlocks(Material block) {
            Preconditions.checkArgument(block != null, "block must not be null");
            Preconditions.checkArgument(block.isBlock(), "block must be a block type, given %s", block.getKey());
            handle = new Tool.Rule(HolderSet.direct(CraftBlockType.bukkitToMinecraft(block).builtInRegistryHolder()), handle.speed(), handle.correctForDrops());
        }

        @Override
        public void setBlocks(Collection<Material> blocks) {
            Preconditions.checkArgument(blocks != null, "blocks must not be null");
            for (Material material : blocks) {
                Preconditions.checkArgument(material.isBlock(), "blocks contains non-block type: %s", material.getKey());
            }

            handle = new Tool.Rule(HolderSet.direct((List) blocks.stream().map(CraftBlockType::bukkitToMinecraft).map(Block::builtInRegistryHolder).collect(Collectors.toList())), handle.speed(), handle.correctForDrops());
        }

        @Override
        public void setBlocks(Tag<Material> tag) {
            Preconditions.checkArgument(tag instanceof CraftBlockTag, "tag must be a block tag");
            handle = new Tool.Rule(((CraftBlockTag) tag).getHandle(), handle.speed(), handle.correctForDrops());
        }

        @Override
        public Float getSpeed() {
            return handle.speed().orElse(null);
        }

        @Override
        public void setSpeed(Float speed) {
            handle = new Tool.Rule(handle.blocks(), Optional.ofNullable(speed), handle.correctForDrops());
        }

        @Override
        public Boolean isCorrectForDrops() {
            return handle.correctForDrops().orElse(null);
        }

        @Override
        public void setCorrectForDrops(Boolean correct) {
            handle = new Tool.Rule(handle.blocks(), handle.speed(), Optional.ofNullable(correct));
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.handle);
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
            final CraftToolRule other = (CraftToolRule) obj;
            return Objects.equals(this.handle, other.handle);
        }

        @Override
        public String toString() {
            return "CraftToolRule{" + "handle=" + handle + '}';
        }
    }
}
