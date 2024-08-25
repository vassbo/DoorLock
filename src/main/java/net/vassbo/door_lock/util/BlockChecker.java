package net.vassbo.door_lock.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChecker {
    public static @Nullable ActionResult checkBlock(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		// // DON'T ADD NORMAL KEY TO IRON ETC. DOOR
		// @Nullable ItemStack keyStack = LockCheck.getKeyHandItem(player);
		// // if (keyStack == null && world.isClient()) return;

		// if (world.isClient() && !this.blockSetType.canOpenByHand()) {
		// 	if (keyStack == null) {
		// 		return ActionResult.PASS;
		// 	}
			
		// 	// skip if clicking e.g. iron door & item in hand is not a key (universal should not work for iron types)
		// 	if (!KeyPass.isKeyItem(keyStack, false)) {
		// 		if (KeyPass.isKeyItem(keyStack)) {
		// 			player.sendMessage(Text.translatable("key_use.wrong_type"), true);
		// 		}

		// 		return ActionResult.PASS;
		// 	}
		// }

        // if (!world.isClient() && !LockCheck.canOpen(world, DoorHelper.getBottomHalf(world, pos), player)) {
		// 	// return ActionResult.PASS;
		// 	return ActionResult.success(false);
		// } else {
		// 	// open iron doors etc. (only iron/golden key!)
		// 	// keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM) || 
		// 	if (keyStack != null && (KeyPass.isKeyItem(keyStack, false))) {
		// 		state = state.cycle(OPEN);
		// 		world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
		// 		this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
		// 		world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
		// 		return ActionResult.success(world.isClient);
		// 	}
		// }

        return null;
    }
}
