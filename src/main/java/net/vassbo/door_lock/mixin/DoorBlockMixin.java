package net.vassbo.door_lock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.util.DoorHelper;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient() && !LockCheck.canOpen(world, DoorHelper.getBottomHalf(world, pos), player)) {
			cir.setReturnValue(ActionResult.FAIL);
		}

		// WIP client stop if locked!!
		// if (world.isClient()) cir.setReturnValue(ActionResult.FAIL);
	}

    @Inject(at = @At("HEAD"), method = "neighborUpdate", cancellable = true)
	private void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo info) {
		if (!world.isClient() && !LockCheck.canOpen(world, DoorHelper.getBottomHalf(world, pos))) {
			info.cancel();
		}
	}
}
