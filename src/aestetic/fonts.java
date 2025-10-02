package aestetic;

import processing.core.PApplet;
import processing.core.PFont;
import static aestetic.fontsizes.*;

public class fonts {
    PFont[] fonts;

    public void setFonts(PApplet p5){
        this.fonts = new PFont[3];
        this.fonts[0] = p5.createFont("data/Consolas-regular.ttf", parrafadaSize);
    }

    public PFont getThisFont(int i){
        return this.fonts[i];
    }
}
