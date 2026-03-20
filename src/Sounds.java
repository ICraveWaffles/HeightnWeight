import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sounds {

    public static Clip[] sounds;
    public static float amp;

    public static void instanceSounds() {
        sounds = new Clip[18];
        sounds[0] = loadClip("data/Left.wav");
        sounds[1] = loadClip("data/Right.wav");
        sounds[2] = loadClip("data/FarLeft.wav");
        sounds[3] = loadClip("data/Left.wav");
        sounds[4] = loadClip("data/Right.wav");
        sounds[5] = loadClip("data/FarRight.wav");
        sounds[6] = loadClip("data/EnterScene.wav");
        sounds[7] = loadClip("data/ExitScene.wav");
        sounds[8] = loadClip("data/DelScene.wav");
        sounds[9] = loadClip("data/DelDelScene.wav");
        sounds[10] = loadClip("data/RButton.wav");
        sounds[11] = loadClip("data/AddStand.wav");
        sounds[12] = loadClip("data/AdOc.wav");
        sounds[13] = loadClip("data/AddOc.wav");
        sounds[14] = loadClip("data/DelAll.wav");
        sounds[15] = loadClip("data/DelDelAll.wav");
        sounds[16] = loadClip("data/Nuke.wav");
        sounds[17] = loadClip("data/Copy.wav");
        amp = 50f;
        redoAmp(amp);
    }

    private static Clip loadClip(String path) {
        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(new File(path));

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;

        } catch (UnsupportedAudioFileException |
                 IOException |
                 LineUnavailableException e) {
            return null;
        }
    }

    public static void emit(int s) {
        if (sounds[s] != null) {
            Clip clip = sounds[s];
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void redoAmp(float f) {
        amp = f;
        for (Clip clip : sounds) {
            if (clip != null) {
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (20 * Math.log10(amp / 100.0));
                gainControl.setValue(dB);
            }
        }
    }
}