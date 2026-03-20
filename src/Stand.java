import processing.core.PApplet;
import processing.core.PImage;

public class Stand {

    public int ID;
    long uniqueID;
    String name;
    float x, y;
    float width, height;
    float tWidth, ttWidth, tHeight;
    PImage pic;

    public Stand() {

    }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.rectMode(p5.LEFT);
        p5.image(pic, x,y, width, height);
        p5.popStyle();
    }

}
