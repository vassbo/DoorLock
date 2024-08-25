package net.vassbo.door_lock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.util.LockCheck;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
	private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (!world.isClient() && !LockCheck.canOpen(world, pos, player)) {
			cir.setReturnValue(ActionResult.CONSUME);
		}
	}
}
