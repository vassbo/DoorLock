package net.vassbo.door_lock.util;

// Encountered an unexpected exception java.lang.NoClassDefFoundError: org/apache/commons/codec/binary/Base64
// Crashes in some cases
// import org.apache.commons.codec.binary.Base64;
// Base64.encodeBase64String(password.getBytes());

import net.minecraft.item.ItemStack;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.item.ModItems;

public class KeyPass {
    public static String getKeyHash(String password) {
        // simple password encoding stored in locked blocks
        return encode(password, password.length());
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

    // ENCODING

    public static String encode(String text, int key) {
        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char shifted = (char) (c + key); // shift ASCII value
            encoded.append(shifted);
        }

        return encoded.toString();
    }
}
