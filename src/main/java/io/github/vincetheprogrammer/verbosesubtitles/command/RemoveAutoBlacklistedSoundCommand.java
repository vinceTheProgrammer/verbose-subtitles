package io.github.vincetheprogrammer.verbosesubtitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import io.github.vincetheprogrammer.verbosesubtitles.providers.BlacklistedSoundsSuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class RemoveAutoBlacklistedSoundCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        RequiredArgumentBuilder<FabricClientCommandSource, String> blacklistedSoundArgument = ClientCommandManager.argument("sound", StringArgumentType.string()).suggests(new BlacklistedSoundsSuggestionProvider());
        dispatcher.register(ClientCommandManager.literal("verbosesubtitles")
                .then(ClientCommandManager.literal("blacklistedsounds")
                        .then(ClientCommandManager.literal("remove")
                                .then(blacklistedSoundArgument.executes(RemoveAutoBlacklistedSoundCommand::run)))));
    }

    private static int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        String blacklistedSoundString = commandContext.getArgument("sound", String.class);
        boolean success = VerboseSubtitlesConfig.INSTANCE.blacklistedSounds.remove(blacklistedSoundString);
        if (success) {
            VerboseSubtitlesConfig.saveConfig();
            commandContext.getSource().sendFeedback(Text.of("Sound ID \"" + blacklistedSoundString + "\" removed from sound blacklist."));
            return 1;
        } else {
            commandContext.getSource().sendFeedback(Text.of("Sound ID \"" + blacklistedSoundString + "\" is not present in sound blacklist."));
            return -1;
        }
    }

}