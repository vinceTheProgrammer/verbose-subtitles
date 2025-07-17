package io.github.vincetheprogrammer.verbosesubtitles.mixin;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import io.github.vincetheprogrammer.verbosesubtitles.config.VerboseSubtitlesConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

// Credit for most of this class goes to dicedpixels

@Mixin(SubtitlesHud.class)
abstract class MixinSubtitlesHud {
    @Shadow
    @Final
    private List<SubtitleEntry> entries;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onSoundPlayed", at = @At("HEAD"), cancellable = true)
    private void verboseSubtitles$addAnySoundToSubtitles(SoundInstance sound, WeightedSoundSet soundSet, float range, CallbackInfo ci) {
        boolean isBlocked = false;
        for (String blacklistedSound : VerboseSubtitlesConfig.INSTANCE.blacklistedSounds) {
            if (sound.getId().toString().startsWith(blacklistedSound)) {
                isBlocked = true;
                break;
            }
        }
        if (VerboseSubtitlesConfig.INSTANCE.enabled) {
            if (!isBlocked) {
                MutableText text = MutableText.of(Text.of("").getContent());
                Text displaynameText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.bold, VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.underline, VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.italic, VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.labelStyleDisplayname.color);
                Text idText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsId.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.bold, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.underline, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.italic, VerboseSubtitlesConfig.INSTANCE.optionsId.labelStyleId.color);
                Text volumeText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsVolume.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.bold, VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.underline, VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.italic, VerboseSubtitlesConfig.INSTANCE.optionsVolume.labelStyleVolume.color);
                Text pitchText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsPitch.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.bold, VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.underline, VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.italic, VerboseSubtitlesConfig.INSTANCE.optionsPitch.labelStylePitch.color);
                Text variantText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsVariant.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.bold, VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.underline, VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.italic, VerboseSubtitlesConfig.INSTANCE.optionsVariant.labelStyleVariant.color);
                Text positionText = applyFormatting(Text.of(VerboseSubtitlesConfig.INSTANCE.optionsPosition.label).copy(), VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.obfuscated, VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.bold, VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.strikethrough, VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.underline, VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.italic, VerboseSubtitlesConfig.INSTANCE.optionsPosition.labelStylePosition.color);

                if (VerboseSubtitlesConfig.INSTANCE.optionsId.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsId.showId) text.append(idText).append(sound.getId().toString()).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsId.showId) text.append(sound.getId().toString()).append(" ");
                }

                Text subtitleText = Objects.requireNonNullElse(soundSet.getSubtitle(), Text.of("nullDisplayName"));
                if (VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.showDisplayname) text.append(displaynameText).append(subtitleText.getString()).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsDisplayname.showDisplayname) text.append(subtitleText.getString()).append(" ");
                }

                if (VerboseSubtitlesConfig.INSTANCE.optionsVolume.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsVolume.showVolume) text.append(volumeText).append(String.valueOf(sound.getVolume())).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsVolume.showVolume) text.append(String.valueOf(sound.getVolume())).append(" ");
                }

                if (VerboseSubtitlesConfig.INSTANCE.optionsPitch.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsPitch.showPitch) text.append(pitchText).append(String.valueOf(sound.getPitch())).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsPitch.showPitch) text.append(String.valueOf(sound.getPitch())).append(" ");
                }

                if (VerboseSubtitlesConfig.INSTANCE.optionsVariant.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsVariant.showVariant) text.append(variantText).append((getVariant(sound))).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsVariant.showVariant) text.append((getVariant(sound))).append(" ");
                }

                if (VerboseSubtitlesConfig.INSTANCE.optionsPosition.showLabel) {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsPosition.showPosition) text.append(positionText).append(getRelativePositionString(sound)).append(" ");
                } else {
                    if (VerboseSubtitlesConfig.INSTANCE.optionsPosition.showPosition) text.append(getRelativePositionString(sound)).append(" ");
                }

                SubtitleEntry newEntry = new SubtitleEntry(text, range, new Vec3d(sound.getX(), sound.getY(), sound.getZ()));

                if (!VerboseSubtitlesConfig.INSTANCE.logToFile) VerboseSubtitles.closeFileWriter();

                if (VerboseSubtitlesConfig.INSTANCE.logToFile && VerboseSubtitles.getFileWriter() != null) {
                    try {
                        VerboseSubtitles.getFileWriter().write(newEntry.getText().getString() + System.lineSeparator());
                        VerboseSubtitles.getFileWriter().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                this.entries.add(newEntry);
                ci.cancel();
            } else {
                ci.cancel();
            }
        }
    }

    private MutableText applyFormatting(MutableText text, boolean obfuscated, boolean bold, boolean strikethrough, boolean underline, boolean italic, int formattingColor) {
        if (obfuscated) text.formatted(Formatting.OBFUSCATED);
        if (bold) text.formatted(Formatting.BOLD);
        if (strikethrough) text.formatted(Formatting.STRIKETHROUGH);
        if (underline) text.formatted(Formatting.UNDERLINE);
        if (italic) text.formatted(Formatting.ITALIC);
        text.styled(style -> style.withColor(formattingColor));
        return text;
    }

    private String getVariant(SoundInstance sound) {
        String[] strArr1 = sound.getSound().getLocation().toString().split("/");
        String fileName = strArr1[strArr1.length - 1];
        return fileName.split("\\.")[0];
    }

    private String getRelativePositionString(SoundInstance sound) {
        double soundX = sound.getX();
        double soundY = sound.getY();
        double soundZ = sound.getZ();

        ClientPlayerEntity player = client.player;
        if (player == null) return "?, ?, ?";
        Vec3d playerPosition = client.player.getPos();
        double yawDegrees = normalizeAngle(player.getYaw());
        double yaw = Math.toRadians(yawDegrees);


        double playerX = playerPosition.getX();
        double playerY = playerPosition.getY() + 2;
        double playerZ = playerPosition.getZ();

        double dx = soundX - playerX;
        double dy = soundY - playerY;
        double dz = soundZ - playerZ;

        double relative_x = -((dz * Math.sin(yaw)) + (dx * Math.cos(yaw)));
        double relative_z = (dz * Math.cos(yaw)) - (dx * Math.sin(yaw));

        return "horiz:" + Math.round(relative_x) + " fwd:" + Math.round(relative_z) + " vert:" + Math.round(dy);
    }

    private double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

}
