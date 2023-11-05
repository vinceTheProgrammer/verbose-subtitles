package io.github.vincetheprogrammer.verbosesubtitles.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VerboseSubtitlesModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> AutoConfig.getConfigScreen(VerboseSubtitlesConfig.class, parent).get();
    }
}
