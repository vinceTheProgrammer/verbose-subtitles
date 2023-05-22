package io.github.vincetheprogrammer.verbosesubtitles.listener;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class ReloadListener implements SimpleSynchronousResourceReloadListener {

    boolean firstRun = true;

    @Override
    public Identifier getFabricId() {
        return new Identifier("verbose-subtitles", "reload_listener");
    }

    @Override
    public void reload(ResourceManager manager) {
        if (firstRun) {
            VerboseSubtitlesConfig.init();
            firstRun = false;
        }
    }

    @Override
    public String getName() {
        return SimpleSynchronousResourceReloadListener.super.getName();
    }
}
