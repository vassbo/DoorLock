package net.vassbo.door_lock.data;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NbtCompound;

public class DataHelpers {
    // STRING LISTS

    public static NbtCompound storeList(NbtCompound playerNbt, String key, List<String> list) {
        int listLength = list.size();
        playerNbt.putInt(key + "_SIZE", listLength);

        int index = -1;
        for (String value : list) {
            index++;
            playerNbt.putString(key + ":" + index, value);
        };

        return playerNbt;
    }

    public static List<String> getList(NbtCompound playerNbt, String key) {
        int listLength = playerNbt.getInt(key + "_SIZE");
        List<String> list = new ArrayList<>();

        for (int i = 0; i < listLength; i++) {
            list.add(playerNbt.getString(key + ":" + i));
        }

        return list;
    }
}
