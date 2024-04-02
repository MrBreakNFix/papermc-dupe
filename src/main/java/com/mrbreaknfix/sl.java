package com.mrbreaknfix;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class sl {
    private static final Logger LOGGER = LoggerFactory.getLogger("temu");
    private static final Path SOUND = FabricLoader.getInstance().getConfigDir().resolve("forgis.wav");
    private static Clip clip;

    public static void init() {
        getSound().ifPresent(it -> {
            try {
                AudioInputStream is = AudioSystem.getAudioInputStream(it);
                clip = AudioSystem.getClip();
                clip.open(is);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                LOGGER.error("Couldn't load clip %s: %s".formatted(SOUND, e.getMessage()));
            }
        });
    }

    public static void tryPlay() {
        if (clip != null) {
            new Thread(() -> {
                clip.start();
                LOGGER.info("Playing!");
                try {
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                } catch (InterruptedException ignored) {
                }
            }).start();
        } else {
            LOGGER.error("Clip is null");
        }
    }

    private static Optional<File> getSound() {
        try {
            if (!Files.exists(SOUND)) {
                Files.createDirectories(SOUND.getParent());
                moveResource();
            }
            return Optional.of(SOUND.toFile().getAbsoluteFile());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static void moveResource() {
        try (InputStream is = sl.class.getClassLoader().getResourceAsStream("forgis.wav")) {
            if (is == null) throw new IllegalStateException("Sound resource not present");
            Files.copy(is, SOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
