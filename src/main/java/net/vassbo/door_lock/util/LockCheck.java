package net.vassbo.door_lock.util;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.data.ModData;
import net.vassbo.door_lock.data.StateSaverAndLoader;

public class LockCheck {
    public static boolean isBlockLocked(World world, BlockPos pos, boolean isDoor) {
        MinecraftServer server = world.getServer();
        
        ModData globalData = StateSaverAndLoader.getModData(server);
        List<String> lockedBlocks = globalData.LOCKED_BLOCKS;

        return getLockData(lockedBlocks, pos, isDoor) != null;
    }

    // FORMAT: x.y.z_keyHash
    private static LockData getLockData(List<String> lockedBlocks, BlockPos pos, boolean isDoor) {
        for (String dataString : lockedBlocks) {
            LockData data = convertStringToData(dataString);
            if (checkPosMatch(pos, data.pos, isDoor)) return data;
        }
        
        return null;
    }

    // isDoor: if player clicks door at the top, the block under will also be matched
    private static boolean checkPosMatch(BlockPos blockPos, BlockPos storedPos, boolean isDoor) {
        return blockPos.getX() == storedPos.getX() && blockPos.getY() == storedPos.getY() && (isDoor ? blockPos.getY() - 1 == storedPos.getY() : true) && blockPos.getZ() == storedPos.getZ();
    }

    private static LockData convertStringToData(String dataString) {
        LockData data = new LockData();
        String[] parts = dataString.split("_");

        String[] pos = parts[0].split(".");
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);
        int z = Integer.parseInt(pos[2]);
        BlockPos blockPos = new BlockPos(x, y, z);

        data.pos = blockPos;
        data.keyHash = parts[1];

        return data;
    }

    // public static void sendMessageToClient(PlayerEntity player, String message) {
    //     ModData playerState = StateSaverAndLoader.getPlayerState(player);
    //     playerState.MESSAGE = message;
    //     DataSender.sendPlayerData(player, playerState);

    //     String playerId = player.getUuid().toString();
    //     if (!TIMEOUT_IDs.containsKey(playerId)) TIMEOUT_IDs.put(playerId, 0);
    //     int currentId = TIMEOUT_IDs.get(playerId) + 1;
    //     TIMEOUT_IDs.put(playerId, currentId);

    //     // timeout message
    //     new Thread(() -> {
    //         wait(1200);

    //         // another message was sent before this could clear!
    //         if (TIMEOUT_IDs.get(playerId) != currentId) return;

    //         ModData playerStateNew = StateSaverAndLoader.getPlayerState(player);
    //         playerStateNew.MESSAGE = "";
    //         DataSender.sendPlayerData(player, playerStateNew);
    //     }).start();
    // }
}
