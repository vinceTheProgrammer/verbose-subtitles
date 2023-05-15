package io.github.vincetheprogrammer.verbosesubtitles;

import io.github.vincetheprogrammer.verbosesubtitles.command.*;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import io.github.vincetheprogrammer.verbosesubtitles.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerboseSubtitles implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("verbose-subtitles");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
        KeyInputHandler.register();
        VerboseSubtitlesConfig.init();
        registerCommands();
    }

    private void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(AddAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(AddCustomBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(RemoveAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(ListBlacklistedSoundsCommand::register);
    }

}