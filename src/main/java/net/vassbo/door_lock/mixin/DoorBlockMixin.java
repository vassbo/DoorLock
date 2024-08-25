package net.vassbo.door_lock.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.util.DoorHelper;
import net.vassbo.door_lock.util.KeyPass;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
	@Shadow @Final static BooleanProperty OPEN;
    @Shadow @Final private BlockSetType blockSetType;

	// WIP custom textures when locked?!

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		// WIP client stop if locked!!
		// if (world.isClient()) cir.setReturnValue(ActionResult.PASS);

		@Nullable ItemStack keyStack = LockCheck.getKeyHandItem(player);
		// if (keyStack == null && world.isClient()) return;

		if (world.isClient() && !this.blockSetType.canOpenByHand()) {
			if (keyStack == null) {
				cir.setReturnValue(ActionResult.PASS);
				return;
			}
			
			// skip if clicking e.g. iron door & item in hand is not a key (universal should not work for iron types by default)
			if (!KeyPass.isKeyItem(keyStack, false)) {
				if (KeyPass.isKeyItem(keyStack)) {
					player.sendMessage(Text.translatable("key_use.wrong_type"), true);
				}

				cir.setReturnValue(ActionResult.PASS);
				return;
			}
		}

        if (!world.isClient() && !LockCheck.canOpen(world, DoorHelper.getBottomHalf(world, pos), player)) {
			// cir.setReturnValue(ActionResult.success(false));
			cir.setReturnValue(ActionResult.PASS);
		} else {
			// open iron doors etc. (only iron/golden key!)
			// keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM) || 
			if (keyStack != null && (KeyPass.isKeyItem(keyStack, false))) {
				state = state.cycle(OPEN);
				world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
				this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
				world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}

		// @Nullable ActionResult returnValue = BlockChecker.checkBlock(state, world, pos, player);
		// if (returnValue != null) cir.setReturnValue(returnValue);
	}
	
	public boolean isOpen(BlockState state) {
		return (Boolean)state.get(OPEN);
	}

	private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
		world.playSound(
			entity, pos, open ? this.blockSetType.doorOpen() : this.blockSetType.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F
		);
	}

    @Inject(at = @At("HEAD"), method = "neighborUpdate", cancellable = true)
	private void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo info) {
		if (ModConfig.DISABLE_REDSTONE_WHEN_LOCKED && !world.isClient() && !LockCheck.canOpen(world, DoorHelper.getBottomHalf(world, pos))) {
			info.cancel();
		}
	}
}
