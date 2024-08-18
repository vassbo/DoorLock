package net.vassbo.door_lock.util;

import net.minecraft.item.ItemStack;
import net.vassbo.door_lock.item.ModItems;

public class KeyPass {
    public static String getKeyHash(String password) {
        // WIP hash pass

        return password;
    }

    public static boolean checkHashMatch(String password, String passHash) {
        return getKeyHash(password).contains(passHash);
    }

    public static boolean isKeyItem(ItemStack stack) {
        return stack.isOf(ModItems.KEY_ITEM) || stack.isOf(ModItems.GOLDEN_KEY_ITEM) || stack.isOf(ModItems.UNIVERSAL_KEY_ITEM);
    }

    // public static boolean isKeyItem(ItemStack stack) {
    //     String itemId = stack.getItem().toString();
    //     Identifier identifier = ItemHelper.identifierById(itemId);
    //     if (identifier.getNamespace() != DoorLock.MOD_ID) return false;

    //     return identifier.getPath().contains("key");
    // }
}
