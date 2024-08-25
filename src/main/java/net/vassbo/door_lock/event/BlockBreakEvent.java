package net.vassbo.door_lock.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.util.DoorHelper;
import net.vassbo.door_lock.util.LockCheck;
import net.vassbo.door_lock.util.LockData;

public class BlockBreakEvent {
    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            BlockPos fixedPos = DoorHelper.getBottomHalf(world, pos);

            // boolean isDoor =  state.getBlock().asItem().isTag(BlockTags.DOOR);
            LockData lockData = LockCheck.getLockData(world, fixedPos);
            if (lockData != null) {
                if (ModConfig.DISABLE_BLOCK_BREAK_WHEN_LOCKED) {
                    return false;
                }

                // remove
                LockCheck.removeLockData(world, lockData);
                if (!world.isClient()) player.sendMessage(Text.translatable("break.remove_lock"), true);
            }

            return true;
        });
    }
}
