package net.vassbo.door_lock.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.vassbo.door_lock.DoorLock;

public class ModCommands {
    private static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(command));
    }

    public static void feedback(CommandContext<ServerCommandSource> context, String msg) {
        context.getSource().sendFeedback(() -> Text.literal(msg), false);
    }

    // EXECUTE

    private interface CommandMethodInterface {
        int execute(CommandContext<ServerCommandSource> context, String command);
    }

    private static int executeCommand(CommandContext<ServerCommandSource> context, String command, CommandMethodInterface func) {
        DoorLock.LOGGER.info("Executed command: " + command);
        return func.execute(context, command);
    }

    // INITIALIZE

    public static void init() {
        // WIP commands to lock/unlock block?

        registerCommand(
            literal("password").requires(source -> source.hasPermissionLevel(2))
            .then(argument("value", StringArgumentType.string())
            .executes((context) -> executeCommand(context, "password", UpdateKey::setPassword)))
        );

        registerCommand(
            literal("keys").requires(source -> source.hasPermissionLevel(2))
            .then(argument("amount", IntegerArgumentType.integer())
            .executes((context) -> executeCommand(context, "keys", GiveKeys::giveKeys)))
            // WIP optional start index ?
            // .then(argument("startIndex", IntegerArgumentType.integer())
            // .executes((context) -> executeCommand(context, "keys", GiveKeys::giveKeys)))
        );
    }
}
