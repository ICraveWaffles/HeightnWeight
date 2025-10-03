package aestetic;

import processing.core.PApplet;
import processing.core.PFont;
import static aestetic.fontsizes.*;

public class Fonts {

    PFont[] fonts;

    public Fonts(PApplet p5){
        setFonts(p5);
    }

    public void setFonts(PApplet p5){
        this.fonts = new PFont[4];
        this.fonts[0] = p5.createFont("data/Consolas-Regular.ttf", bigSize);
        this.fonts[1] = p5.createFont("data/Consolas-Regular.ttf", buttonSize);
        this.fonts[2] = p5.createFont("data/Consolas-Regular.ttf", textSize);
    }

    public PFont getThisFont(int i){
        return this.fonts[i];
    }
}
