import processing.core.PApplet;
import processing.sound.*;

public class Sounds {
    public static SoundFile[] sounds;
    public static void instanceSounds(PApplet p5){
        sounds = new SoundFile[10];
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


    }
    public static void emit(int s){
        sounds[s].play();
    }
}
