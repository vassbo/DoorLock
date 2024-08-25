package net.vassbo.door_lock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(FenceGateBlock.class)
public class FenceGateBlockMixin {
    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (!world.isClient() && !LockCheck.canOpen(world, pos, player)) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}

    @Inject(at = @At("HEAD"), method = "neighborUpdate", cancellable = true)
	private void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo info) {
		if (ModConfig.DISABLE_REDSTONE_WHEN_LOCKED && !world.isClient() && !LockCheck.canOpen(world, pos)) {
			info.cancel();
		}
	}
}
