package net.vassbo.door_lock.data;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NbtCompound;

public class DataHelpers {
    // STRING LISTS

    public static NbtCompound storeList(NbtCompound nbt, String key, List<String> list) {
        int listLength = list.size();
        nbt.putInt(key + "_SIZE", listLength);

        int index = -1;
        for (String value : list) {
            index++;
            nbt.putString(key + ":" + index, value);
        };

        return nbt;
    }

    public static List<String> getList(NbtCompound nbt, String key) {
        int listLength = nbt.getInt(key + "_SIZE");
        List<String> list = new ArrayList<>();

        for (int i = 0; i < listLength; i++) {
            list.add(nbt.getString(key + ":" + i));
        }

        return list;
    }
}
