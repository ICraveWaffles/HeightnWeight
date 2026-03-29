import processing.core.PApplet;
import processing.core.PImage;

public class Stand {

    public int ID;
    public long uniqueID;
    public String name;
    public float x, y;
    public float width, height;
    public float tWidth, tHeight;
    public PImage pic;

    public Stand() {

    }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.rectMode(p5.LEFT);
        p5.image(pic, x,y, width, height);
        p5.popStyle();
    }

}
