package io.github.vincetheprogrammer.verbosesubtitles.mixin;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

// Credit for most of this class goes to dicedpixels

@Mixin(SubtitlesHud.class)
abstract class MixinSubtitlesHud {
    @Shadow
    @Final
    private List<SubtitleEntry> entries;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onSoundPlayed", at = @At("HEAD"), cancellable = true)
    private void verboseSubtitles$addAnySoundToSubtitles(SoundInstance sound, WeightedSoundSet soundSet, CallbackInfo ci) {
        boolean isBlocked = false;
        for (String blacklistedSound : VerboseSubtitlesConfig.INSTANCE.blacklistedSounds) {
            if (sound.getId().toString().startsWith(blacklistedSound)) {
                VerboseSubtitles.LOGGER.info("\"" + sound.getId().toString() + "\" = \"" + blacklistedSound + "\"");
                isBlocked = true;
                break;
            }
        }
        if (VerboseSubtitlesConfig.INSTANCE.enabled) {
            if (soundSet.getSubtitle() == null) ((WeightedSoundSetAccessor)soundSet).setSubtitle(Text.of("testo"));
            if (!isBlocked) {
                MutableText text = MutableText.of(TextContent.EMPTY);
                Text idText = applyFormatting(Text.of("ID: ").copy(), VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.bold, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.underline, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.italic, new Formatting());
                Text volumeText = applyFormatting(Text.of("Volume: ").copy().formatted(Formatting.ITALIC));
                Text pitchText = applyFormatting(Text.of("Pitch: ").copy().formatted(Formatting.ITALIC));
                if (VerboseSubtitlesConfig.INSTANCE.optionsId.showId) text.append(idText).append(Text.of(sound.getId().toString())).append(" ");
                if (VerboseSubtitlesConfig.INSTANCE.optionsVolume.showVolume) text.append(volumeText).append(Text.of(String.valueOf(sound.getVolume()))).append(" ");
                if (VerboseSubtitlesConfig.INSTANCE.optionsPitch.showPitch) text.append(pitchText).append(Text.of(String.valueOf(sound.getPitch()))).append(" ");
                this.entries.add(new SubtitleEntry(text, new Vec3d(sound.getX(), sound.getY(), sound.getZ())));
                ci.cancel();
            } else {
                VerboseSubtitles.LOGGER.info("herrrrr");
                ci.cancel();
            }
        }
    }

    private MutableText applyFormatting(MutableText text, boolean obfuscated, boolean bold, boolean strikethrough, boolean underline, boolean italic, int formattingColor) {
        text.setStyle(new Style(TextColor.fromRgb(formattingColor), bold, italic, underline, strikethrough, obfuscated));
        return text;
    }
}
