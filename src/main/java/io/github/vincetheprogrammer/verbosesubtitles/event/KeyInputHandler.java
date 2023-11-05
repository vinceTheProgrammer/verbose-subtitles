package io.github.vincetheprogrammer.verbosesubtitles.event;

import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_SOUND_INFO = "Verbose Subtitles Mod";
    public static final String KEY_TOGGLE_SOUND_INFO = "Toggle Verbose Subtitles";
    public static final String KEY_TOGGLE_VANILLA_SUBTITLES = "Toggle Subtitles";

    public static KeyBinding toggleVerboseSubtitlesKey;
    public static KeyBinding toggleVanillaSubtitlesKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(toggleVerboseSubtitlesKey.wasPressed()) {
                VerboseSubtitlesConfig.INSTANCE.enabled = !VerboseSubtitlesConfig.INSTANCE.enabled;
                VerboseSubtitlesConfig.saveConfig();
            }
            if(toggleVanillaSubtitlesKey.wasPressed()) {
                client.options.getShowSubtitles().setValue(!client.options.getShowSubtitles().getValue());
            }
        });
    }

    public static void register() {
        toggleVerboseSubtitlesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_SOUND_INFO,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_SOUND_INFO
        ));
        toggleVanillaSubtitlesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_VANILLA_SUBTITLES,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY_SOUND_INFO
        ));
        registerKeyInputs();
    }
}
