package net.vassbo.door_lock.util;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vassbo.door_lock.data.ModData;
import net.vassbo.door_lock.data.StateSaverAndLoader;
import net.vassbo.door_lock.item.KeyItem;
import net.vassbo.door_lock.item.ModItems;

public class LockCheck {
    public static boolean canOpen(World world, BlockPos pos) {
        // block lock data
        @Nullable LockData lockData = getLockData(world, pos);
        
        return lockData == null;
    }

    public static boolean canOpen(World world, BlockPos pos, @Nullable PlayerEntity player) {
        // block lock data
        @Nullable LockData lockData = getLockData(world, pos);

        // get key in hands
        // this will not check the second key if holding two!
        @Nullable ItemStack keyStack = getKeyHandItem(player);

        boolean hasLock = lockData != null;
        if (hasLock) {
            if (keyStack == null) {
                player.sendMessage(Text.translatable("open.locked"), true);
                return false;
            }

            // universal key can open everything!
            if (keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM)) {
                return true;
            }

            @Nullable String keyPass = KeyItem.getPassData(keyStack);
            if (keyPass == null) {
                player.sendMessage(Text.translatable("key_use.no_password"), true);
                return false;
            }

            boolean isCorrectKey = KeyPass.checkHashMatch(keyPass, lockData.keyHash);
            // boolean isCorrectKey = KeyItem.checkForHash(keyStack, lockData.keyHash);

            if (!isCorrectKey) {
                player.sendMessage(Text.translatable("key_use.incorrect"), true);
                return false;
            }

            // open door with key
            return true;
        }
        
        // open door with no key
        if (keyStack == null || keyStack.isOf(ModItems.UNIVERSAL_KEY_ITEM)) return true;

        String keyPass = KeyItem.getPassData(keyStack);
        if (keyPass == null) {
            player.sendMessage(Text.translatable("key_set.no_password"), true);
            return false;
        }

        // add key to block data
        LockData blockLockData = new LockData();
        blockLockData.pos = pos;
        blockLockData.keyHash = KeyPass.getKeyHash(keyPass);
        addLockData(world, blockLockData);
        
        player.sendMessage(Text.translatable("key_set.added"), true);
        return false;
    }

    public static ItemStack getKeyHandItem(PlayerEntity player) {
        for (ItemStack handStack : player.getHandItems()) {
            if (KeyPass.isKeyItem(handStack)) return handStack;
        }

        return null;
    }

    // public static boolean isBlockLocked(World world, BlockPos pos, boolean isDoor) {
    //     MinecraftServer server = world.getServer();
        
    //     ModData globalData = StateSaverAndLoader.getModData(server);
    //     List<String> lockedBlocks = globalData.LOCKED_BLOCKS;

    //     return getLockData(lockedBlocks, pos, isDoor) != null;
    // }

    private static void addLockData(World world, LockData data) {
        MinecraftServer server = world.getServer();
        ModData globalData = StateSaverAndLoader.getModData(server);

        // LOCKED_BLOCKS
        List<String> lockedBlocks = globalData.LOCKED_BLOCKS;
        String dataString = convertDataToString(data);
        if (lockedBlocks.contains(dataString)) return;

        lockedBlocks.add(dataString);
        globalData.LOCKED_BLOCKS = lockedBlocks;

        // setLockData(world, globalData);
        StateSaverAndLoader.setModData(server, globalData);
    }

    public static void removeLockData(World world, LockData data) {
        MinecraftServer server = world.getServer();
        ModData globalData = StateSaverAndLoader.getModData(server);

        // LOCKED_BLOCKS
        List<String> lockedBlocks = globalData.LOCKED_BLOCKS;
        String dataString = convertDataToString(data);
        lockedBlocks.remove(dataString);
        globalData.LOCKED_BLOCKS = lockedBlocks;

        StateSaverAndLoader.setModData(server, globalData);
    }

    // private static void setLockData(World world, ModData data) {
    //     MinecraftServer server = world.getServer();
    //     ModData globalData = StateSaverAndLoader.getModData(server);

    //     globalData.LOCKED_BLOCKS = data.LOCKED_BLOCKS;

    //     StateSaverAndLoader.setModData(server, globalData);
    // }

    // FORMAT: x,y,z_keyHash
    public static @Nullable LockData getLockData(World world, BlockPos pos) {
        MinecraftServer server = world.getServer();
        ModData globalData = StateSaverAndLoader.getModData(server);
        List<String> lockedBlocks = globalData.LOCKED_BLOCKS;

        for (String dataString : lockedBlocks) {
            @Nullable LockData data = convertStringToData(dataString);
            if (data == null) return null;
            if (checkPosMatch(pos, data.pos)) return data;
        }
        
        return null;
    }

    // isDoor: if player clicks door at the top, the block under will also be matched
    private static boolean checkPosMatch(BlockPos blockPos, BlockPos storedPos) {
        // DoorLock.LOGGER.info("POS: " + blockPos.toShortString() + " - " + storedPos.toShortString());
        return blockPos.getX() == storedPos.getX() && (blockPos.getY() == storedPos.getY() ) && blockPos.getZ() == storedPos.getZ();
    }

    private static String convertDataToString(LockData data) {
        String dataString = "";

        dataString += data.pos.toShortString().replaceAll(" ", "");
        dataString += "_" + data.keyHash;

        return dataString;
    }

    private static @Nullable LockData convertStringToData(String dataString) {
        LockData data = new LockData();
        String[] parts = dataString.split("_");
        if (parts.length == 0) return null;

        String[] pos = parts[0].split(",");
        if (pos.length == 0) return null;

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
