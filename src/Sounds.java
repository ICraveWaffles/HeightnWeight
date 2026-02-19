import processing.core.PApplet;
import processing.sound.*;

public class Sounds {
    public static SoundFile[] sounds;
    public static float amp;

    public static void instanceSounds(PApplet p5) {
        sounds = new SoundFile[18];
        sounds[0] = new SoundFile(p5, "data/type.wav");
        sounds[1] = new SoundFile(p5, "data/detype.wav");
        sounds[2] = new SoundFile(p5, "data/FarLeft.wav");
        sounds[3] = new SoundFile(p5, "data/Left.wav");
        sounds[4] = new SoundFile(p5, "data/Right.wav");
        sounds[5] = new SoundFile(p5, "data/FarRight.wav");
        sounds[6] = new SoundFile(p5, "data/EnterScene.wav");
        sounds[7] = new SoundFile(p5, "data/ExitScene.wav");
        sounds[8] = new SoundFile(p5, "data/DelScene.wav");
        sounds[9] = new SoundFile(p5, "data/DelDelScene.wav");
        sounds[10] = new SoundFile(p5, "data/RButton.wav");
        sounds[11] = new SoundFile(p5, "data/AddStand.wav");
        sounds[12] = new SoundFile(p5, "data/AdOc.wav");
        sounds[13] = new SoundFile(p5, "data/AddOc.wav");
        sounds[14] = new SoundFile(p5, "data/DelAll.wav");
        sounds[15] = new SoundFile(p5, "data/DelDelAll.wav");
        sounds[16] = new SoundFile(p5, "data/Nuke.wav");
        sounds[17] = new SoundFile(p5, "data/Copy.wav");
        amp = 50f;
    }

    public static void emit(int s) {
        if (sounds[s] != null) {
            sounds[s].stop();
            sounds[s].play();
            sounds[s].amp(amp / 100f);
        }
    }

    public static void redoAmp(float f) {
        amp = f;
        for (SoundFile s : sounds) {
            if (s != null) {
                s.amp(amp / 100f);
            }
        }
    }
}