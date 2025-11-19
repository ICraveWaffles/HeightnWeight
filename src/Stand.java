import processing.core.PApplet;
import processing.core.PImage;

public class Stand {

    String name;
    float x, y;
    float width, height;
    float tWidth, tHeight;
    PImage pic;

    Stand (String name,float tWidth, float tHeight, PImage pic){
        this.name = name;
        this.tWidth = tWidth;
        this.tHeight = tHeight;
        this.pic = pic;
    }

    public Stand() {

    }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.rectMode(p5.LEFT);
        p5.image(pic, x,y, width, height);
        p5.popStyle();
    }

}
