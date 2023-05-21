package io.github.vincetheprogrammer.verbosesubtitles;

import io.github.vincetheprogrammer.verbosesubtitles.command.AddAutoBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.AddCustomBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.ListBlacklistedSoundsCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.RemoveAutoBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.event.KeyInputHandler;
import io.github.vincetheprogrammer.verbosesubtitles.listener.ReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerboseSubtitles implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("verbose-subtitles");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
        ReloadListener reloadListener = new ReloadListener();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(reloadListener);
        KeyInputHandler.register();
        registerCommands();
    }

    private void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(AddAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(AddCustomBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(RemoveAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(ListBlacklistedSoundsCommand::register);
    }

}