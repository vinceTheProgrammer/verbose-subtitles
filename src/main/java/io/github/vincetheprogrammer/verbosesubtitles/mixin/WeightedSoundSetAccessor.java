package io.github.vincetheprogrammer.verbosesubtitles.mixin;

import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedSoundSet.class)
public interface WeightedSoundSetAccessor {
    @Accessor
    Text getSubtitle();

    @Mutable
    @Accessor
    void setSubtitle(Text subtitle);
}
