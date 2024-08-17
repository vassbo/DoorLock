package net.vassbo.door_lock.event;

public class JoinEvent {
	// private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDataPayload.class);

    public static void init() {
        // ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
		// 	ServerPlayerEntity playerEntity = handler.getPlayer();

        //     if (ServerPlayNetworking.canSend(playerEntity, PlayerDataPayload.ID)) {
        //         ModData playerState = StateSaverAndLoader.getPlayerState(playerEntity);
        //         DataSender.sendPlayerData(playerEntity, playerState);
        //     } else {
        //         LOGGER.error("Client cannot receive packet. This probably means that door_lock is not installed on the client.");
        //         handler.disconnect(Text.literal("Please install the door_lock mod to play on this server."));
        //     }
        // });
    }
}
