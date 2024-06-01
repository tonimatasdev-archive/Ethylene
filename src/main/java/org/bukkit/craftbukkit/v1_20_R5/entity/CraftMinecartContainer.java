package org.bukkit.craftbukkit.v1_20_R5.entity;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import org.bukkit.craftbukkit.v1_20_R5.CraftLootTable;
import org.bukkit.craftbukkit.v1_20_R5.CraftServer;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public abstract class CraftMinecartContainer extends CraftMinecart implements Lootable {

    public CraftMinecartContainer(CraftServer server, AbstractMinecart entity) {
        super(server, entity);
    }

    @Override
    public AbstractMinecartContainer getHandle() {
        return (AbstractMinecartContainer) entity;
    }

    @Override
    public void setLootTable(LootTable table) {
        setLootTable(table, getSeed());
    }

    @Override
    public LootTable getLootTable() {
        return CraftLootTable.minecraftToBukkit(getHandle().lootTable);
    }

    @Override
    public void setSeed(long seed) {
        setLootTable(getLootTable(), seed);
    }

    @Override
    public long getSeed() {
        return getHandle().lootTableSeed;
    }

    private void setLootTable(LootTable table, long seed) {
        getHandle().setLootTable(CraftLootTable.bukkitToMinecraft(table), seed);
    }
}
