# Door Lock

_Door Lock is a simple Minecraft Fabric mod to lock doors, trapdoors, fence gates & chests._

![](https://i.imgur.com/p6QJhgx.png "Mod Banner")

## Version

Currently working on Minecraft 1.21! Because this is Fabric you will also need the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)!

You are free to include this in a modpack.

# How does it work?

This mod adds 3 keys to the game; a normal iron "Key", a "Golden Key", and a "Universal key"!

The "Key" & "Golden Key" are the same, but visually different, right click a [lockable block](#lockable-blocks) with the key to lock the block. To open, right click with the correct key in hand, or use the universal key.

You can also lock iron block types, this will allow you to open iron doors/trapdoors with the correct key in hand. By default the Universal key will not work on iron types.

When a block is locked, any redstone interaction is disabled by default, but that can be toggled in the [config](mod-config).

To remove a lock sneak (shift) right click on the block with the correct key in hand or the universal key. Breaking the block will also remove the key.

## Lockable blocks

Here's a list of the currently supported blocks a key can lock:

-   Doors
-   Trapdoors
-   Fence gates
-   Chests
-   Shulker boxes

## Setting a password

Each key needs a unique password, you can add a password by renaming in an anvil and adding #password (Item Name #my_secret_password), as shown in the image below. (NOTE: taking the item out normally will remove the #password in the name, but shift clicking to take it will keep the full name!)

# Screenshots

![screenshot](https://i.imgur.com/sfFeMWI.png)
![screenshot](https://i.imgur.com/N8uAdq9.png)

# Crafting

Here's how you craft the "Key":

![screenshot](https://i.imgur.com/nHBB17d.png)

And here's how the "Golden key" is crafted:

![screenshot](https://i.imgur.com/mMHyFSS.png)

The "Universal key" is only obtainable from commands, or the creative inventory.

# Mod Config

-   `disable_block_break_when_locked=*false*|true`: Stop block break event if block is locked.
-   `disable_redstone_when_locked=false|*true*`: Stop redstone inputs from interacting with a locked block.
-   `universal_unlocks_iron=*false*|true`: Use universal key to unlock iron types.
-   `show_password_on_key=false|*true*`: Show key password in item tooltip.

# Mod Commands

-   `/password {value}`: Set a password on a key in the main hand.
-   `/keys {amount}`: Give a set of keys in bulk with unique passwords.

# License

Door Lock is licensed under the CC0-1.0 license. Read [more here](https://github.com/vassbo/DoorLock/blob/main/LICENSE).
