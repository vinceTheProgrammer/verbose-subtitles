package io.github.vincetheprogrammer.verbosesubtitles.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Config(name = "verbose-subtitles")
public class VerboseSubtitlesConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static final VerboseSubtitlesConfig INSTANCE;
    @ConfigEntry.Gui.Excluded
    private static ConfigHolder<VerboseSubtitlesConfig> CONFIG_HOLDER;
//    @ConfigEntry.Category("general-category")
    public boolean enabled = true;
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsId optionsId = new OptionsId();
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsDisplayname optionsDisplayname = new OptionsDisplayname();
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsVolume optionsVolume = new OptionsVolume();
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsPitch optionsPitch = new OptionsPitch();
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsVariant optionsVariant = new OptionsVariant();
    @ConfigEntry.Gui.CollapsibleObject
    public final OptionsPosition optionsPosition = new OptionsPosition();
    @ConfigEntry.Gui.PrefixText()
    public final boolean logToFile = false;
    public List<String> blacklistedSounds = new ArrayList<>();


    public static class OptionsId {
        public final boolean showId = true;
        public final boolean showLabel = true;
        public final String label = "ID: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStyleId labelStyleId = new LabelStyleId();
    }

    public static class OptionsDisplayname {
        public final boolean showDisplayname = true;
        public final boolean showLabel = true;
        public final String label = "Displayname: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStyleDisplayname labelStyleDisplayname = new LabelStyleDisplayname();
    }

    public static class OptionsVolume {
        public final boolean showVolume = true;
        public final boolean showLabel = true;
        public final String label = "Volume: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStyleVolume labelStyleVolume = new LabelStyleVolume();
    }

    public static class OptionsPitch {
        public final boolean showPitch = true;
        public final boolean showLabel = true;
        public final String label = "Pitch: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStylePitch labelStylePitch = new LabelStylePitch();
    }

    public static class OptionsVariant {
        public final boolean showVariant = true;
        public final boolean showLabel = true;
        public final String label = "Variant: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStyleVariant labelStyleVariant = new LabelStyleVariant();
    }

    public static class OptionsPosition {
        public final boolean showPosition = true;
        public final boolean showLabel = true;
        public final String label = "Position: ";
        @ConfigEntry.Gui.CollapsibleObject
        public final LabelStylePosition labelStylePosition = new LabelStylePosition();
    }

    public static class LabelStyleId {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    public static class LabelStyleDisplayname {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    public static class LabelStyleVolume {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    public static class LabelStylePitch {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    public static class LabelStyleVariant {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    public static class LabelStylePosition {
        public final boolean obfuscated = false;
        public final boolean bold = false;
        public final boolean strikethrough = false;
        public final boolean underline = false;
        public final boolean italic = false;
        @ConfigEntry.ColorPicker
        public final int color = 0xffffff;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        validateConfig();
    }

    private static ConfigHolder<VerboseSubtitlesConfig> getConfigHolder() {
        if (CONFIG_HOLDER == null) {
            ConfigHolder<VerboseSubtitlesConfig> configHolder = AutoConfig.getConfigHolder(VerboseSubtitlesConfig.class);
            CONFIG_HOLDER = configHolder;
            return configHolder;
        } else {
            return CONFIG_HOLDER;
        }
    }

    public static void saveConfig() {
        getConfigHolder().save();
    }

    public void validateConfig() {
        HashSet<String> blacklistedSoundsHashSet = new HashSet<>(blacklistedSounds);
        blacklistedSounds = new ArrayList<>(blacklistedSoundsHashSet);
    }

    public static void init() { }

    static {
        AutoConfig.register(VerboseSubtitlesConfig.class, GsonConfigSerializer::new);
        INSTANCE = getConfigHolder().getConfig();
    }
}
