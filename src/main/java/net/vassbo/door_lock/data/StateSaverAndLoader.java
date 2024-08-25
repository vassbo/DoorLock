package net.vassbo.door_lock.data;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.vassbo.door_lock.DoorLock;

// https://fabricmc.net/wiki/tutorial:persistent_states#player_specific_persistent_state
public class StateSaverAndLoader extends PersistentState {
    public ModData storedData = new ModData();

    private static NbtCompound storeModData(NbtCompound nbt, ModData data) {
        nbt = DataHelpers.storeList(nbt, "LOCKED_BLOCKS", data.LOCKED_BLOCKS);

        return nbt;
    }

    private static ModData getModData(NbtCompound nbt, ModData data) {
        data.LOCKED_BLOCKS = DataHelpers.getList(nbt, "LOCKED_BLOCKS");

        return data;
    }

    // STORE DATA

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound globalNbt = new NbtCompound();

        // door_lock_data
        globalNbt = storeModData(globalNbt, storedData);
        nbt.put("door_lock_data", globalNbt);

        return nbt;
    }

    // GET DATA

    public static StateSaverAndLoader createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();

        // door_lock_data
        NbtCompound globalNbt = nbt.getCompound("door_lock_data");
        ModData data = new ModData();
        data = getModData(globalNbt, data);
        state.storedData = data;

        return state;
    }

    // GLOBAL

    public static ModData getModData(MinecraftServer server) {
        StateSaverAndLoader serverState = getServerState(server);
        return serverState.storedData;
    }

    public static void setModData(MinecraftServer server, ModData data) {
        StateSaverAndLoader serverState = getServerState(server);
        serverState.storedData = data;
    }

    // STATE MANAGER

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new, // If there's no 'StateSaverAndLoader' yet create one
            StateSaverAndLoader::createFromNbt, // If there is a 'StateSaverAndLoader' NBT, parse it with 'createFromNbt'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        // (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // The first time the following 'getOrCreate' function is called, it creates a brand new 'StateSaverAndLoader' and
        // stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
        // 'StateSaverAndLoader' NBT on disk to our function 'StateSaverAndLoader::createFromNbt'.
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, DoorLock.MOD_ID);

        // If state is not marked dirty, when Minecraft closes, 'writeNbt' won't be called and therefore nothing will be saved.
        state.markDirty();

        return state;
    }

    private static StateSaverAndLoader getSaver(LivingEntity player) {
        MinecraftServer server = player.getServer();
        return getServerState(server);
    }

    private static ModData getModDataState(StateSaverAndLoader serverState) {
        return serverState.storedData;
    }

    public static void setPlayerLearned(LivingEntity player, List<String> learnedList) {
        if (player.getServer() == null) return;

        StateSaverAndLoader serverState = getSaver(player);
        ModData playerState = getModDataState(serverState);

        playerState.LOCKED_BLOCKS = learnedList;
        updateState(player, serverState, playerState);
    }

    private static void updateState(LivingEntity player, StateSaverAndLoader serverState, ModData playerState) {
        serverState.storedData = playerState;
        // updateAllServerPlayers(player.getServer());
    }
}
