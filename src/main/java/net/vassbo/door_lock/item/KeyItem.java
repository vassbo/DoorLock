package net.vassbo.door_lock.item;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.util.DoorHelper;
import net.vassbo.door_lock.util.KeyPass;
import net.vassbo.door_lock.util.LockCheck;
import net.vassbo.door_lock.util.LockData;

public class KeyItem extends Item {
    private static String TOOLTIP_TEXT = "item_tooltip.door_lock.key";
    private static Formatting TOOLTIP_FORMAT = Formatting.GOLD;

    public KeyItem(Settings settings) {
		super(settings);
	}

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(TOOLTIP_FORMAT));

        @Nullable String password = getPassData(stack);
        if (password == null || !ModConfig.SHOW_PASSWORD_ON_KEY) return;

        // WIP shift to reveal (only client)
        // if (Screen.hasShiftDown()) {
            tooltip.add(Text.literal(password).formatted(Formatting.AQUA));
        // } else {
        //     tooltip.add(Text.translatable("Hold shift to reveal password").formatted(Formatting.GRAY));
        // }
    }

    @Override
	public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        // remove key with sneak (shift) click
        if (player.getServer() != null && player.isSneaking()) {
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockPos fixedPos = DoorHelper.getBottomHalf(world, pos);
            
            // block lock data
            @Nullable LockData lockData = LockCheck.getLockData(world, fixedPos);

            // get key in hands
            // this will not check the second key if holding two!
            @Nullable ItemStack keyStack = LockCheck.getKeyHandItem(player);

            boolean hasLock = lockData != null;
            if (hasLock) {
                if (keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM)) {
                    // WIP currently also removing key on iron types
                    LockCheck.removeLockData(world, lockData);
                    player.sendMessage(Text.translatable("key_remove.success"), true);
                    return ActionResult.PASS;
                }

                @Nullable String keyPass = KeyItem.getPassData(keyStack);
                boolean isCorrectKey = KeyPass.checkHashMatch(keyPass, lockData.keyHash);
                if (isCorrectKey) {
                    LockCheck.removeLockData(world, lockData);
                    player.sendMessage(Text.translatable("key_remove.success"), true);
                }
            }
        }

		return ActionResult.PASS;
	}
    
    // NBT DATA

    private static @Nullable String getData(final ItemStack stack) {
        @Nullable var data = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (data != null) {
            NbtCompound value = data.copyNbt();
            return value.getString(PASS_DATA_KEY);
        }

        return null;
    }
    
    private static void setData(ItemStack stack, Consumer<NbtCompound> func) {
        // if (stack.getItem() != this) return;

        // NbtCompound nbt = new NbtCompound();
        // nbt.putString(DATA_KEY, data);
        // NbtComponent component = NbtComponent.of(nbt);
        // stack.set(DataComponentTypes.CUSTOM_DATA, component);

        // apply to existing data, this ensures other data is kept
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(func));
    }

    // private static boolean hasMatchingNbt(ItemStack stack, NbtCompound requiredNbt) {
    //     // Checking if a certain NBT is a subset of the stack NBT
    //     // (also known as: "non-strict matching")
    //     return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).matches(requiredNbt);
    // }

    // PASSWORD

    private static final String PASS_DATA_KEY = "KeyHash";

    public static void setPassData(ItemStack stack, String pwdHash) {
        setData(stack, (nbt) -> {
            nbt.putString(PASS_DATA_KEY, pwdHash);
        });
    }

    public static @Nullable String getPassData(ItemStack stack) {
        return getData(stack);
    }

    // public static boolean checkForHash(ItemStack stack, String pwdHash) {
    //     NbtCompound nbt = new NbtCompound();
    //     nbt.putString(PASS_DATA_KEY, pwdHash);

    //     return hasMatchingNbt(stack, nbt);
    // }
}
