package Sweeper;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private File exterminationSound = new File("res/wav/exterminate.wav");
    AudioInputStream ais;
    Clip clip;

    public Sound (String sound) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        switch (sound){
            case "exterminationSound": play (exterminationSound);break;
        }

    }
    private void play(File soundEffect) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ais = AudioSystem.getAudioInputStream(soundEffect);
        clip = AudioSystem.getClip();
        clip.open(ais);
        clip.setFramePosition(0);
        clip.start();

    }

}
