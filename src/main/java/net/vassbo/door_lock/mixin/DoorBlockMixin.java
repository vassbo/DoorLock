package net.vassbo.door_lock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
	// @Shadow @Final public static BooleanProperty OPEN = Properties.OPEN;
    // @Shadow @Final private BlockSetType blockSetType;

    // @Accessor
    // BlockSetType getBlockSetType();

    @Inject(at = @At("HEAD"), method = "onUse")
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<?> info) {
        if (!world.isClient()) {
            // player.sendMessage(Text.literal("USE DOOR!!!: " + pos.toString()));
            // WIP check if locked
            // WIP apply a key / remove

            // check locked
            if (LockCheck.isBlockLocked(world, pos, true)) {
                // check the correct key!!
                return;
            } else {
                // add the key!!!!
                // get left & right hand items
                // ItemStack handItem = // player.getHandItems()
                // player.sendMessage(Text.literal("HAND: " + player.getHandItems().toString()));
            }
        }

		// if (!this.blockSetType.canOpenByHand()) {
		// 	return ActionResult.PASS;
		// } else {
		// 	state = state.cycle(OPEN);
		// 	world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
		// 	this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
		// 	world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
		// 	return ActionResult.success(world.isClient);
		// }
	}

	// private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
	// 	world.playSound(
	// 		entity, pos, open ? this.blockSetType.doorOpen() : this.blockSetType.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F
	// 	);
	// }

	// public boolean isOpen(BlockState state) {
	// 	return (Boolean)state.get(OPEN);
	// }
}
