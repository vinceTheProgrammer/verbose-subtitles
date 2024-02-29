package io.github.vincetheprogrammer.verbosesubtitles.runnables;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;
import net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;

import java.io.FileWriter;
import java.io.IOException;

public class SubtitleLoggerRunnable implements  Runnable {
    private final SubtitleEntry entry;
    private final FileWriter fileWriter;

    public SubtitleLoggerRunnable(SubtitleEntry entry, FileWriter fileWriter) {
        this.entry = entry;
        this.fileWriter = fileWriter;
    }

    @Override
    public void run() {
        if (fileWriter != null) {
            try {
                fileWriter.write(entry.getText().getString() + System.lineSeparator());
                fileWriter.flush();
            } catch (IOException e) {
                VerboseSubtitles.LOGGER.error("Error logging subtitle to file", e);
            }
        }
    }
}
