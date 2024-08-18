package net.vassbo.door_lock.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DoorHelper {
    // always get bottom half of door blocks!
    public static BlockPos getBottomHalf(World world, BlockPos pos) {
		BlockPos bottomPos = pos;

		String blockId = world.getBlockState(pos).getBlock().toString();
        BlockPos blockBelowPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		String blockBelowId = world.getBlockState(blockBelowPos).getBlock().toString();
        
        if (blockBelowId.contains(blockId)) bottomPos = blockBelowPos;
        return bottomPos;
    }
}
