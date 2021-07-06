package Sweeper;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public Sound (String sound) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File exterminationSound = new File("res/wav/exterminate.wav");
        File winsound = new File("res/wav/winsound.wav");
        File startsound = new File("res/wav/startsound.wav");
        switch (sound){
            case "exterminationSound": play (exterminationSound);break;
            case "winsound": play (winsound);break;
            case "startsound": play (startsound);break;
        }

    }
    private void play(File soundEffect) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundEffect);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.setFramePosition(0);
        clip.start();

    }

}
