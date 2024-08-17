package net.vassbo.door_lock.packets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.vassbo.door_lock.DoorLock;
import net.vassbo.door_lock.data.PlayerDataClient;
import net.vassbo.door_lock.packets.clientbound.PlayerDataPayload;

public class DataReceiverClient {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(PlayerDataPayload.ID, (payload, context) -> {
			PlayerEntity player = context.player();
            PlayerDataPayload playerData = payload;

            if (player == null) {
                DoorLock.LOGGER.error("Something went wrong! No client player receiver.");
                return;
            }

			receivedData(player, playerData);
        });
    }

	private static void receivedData(PlayerEntity player, PlayerDataPayload playerData) {
		PlayerDataClient.MESSAGE = playerData.message();
	}

	// HELPERS

	public static List<String> stringToList(String stringList) {
		if (stringList.length() == 0) return new ArrayList<>();
		List<String> list = Arrays.asList(stringList.split(";;"));
		// list.remove(list.size() - 1); // last one is always empty (but automatically removed!)

		return list;
	}
}
