package io.github.vincetheprogrammer.verbosesubtitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import io.github.vincetheprogrammer.verbosesubtitles.providers.AvailableSoundsSuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AddAutoBlacklistedSoundCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        RequiredArgumentBuilder<FabricClientCommandSource, Identifier> soundArgument = ClientCommandManager.argument("sound", IdentifierArgumentType.identifier()).suggests(new AvailableSoundsSuggestionProvider());
        dispatcher.register(ClientCommandManager.literal("verbosesubtitles")
                .then(ClientCommandManager.literal("blacklistedsounds")
                        .then(ClientCommandManager.literal("add")
                                .then(ClientCommandManager.literal("autocomplete")
                                        .then(soundArgument.executes(AddAutoBlacklistedSoundCommand::run))))));
    }

    private static int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        String blacklistedSoundString = commandContext.getArgument("sound", Identifier.class).toString();
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