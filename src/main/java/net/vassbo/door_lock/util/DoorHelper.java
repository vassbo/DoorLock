package net.vassbo.door_lock.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DoorHelper {
    // always get bottom half of door blocks!
    public static BlockPos getBottomHalf(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockPos bottomPos = pos;
        if (state.getBlock().toString().contains("_door") && state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            bottomPos = pos.down();
        }
        
        return bottomPos;
    }
}
