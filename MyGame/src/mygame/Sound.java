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

public class Sound {

    private Clip clip;
    private String[] url = new String[15];

    public Sound() {
        url[0] = "res/sound/titleMusic.wav";
        url[1] = "res/sound/bombExplosionSound.wav";
        url[2] = "res/sound/playingMusic.wav";
        url[3] = "res/sound/loadingMusic.wav";
        url[4] = "res/sound/lifeLostMusic.wav";
        url[5] = "res/sound/gameoverMusic.wav";
    }

    public void setSound(int i) {
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

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
        clip.close();
    }

    public void stop1() {
        clip.stop();
    }

    public Clip getClip() {
        return clip;
    }

}
