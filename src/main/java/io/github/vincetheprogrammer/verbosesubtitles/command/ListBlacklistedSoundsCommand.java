package io.github.vincetheprogrammer.verbosesubtitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class ListBlacklistedSoundsCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("verbosesubtitles")
                .then(ClientCommandManager.literal("blacklistedsounds")
                        .then(ClientCommandManager.literal("list").executes(ListBlacklistedSoundsCommand::run))));
    }

    private static int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        VerboseSubtitlesConfig.INSTANCE.getConfigHolder().load();
        VerboseSubtitlesConfig.INSTANCE.saveBlacklistedSounds();
        String blacklistedSoundsString = String.join("\n", VerboseSubtitlesConfig.INSTANCE.blacklistedSounds);
        commandContext.getSource().sendFeedback(Text.of(blacklistedSoundsString));
        return 1;
    }

}