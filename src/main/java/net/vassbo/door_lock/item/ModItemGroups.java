package net.vassbo.door_lock.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroup.DisplayContext;
import net.minecraft.item.ItemGroup.Entries;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.vassbo.door_lock.DoorLock;

public class ModItemGroups {
    private static Item MOD_GROUP_ICON_ITEM = ModItems.KEY_ITEM;
    public static final ItemGroup door_lock_GROUP = registerItemGroup("door_lock_group", "door_lock.modname", MOD_GROUP_ICON_ITEM, ModItemGroups::addToCustomInventory);

    private static void addToCustomInventory(DisplayContext displayContext, Entries entries) {
        entries.add(ModItems.KEY_ITEM);
        entries.add(ModItems.GOLDEN_KEY_ITEM);
        entries.add(ModItems.UNIVERSAL_KEY_ITEM);
    }

    // HELPERS

	private static ItemGroup registerItemGroup(String id, String name, Item icon, ItemGroup.EntryCollector entryList) {
		return Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(DoorLock.MOD_ID, id),
            FabricItemGroup.builder()
            .displayName(Text.translatable(name))
            .icon(() -> new ItemStack(icon))
            .entries(entryList)
            .build()
        );
    }

    // INITIALIZE

    public static void init() {
    }
}
