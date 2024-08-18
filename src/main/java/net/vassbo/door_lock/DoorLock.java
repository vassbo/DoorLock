package net.vassbo.door_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.vassbo.door_lock.command.ModCommands;
import net.vassbo.door_lock.config.ModConfig;
import net.vassbo.door_lock.event.BlockBreakEvent;
import net.vassbo.door_lock.event.JoinEvent;
import net.vassbo.door_lock.item.ModItemGroups;
import net.vassbo.door_lock.item.ModItems;
import net.vassbo.door_lock.packets.DataReceiver;
import net.vassbo.door_lock.packets.Packets;

public class DoorLock implements ModInitializer {
	public static final String MOD_ID = "door_lock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Please use this code as inspiration for your projects! :)
		// For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life.
		// John 3:16

		LOGGER.info("Initializing Door Lock!");

		ModConfig.init();

		Packets.init();
		DataReceiver.init();

        JoinEvent.init();
        BlockBreakEvent.init();
		
		ModCommands.init();

		ModItemGroups.init();
		ModItems.init();

		LOGGER.info("Door Lock ready!");
	}
}