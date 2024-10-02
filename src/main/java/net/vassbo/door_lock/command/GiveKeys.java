package net.vassbo.door_lock.command;

import java.util.Random;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.vassbo.door_lock.item.KeyItem;
import net.vassbo.door_lock.item.ModItems;

public class GiveKeys {
    public static int giveKeys(CommandContext<ServerCommandSource> context, String command) {
        int value = IntegerArgumentType.getInteger(context, "amount");
        // int startIndex = IntegerArgumentType.getInteger(context, "startIndex");
        if (value < 1) value = 1;

        PlayerEntity player = context.getSource().getPlayer();
        Random random = new Random();

        for (int i = 0; i < value; i++) {
            String password = String.format("%08d", random.nextInt(100000000)); // random 8 digit number
            String name = "#" + String.format("%0" + Integer.toString(value).length() + "d", (i + 1));

            ItemStack key = ModItems.KEY_ITEM.getDefaultStack();
            KeyItem.setPassData(key, password);
            key.set(DataComponentTypes.CUSTOM_NAME, Text.literal(name));

            player.giveItemStack(key);
        }

        ModCommands.feedback(context, Text.translatable("command.give_keys", value).getString());
        return 1;
    }
}
