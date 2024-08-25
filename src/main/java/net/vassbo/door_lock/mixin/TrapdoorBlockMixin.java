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
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
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
import net.vassbo.door_lock.util.KeyPass;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(TrapdoorBlock.class)
public class TrapdoorBlockMixin {
	@Shadow @Final static BooleanProperty OPEN;
    @Shadow @Final private BlockSetType blockSetType;
	@Shadow @Final private static BooleanProperty WATERLOGGED;

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		@Nullable ItemStack keyStack = LockCheck.getKeyHandItem(player);

		if (world.isClient() && !this.blockSetType.canOpenByHand()) {
			if (keyStack == null) {
				cir.setReturnValue(ActionResult.PASS);
				return;
			}
			
			// skip if clicking e.g. iron door & item in hand is not a key
			if (!KeyPass.isKeyItem(keyStack, false)) {
				if (KeyPass.isKeyItem(keyStack)) {
					player.sendMessage(Text.translatable("key_use.wrong_type"), true);
				}

				cir.setReturnValue(ActionResult.PASS);
				return;
			}
		}

        if (!world.isClient() && !LockCheck.canOpen(world, pos, player)) {
			// cir.setReturnValue(ActionResult.success(false));
			cir.setReturnValue(ActionResult.PASS);
		} else {
			// open iron doors etc. (only iron/golden key!)
			if (keyStack != null && (KeyPass.isKeyItem(keyStack, false))) {
				this.flip(state, world, pos, null);
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}
	}

	private void flip(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity player) {
		BlockState blockState = state.cycle(OPEN);
		world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
		if ((Boolean)blockState.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		this.playToggleSound(player, world, pos, (Boolean)blockState.get(OPEN));
	}

	public void playToggleSound(@Nullable PlayerEntity player, World world, BlockPos pos, boolean open) {
		world.playSound(
			player,
			pos,
			open ? this.blockSetType.trapdoorOpen() : this.blockSetType.trapdoorClose(),
			SoundCategory.BLOCKS,
			1.0F,
			world.getRandom().nextFloat() * 0.1F + 0.9F
		);
		world.emitGameEvent(player, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
	}

    @Inject(at = @At("HEAD"), method = "neighborUpdate", cancellable = true)
	private void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo info) {
		if (ModConfig.DISABLE_REDSTONE_WHEN_LOCKED && !world.isClient() && !LockCheck.canOpen(world, pos)) {
			info.cancel();
		}
	}
}
