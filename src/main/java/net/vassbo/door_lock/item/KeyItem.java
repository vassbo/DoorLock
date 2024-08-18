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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vassbo.door_lock.DoorLock;
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

        // WIP HOLD shift to show password!
    }

    @Override
	public ActionResult useOnBlock(ItemUsageContext context) {
        // WIP SHIFT RIGHT CLICK TO REMOVE!
        DoorLock.LOGGER.info("useOnBlock");
        PlayerEntity player = context.getPlayer();
        // just for sneak click to remove
        if (player.getServer() != null && player.isSneaking()) {
            DoorLock.LOGGER.info("HERE");
            World world = context.getWorld();
            Vec3d vecPos = context.getHitPos();
            BlockPos pos = new BlockPos((int)vecPos.x, (int)vecPos.y, (int)vecPos.z);
            
            // block lock data
            @Nullable LockData lockData = LockCheck.getLockData(world, pos);

            // get key in hands
            // this will not check the second key if holding two!
            @Nullable ItemStack keyStack = LockCheck.getKeyHandItem(player);

            boolean hasLock = lockData != null;
            if (hasLock) {
                DoorLock.LOGGER.info("HAS LOCK");
                if (keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM)) {
                    DoorLock.LOGGER.info("SHIFT REMVOE!: " + lockData.toString());
                    LockCheck.removeLockData(world, lockData);
                    player.sendMessage(Text.literal("Lock removed!!!"), true);
                    return ActionResult.PASS;
                }

                @Nullable String keyPass = KeyItem.getPassData(keyStack);
                boolean isCorrectKey = KeyPass.checkHashMatch(keyPass, lockData.keyHash);
                if (isCorrectKey) {
                    DoorLock.LOGGER.info("SHIFT REMVOE!: " + lockData.toString());
                    LockCheck.removeLockData(world, lockData);
                    player.sendMessage(Text.literal("Lock removed!!!"), true);
                }
            }
        }

		return ActionResult.PASS;
	}

    // @Override
	// public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    //     DoorLock.LOGGER.info("USE");
    //     return super.use(world, user, hand);
	// }

    // @Override
	// public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
    //     DoorLock.LOGGER.info("onClicked");
	// 	return false;
	// }

    // @Override
	// public UseAction getUseAction(ItemStack stack) {
    //     DoorLock.LOGGER.info("getUseAction");
	// 	return stack.contains(DataComponentTypes.FOOD) ? UseAction.EAT : UseAction.NONE;
	// }
    
    // @Override
	// public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
    //     DoorLock.LOGGER.info("usageTick");
	// }
    
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
