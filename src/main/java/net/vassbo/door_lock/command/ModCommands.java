package net.vassbo.door_lock.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ModCommands {
    // private static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
    //     CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(command));
    // }

    // private static void createCustomCommand(String command, CommandMethodInterface func) {
    //     registerCommand(
    //         createRootCommand(command)
    //         .executes(context -> {
    //             door_lock.LOGGER.info("Executed command: " + command);
    //             return func.execute(context, command);
    //         })
    //     );
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createRootCommand(String command) {
    //     return literal(command).requires(source -> source.hasPermissionLevel(2)); // requires OP
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createCommandWithPlayerArg(String command, ArgumentType<?> argType, CommandMethodInterface func, CommandMethodPlayerInterface playerFunc) {
    //     return literal(command).executes((context) -> executeCommand(context, command, func)).then(argument("player", StringArgumentType.string()).executes((context) -> executePlayerCommand(context, command, playerFunc)));
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createSubCommand(String command, String argId, ArgumentType<?> argType, CommandMethodInterface func) {
    //     return literal(command).then(argument(argId, argType).executes((context) -> executeCommand(context, command, func)));
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createPlayerSubCommand(String command, CommandMethodInterface func) {
    //     return literal(command).then(argument("player", StringArgumentType.string()).suggests(new PlayerSuggestionProvider()).executes((context) -> executeCommand(context, command, func)));
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createSubCommandWithPlayerArg(String command, String argId, ArgumentType<?> argType, CommandMethodInterface func, CommandMethodPlayerInterface playerFunc) {
    //     return literal(command).then(argument(argId, argType).executes((context) -> executeCommand(context, command, func)).then(argument("player", StringArgumentType.string()).suggests(new PlayerSuggestionProvider()).executes((context) -> executePlayerCommand(context, command, playerFunc))));
    // }

    // private static LiteralArgumentBuilder<ServerCommandSource> createItemSubCommandWithPlayerArg(String command, CommandRegistryAccess registryAccess, CommandMethodInterface func, CommandMethodPlayerInterface playerFunc) {
    //     return literal(command).then(argument("item", ItemStackArgumentType.itemStack(registryAccess)).suggests(new ItemSuggestionProvider()).executes((context) -> executeCommand(context, command, func)).then(argument("player", StringArgumentType.string()).suggests(new PlayerSuggestionProvider()).executes((context) -> executePlayerCommand(context, command, playerFunc))));
    // }
    
    // special example: msg = "\"%s × %s = %s\".formatted(value, value, result)"
    public static void feedback(CommandContext<ServerCommandSource> context, String msg) {
        context.getSource().sendFeedback(() -> Text.literal(msg), false);
    }
    
    // HELPERS
    
    public static PlayerEntity playerFromName(CommandContext<ServerCommandSource> context, String playerName) {
        return context.getSource().getServer().getPlayerManager().getPlayer(playerName);
    }

    // EXECUTE

    // private interface CommandMethodInterface {
    //     int execute(CommandContext<ServerCommandSource> context, String command);
    // }

    // private static int executeCommand(CommandContext<ServerCommandSource> context, String command, CommandMethodInterface func) {
    //     DoorLock.LOGGER.info("Executed command: " + command);
    //     return func.execute(context, command);
    // }

    // private interface CommandMethodPlayerInterface {
    //     int execute(CommandContext<ServerCommandSource> context, String command, PlayerEntity player);
    // }

    // private static int executePlayerCommand(CommandContext<ServerCommandSource> context, String command, CommandMethodPlayerInterface func) {
    //     final String playerName = StringArgumentType.getString(context, "player");
    //     PlayerEntity player = playerFromName(context, playerName);

    //     // Player currently has to be logged on to the server,
    //     // but it is possible to find & update the player state based on the stored name

    //     if (player == null) {
    //         feedback(context, Text.translatable("command.feedback.player.not_found", playerName).getString());
    //         return -1;
    //     }

    //     DoorLock.LOGGER.info("Executed command to player '" + playerName + "': " + command);
    //     return func.execute(context, command, player);
    // }

    // private static int executeSubCommand(CommandContext<ServerCommandSource> context, String command, CommandMethodInterface func) {
    //     door_lock.LOGGER.info("Executed subcommand: " + command);
    //     return func.execute(context, command);
    // }

    // INITIALIZE

    public static void init() {
        // WIP commands to set key password / lock/unlock

        // createCustomCommand("emctest", ModCommands::changeEMC);

        // registerCommand(
        //     createRootCommand("emc")
        //     .executes((context) -> executeCommand(context, "emc", ChangeEMC::listUserEMC))
        //     .then(literal("list").executes((context) -> executeCommand(context, "list", ChangeEMC::listEMC)))
        //     .then(createPlayerSubCommand("get", ChangeEMC::getEMC))
        //     .then(createSubCommandWithPlayerArg("give", "number", IntegerArgumentType.integer(), ChangeEMC::changeEMC, ChangeEMC::changeEMCPlayer))
        //     .then(createSubCommandWithPlayerArg("take", "number", IntegerArgumentType.integer(), ChangeEMC::changeEMC, ChangeEMC::changeEMCPlayer))
        //     .then(createSubCommandWithPlayerArg("set", "number", IntegerArgumentType.integer(), ChangeEMC::changeEMC, ChangeEMC::changeEMCPlayer))
        // );

        // LEARNED (unlock all, learn specific items, unlearn)
        // CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
        //     createRootCommand("emcmemory")
        //     .then(createCommandWithPlayerArg("fill", ItemStackArgumentType.itemStack(registryAccess), LearnItems::everything, LearnItems::everythingPlayer))
        //     .then(createCommandWithPlayerArg("clear", ItemStackArgumentType.itemStack(registryAccess), LearnItems::forget, LearnItems::forgetPlayer))
        //     .then(createItemSubCommandWithPlayerArg("add", registryAccess, LearnItems::add, LearnItems::addPlayer))
        //     .then(createItemSubCommandWithPlayerArg("remove", registryAccess, LearnItems::remove, LearnItems::removePlayer))
        // ));
    }
}
