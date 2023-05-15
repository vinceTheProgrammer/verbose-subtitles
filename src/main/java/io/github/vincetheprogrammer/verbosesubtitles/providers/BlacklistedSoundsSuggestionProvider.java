package io.github.vincetheprogrammer.verbosesubtitles.providers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.concurrent.CompletableFuture;

public class BlacklistedSoundsSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (String blacklistedSound : VerboseSubtitlesConfig.INSTANCE.blacklistedSounds) {
            builder.suggest(blacklistedSound);
        }
        return builder.buildFuture();
    }
}
