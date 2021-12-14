package mygame;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BombSound {

    private Clip clip;
    private String[] url = new String[15];

    public BombSound() {
        url[0] = "res/sound/bombExplosionSound.wav";
    }

    public void setbombSound(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url[i]).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
        clip.close();
    }

}
