package Sweeper;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Sound {

    public Sound(String soundEffect) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        String filename = "wav/" + soundEffect + ".wav";
        play(filename);
    }

    private void play(String soundEffect) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream audioSrc = classLoader.getResourceAsStream(soundEffect);
        InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(audioSrc));
        AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.setFramePosition(0);
        clip.start();
    }
}
