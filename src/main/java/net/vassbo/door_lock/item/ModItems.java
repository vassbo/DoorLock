package net.vassbo.door_lock.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.vassbo.door_lock.DoorLock;

public class ModItems {
    public static final Item KEY_ITEM = registerItem("key", new KeyItem(new Item.Settings()));
    public static final Item GOLDEN_KEY_ITEM = registerItem("golden_key", new KeyItem(new Item.Settings()));
    public static final Item UNIVERSAL_KEY_ITEM = registerItem("universal_key", new UniversalKeyItem(new Item.Settings()));

    // HELPERS

	private static Item registerItem(String id, Item item) {
		Identifier itemID = Identifier.of(DoorLock.MOD_ID, id);
		Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

		return registeredItem;
    }

    // INITIALIZE

    public static void init() {
    }
}