package net.vassbo.door_lock.util;

import org.apache.commons.codec.binary.Base64;

import net.minecraft.item.ItemStack;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.item.ModItems;

public class KeyPass {
    public static String getKeyHash(String password) {
        // simple password encoding stored in locked blocks
        return Base64.encodeBase64String(password.getBytes());
    }

    public static boolean checkHashMatch(String password, String passHash) {
        return getKeyHash(password).equals(passHash);
    }

    public static boolean isKeyItem(ItemStack stack) {
        return isKeyItem(stack, true);
    }

    public static boolean isKeyItem(ItemStack stack, boolean includeUniversal) {
        return stack.isOf(ModItems.KEY_ITEM) || stack.isOf(ModItems.GOLDEN_KEY_ITEM) || ((includeUniversal || ModConfig.UNIVERSAL_KEY_ON_IRON_TYPE) && stack.isOf(ModItems.UNIVERSAL_KEY_ITEM));
    }
}
