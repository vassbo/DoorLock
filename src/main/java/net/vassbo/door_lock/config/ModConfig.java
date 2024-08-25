package net.vassbo.door_lock.config;

import net.vassbo.door_lock.DoorLock;
import net.vassbo.door_lock.config.model.ConfigConstants;

public class ModConfig {
    public static SimpleConfig CONFIG;
    protected static ModConfigProvider configs;

    public static boolean DISABLE_REDSTONE_WHEN_LOCKED;
    public static boolean DISABLE_BLOCK_BREAK_WHEN_LOCKED;
    public static boolean UNIVERSAL_KEY_ON_IRON_TYPE;
    public static boolean SHOW_PASSWORD_ON_KEY;

    public static void init() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(DoorLock.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        for (ConfigConstants value : ConfigConstants.values()) {
            configs.addKeyValuePair(value.asConfigEntry(), value.getComment());
        }
    }

    private static void assignConfigs() {
        DISABLE_BLOCK_BREAK_WHEN_LOCKED = CONFIG.getOrDefault("disable_block_break_when_locked", false);
        DISABLE_REDSTONE_WHEN_LOCKED = CONFIG.getOrDefault("disable_redstone_when_locked", true);
        UNIVERSAL_KEY_ON_IRON_TYPE = CONFIG.getOrDefault("universal_unlocks_iron", false);
        SHOW_PASSWORD_ON_KEY = CONFIG.getOrDefault("show_password_on_key", true);
    }
}