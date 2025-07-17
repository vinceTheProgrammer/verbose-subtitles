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
    public static VerboseSubtitlesConfig INSTANCE;
    @ConfigEntry.Gui.Excluded
    private static ConfigHolder<VerboseSubtitlesConfig> CONFIG_HOLDER;
//    @ConfigEntry.Category("general-category")
    public boolean enabled = true;
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsId optionsId = new OptionsId();
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsDisplayname optionsDisplayname = new OptionsDisplayname();
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsVolume optionsVolume = new OptionsVolume();
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsPitch optionsPitch = new OptionsPitch();
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsVariant optionsVariant = new OptionsVariant();
    @ConfigEntry.Gui.CollapsibleObject
    public OptionsPosition optionsPosition = new OptionsPosition();
    @ConfigEntry.Gui.PrefixText()
    public boolean logToFile = false;
    public List<String> blacklistedSounds = new ArrayList<>();


    public static class OptionsId {
        public boolean showId = true;
        public boolean showLabel = true;
        public String label = "ID: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStyleId labelStyleId = new LabelStyleId();
    }

    public static class OptionsDisplayname {
        public boolean showDisplayname = true;
        public boolean showLabel = true;
        public String label = "Displayname: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStyleDisplayname labelStyleDisplayname = new LabelStyleDisplayname();
    }

    public static class OptionsVolume {
        public boolean showVolume = true;
        public boolean showLabel = true;
        public String label = "Volume: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStyleVolume labelStyleVolume = new LabelStyleVolume();
    }

    public static class OptionsPitch {
        public boolean showPitch = true;
        public boolean showLabel = true;
        public String label = "Pitch: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStylePitch labelStylePitch = new LabelStylePitch();
    }

    public static class OptionsVariant {
        public boolean showVariant = true;
        public boolean showLabel = true;
        public String label = "Variant: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStyleVariant labelStyleVariant = new LabelStyleVariant();
    }

    public static class OptionsPosition {
        public boolean showPosition = true;
        public boolean showLabel = true;
        public String label = "Position: ";
        @ConfigEntry.Gui.CollapsibleObject
        public LabelStylePosition labelStylePosition = new LabelStylePosition();
    }

    public static class LabelStyleId {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
    }

    public static class LabelStyleDisplayname {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
    }

    public static class LabelStyleVolume {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
    }

    public static class LabelStylePitch {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
    }

    public static class LabelStyleVariant {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
    }

    public static class LabelStylePosition {
        public boolean obfuscated = false;
        public boolean bold = false;
        public boolean strikethrough = false;
        public boolean underline = false;
        public boolean italic = false;
        @ConfigEntry.ColorPicker
        public int color = 0xffffff;
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
