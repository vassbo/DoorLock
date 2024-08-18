package net.vassbo.door_lock.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.text.Text;
import net.vassbo.door_lock.util.LockCheck;
import net.vassbo.door_lock.util.LockData;

public class BlockBreakEvent {
    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            // WIP get bottom block pos if door!!!
            // DoorHelper.getBottomHalf(world, pos)

            // boolean isDoor =  state.getBlock().asItem().isTag(BlockTags.DOOR);
            LockData lockData = LockCheck.getLockData(world, pos);
            // WIP option to stop break event
            if (lockData != null) {
                // remove
                LockCheck.removeLockData(world, lockData);
                if (!world.isClient()) player.sendMessage(Text.literal("Lock removed!!!"), true);
            }

            return true;
        });
    }
}
