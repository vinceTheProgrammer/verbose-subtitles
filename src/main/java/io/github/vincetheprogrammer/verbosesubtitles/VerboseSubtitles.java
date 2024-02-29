package io.github.vincetheprogrammer.verbosesubtitles;

import io.github.vincetheprogrammer.verbosesubtitles.command.AddAutoBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.AddCustomBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.ListBlacklistedSoundsCommand;
import io.github.vincetheprogrammer.verbosesubtitles.command.RemoveAutoBlacklistedSoundCommand;
import io.github.vincetheprogrammer.verbosesubtitles.event.KeyInputHandler;
import io.github.vincetheprogrammer.verbosesubtitles.listener.ReloadListener;
import io.github.vincetheprogrammer.verbosesubtitles.runnables.CloseFileWriterRunnable;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VerboseSubtitles implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("verbose-subtitles");
    private static FileWriter fileWriter;
    private static ExecutorService subtitleLoggingService;

    @Override
    public void onInitializeClient() {
        registerReloadListener();
        KeyInputHandler.register();
        registerCommands();
        registerShutdownHook();
        LOGGER.info("Initialized");
    }

    private void registerReloadListener() {
        ReloadListener reloadListener = new ReloadListener();
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(reloadListener);
    }

    private void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(AddAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(AddCustomBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(RemoveAutoBlacklistedSoundCommand::register);
        ClientCommandRegistrationCallback.EVENT.register(ListBlacklistedSoundsCommand::register);
    }

    private static void initializeFileWriter() {
        if (fileWriter == null) {
            initializeSubtitleLoggingService();
            try {
                fileWriter = new FileWriter("verbose-subtitles-log.txt", true);
            } catch (IOException e) {
                LOGGER.error("Error initializing FileWriter", e);
            }
        }
    }

    public static FileWriter getFileWriter() {
        initializeFileWriter();
        return fileWriter;
    }

    private static void initializeSubtitleLoggingService() {
        if (subtitleLoggingService == null || subtitleLoggingService.isShutdown()) {
            subtitleLoggingService = Executors.newSingleThreadExecutor();
        }
    }

    public static ExecutorService getSubtitleLoggingService() {
        initializeSubtitleLoggingService();
        return subtitleLoggingService;
    }

    public static void closeFileWriter() {
        if (fileWriter != null) {
            if (subtitleLoggingService.isShutdown()) initializeSubtitleLoggingService();
            CloseFileWriterRunnable closeFileWriterRunnable = new CloseFileWriterRunnable(fileWriter);
            subtitleLoggingService.execute(closeFileWriterRunnable);
            fileWriter = null;
            subtitleLoggingService.shutdown();
        }
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new CloseFileWriterRunnable(fileWriter)));
    }
}