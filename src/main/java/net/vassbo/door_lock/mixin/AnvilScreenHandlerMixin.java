package net.vassbo.door_lock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.vassbo.door_lock.item.KeyItem;
import net.vassbo.door_lock.util.KeyPass;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Shadow private String newItemName;

    @Inject(at = @At("HEAD"), method = "onTakeOutput")
	private void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo info) {
        // player.sendMessage(Text.literal("TAKE: " +  stack.getName()));
        // player.sendMessage(Text.literal("TAKE: " +  stack.isOf(ModItems.KEY_ITEM)));
        // player.sendMessage(Text.literal("TAKE: " +  (stack.getItem() == ModItems.KEY_ITEM)));
        if (KeyPass.isKeyItem(stack)) {
            // player.sendMessage(Text.literal("IS KEY!!"));
            // this.input.setStack(0, ItemStack.EMPTY);
            // return handStack;

            int passStartIndex = newItemName.lastIndexOf("#");
            if (passStartIndex >= 0) {
                String password = newItemName.substring(passStartIndex + 1).trim();
                KeyItem.setPassData(stack, password);
                
                newItemName = newItemName.substring(0, passStartIndex).trim();
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(newItemName));
            }
        }
    }
}
