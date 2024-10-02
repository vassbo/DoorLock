package net.vassbo.door_lock.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.vassbo.door_lock.item.KeyItem;
import net.vassbo.door_lock.util.KeyPass;

public class UpdateKey {
    public static int setPassword(CommandContext<ServerCommandSource> context, String command) {
        final String password = StringArgumentType.getString(context, "value");
        PlayerEntity player = context.getSource().getPlayer();

        // get item in main hand
        ItemStack handItem = player.getHandItems().iterator().next();
        if (KeyPass.isKeyItem(handItem)) {
            KeyItem.setPassData(handItem, password);
            player.setStackInHand(Hand.MAIN_HAND, handItem);

            ModCommands.feedback(context, Text.translatable("command.update_password", password).getString());
            return 1;
        }

        ModCommands.feedback(context, Text.translatable("command.update_password.error").getString());
        return 1;
    }
}
