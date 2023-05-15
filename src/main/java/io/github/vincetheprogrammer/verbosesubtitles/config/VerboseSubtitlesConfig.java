package io.github.vincetheprogrammer.verbosesubtitles.config;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Config(name = "verbose-subtitles")
public class VerboseSubtitlesConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static VerboseSubtitlesConfig INSTANCE;
    @ConfigEntry.Gui.Excluded
    public HashSet<String> blacklistedSounds = new HashSet<String>();
//    @ConfigEntry.Category("general-category")
    @ConfigEntry.Gui.PrefixText()
    public boolean enabled = true;
    public boolean showID = true;
    public boolean showVolume = true;
    public boolean showPitch = true;
//    @ConfigEntry.Category("blacklist-category")
    @ConfigEntry.Gui.PrefixText()

    private List<String> blacklistedSoundsArrayList = new ArrayList<String>();
    public void saveBlacklistedSounds() {
        VerboseSubtitles.LOGGER.info(blacklistedSoundsArrayList.toString());
        blacklistedSounds = new HashSet<>(blacklistedSoundsArrayList);
        VerboseSubtitles.LOGGER.info("saved: " + blacklistedSounds);
    }

    public void loadBlacklistedSounds() {
        blacklistedSoundsArrayList = new ArrayList<String>(blacklistedSounds);
        VerboseSubtitles.LOGGER.info("loaded");
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        blacklistedSoundsArrayList = new ArrayList<String>(blacklistedSounds);
        VerboseSubtitles.LOGGER.info("post load: " + blacklistedSounds);
    }

    public ConfigHolder<VerboseSubtitlesConfig> getConfigHolder() {
         return AutoConfig.getConfigHolder(VerboseSubtitlesConfig.class);
    }

    public static void init() { }

    static {
        AutoConfig.register(VerboseSubtitlesConfig.class, GsonConfigSerializer::new);
        ConfigHolder<VerboseSubtitlesConfig> holder = AutoConfig.getConfigHolder(VerboseSubtitlesConfig.class);
        INSTANCE = holder.getConfig();
        holder.registerSaveListener((configHolder, configData) -> {
            VerboseSubtitlesConfig.INSTANCE.saveBlacklistedSounds();
            return ActionResult.PASS;
        });
        holder.registerLoadListener(((configHolder, verboseSubtitlesConfig) -> {
            VerboseSubtitlesConfig.INSTANCE.loadBlacklistedSounds();;
            return  ActionResult.PASS;
        }));


    }
}
