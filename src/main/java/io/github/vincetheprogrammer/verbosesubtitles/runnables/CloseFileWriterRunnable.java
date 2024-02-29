package io.github.vincetheprogrammer.verbosesubtitles.runnables;

import io.github.vincetheprogrammer.verbosesubtitles.VerboseSubtitles;

import java.io.FileWriter;
import java.io.IOException;

public class CloseFileWriterRunnable implements Runnable {
    private final FileWriter fileWriter;

    public CloseFileWriterRunnable(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public void run() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
                VerboseSubtitles.LOGGER.info("successfully closed FileWriter");
            }
        } catch (IOException e) {
            VerboseSubtitles.LOGGER.error("Error closing file writer", e);
        }
    }
}
