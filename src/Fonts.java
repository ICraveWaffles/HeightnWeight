import processing.core.PApplet;
import processing.core.PFont;

public class Fonts {

    private static PFont[] fonts;

    public Fonts(PApplet p5) {
        setFonts(p5);
    }

    private void setFonts(PApplet p5) {
        fonts = new PFont[3];
        fonts[0] = p5.createFont("data/Consolas-Regular.ttf", FontSizes.bigSize);
        fonts[1] = p5.createFont("data/Consolas-Regular.ttf", FontSizes.buttonSize);
        fonts[2] = p5.createFont("data/Consolas-Regular.ttf", FontSizes.textSize);
    }

    public static PFont getThisFont(int i) {
        if (fonts == null) return null;
        return fonts[i];
    }
}
