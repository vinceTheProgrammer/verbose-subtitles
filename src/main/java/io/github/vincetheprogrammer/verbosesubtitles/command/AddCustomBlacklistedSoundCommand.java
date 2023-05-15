package io.github.vincetheprogrammer.verbosesubtitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class AddCustomBlacklistedSoundCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("verbosesubtitles")
                .then(ClientCommandManager.literal("blacklistedsounds")
                        .then(ClientCommandManager.literal("add").then(ClientCommandManager.literal("custom")
                                        .then(ClientCommandManager.argument("sound", string()).executes(AddCustomBlacklistedSoundCommand::run))))));
    }

    private static int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        VerboseSubtitles.LOGGER.info("Addo");
        String blacklistedSoundString = commandContext.getArgument("sound", String.class);
        boolean success = VerboseSubtitlesConfig.INSTANCE.blacklistedSounds.add(blacklistedSoundString);
        if (success) {
            VerboseSubtitlesConfig.INSTANCE.loadBlacklistedSounds();
            VerboseSubtitlesConfig.INSTANCE.getConfigHolder().save();
            commandContext.getSource().sendFeedback(Text.of("Sound ID \"" + blacklistedSoundString + "\" added to sound blacklist."));
            return 1;
        } else {
            commandContext.getSource().sendFeedback(Text.of("Sound ID \"" + blacklistedSoundString + "\" is already in sound blacklist."));
            return -1;
        }
    }

}