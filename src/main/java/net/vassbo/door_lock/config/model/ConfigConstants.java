package net.vassbo.door_lock.config.model;

public enum ConfigConstants {
    DISABLE_BLOCK_BREAK_WHEN_LOCKED(
        new ConfigEntry<>("disable_block_break_when_locked", false),
        "Stop block break event if block is locked."
    ),

    DISABLE_REDSTONE_WHEN_LOCKED(
        new ConfigEntry<>("disable_redstone_when_locked", true),
        "Stop redstone inputs from interacting with a locked block."
    ),

    UNIVERSAL_KEY_ON_IRON_TYPE(
        new ConfigEntry<>("universal_unlocks_iron", false),
        "Use universal key to unlock iron types."
    ),

    SHOW_PASSWORD_ON_KEY(
        new ConfigEntry<>("show_password_on_key", true),
        "Show key password in item tooltip."
    )

    ;

    final ConfigEntry<?> configEntry;
    final String comment;

    <T> ConfigConstants(
        ConfigEntry<T> configEntry,
        String comment
    ) {
        this.configEntry = configEntry;
        this.comment = comment;
    }

    public ConfigEntry<?> asConfigEntry(){
        return configEntry;
    }

    public String getComment() {
        return comment;
    }
}
