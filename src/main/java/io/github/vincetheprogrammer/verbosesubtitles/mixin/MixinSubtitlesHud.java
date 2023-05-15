package io.github.vincetheprogrammer.verbosesubtitles.mixin;

import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
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

    @Inject(method = "onSoundPlayed", at = @At("HEAD"), cancellable = true)
    private void verboseSubtitles$addAnySoundToSubtitles(SoundInstance sound, WeightedSoundSet soundSet, CallbackInfo ci) {
        boolean isBlocked = false;
        for (String blacklistedSound : VerboseSubtitlesConfig.INSTANCE.blacklistedSounds) {
            if (sound.getId().toString().startsWith(blacklistedSound)) {
                isBlocked = true;
                break;
            }
        }
        if (VerboseSubtitlesConfig.INSTANCE.enabled) {
            if (!isBlocked) {
                MutableText text = MutableText.of(TextContent.EMPTY);
                Text idText = Text.of("ID: ").copy().formatted(Formatting.ITALIC);
                Text volumeText = Text.of("Volume: ").copy().formatted(Formatting.ITALIC);
                Text pitchText = Text.of("Pitch: ").copy().formatted(Formatting.ITALIC);
                if (VerboseSubtitlesConfig.INSTANCE.showID) text.append(idText).append(Text.of(sound.getId().toString())).append(" ");
                if (VerboseSubtitlesConfig.INSTANCE.showVolume) text.append(volumeText).append(Text.of(String.valueOf(sound.getVolume()))).append(" ");
                if (VerboseSubtitlesConfig.INSTANCE.showPitch) text.append(pitchText).append(Text.of(String.valueOf(sound.getPitch()))).append(" ");
                this.entries.add(new SubtitleEntry(text, new Vec3d(sound.getX(), sound.getY(), sound.getZ())));
                ci.cancel();
            } else {
                ci.cancel();
            }
        }
    }
}
