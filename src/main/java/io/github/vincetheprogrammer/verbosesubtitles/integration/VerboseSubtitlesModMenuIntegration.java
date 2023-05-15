package io.github.vincetheprogrammer.verbosesubtitles.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class VerboseSubtitlesModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            VerboseSubtitlesConfig.INSTANCE.loadBlacklistedSounds();
            return AutoConfig.getConfigScreen(VerboseSubtitlesConfig.class, parent).get();
        };
    }
}
