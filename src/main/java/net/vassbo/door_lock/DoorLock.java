package net.vassbo.door_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.vassbo.door_lock.command.ModCommands;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.event.BlockBreakEvent;
import net.vassbo.door_lock.item.ModItemGroups;
import net.vassbo.door_lock.item.ModItems;

public class DoorLock implements ModInitializer {
	public static final String MOD_ID = "door_lock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Please use this code as inspiration for your projects! :)
		// Trust in the LORD with all your heart, and do not lean on your own understanding.
		// In all your ways acknowledge Him, and He will make straight your paths.
		// Proverbs 3:5–6

		LOGGER.info("Initializing Door Lock!");

		ModConfig.init();

		// Packets.init();
		// DataReceiver.init();

        BlockBreakEvent.init();
		
		ModCommands.init();

		ModItemGroups.init();
		ModItems.init();

		LOGGER.info("Door Lock ready!");

		// TODO: add key manager block to manage many keys (chest with search)
		// TODO: prevent villagers from opening locked doors?
		// TODO: lock shulker boxes
	}
}