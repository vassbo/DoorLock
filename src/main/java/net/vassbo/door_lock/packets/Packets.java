package net.vassbo.door_lock.packets;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.vassbo.door_lock.DoorLock;
import net.vassbo.door_lock.packets.clientbound.PlayerDataPayload;
import net.vassbo.door_lock.packets.serverbound.ClientPayload;

public class Packets {
	public static void init() {
        DoorLock.LOGGER.info("Registering packet payloads.");
		clientbound(PayloadTypeRegistry.playS2C());
		serverbound(PayloadTypeRegistry.playC2S());
	}

	private static void clientbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
		registry.register(PlayerDataPayload.ID, PlayerDataPayload.CODEC);
	}

	private static void serverbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
		registry.register(ClientPayload.ID, ClientPayload.CODEC);
	}
}
