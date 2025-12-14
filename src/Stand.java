import processing.core.PApplet;
import processing.core.PImage;

public class Stand {

    public int ID;
    long uniqueID;
    String name;
    float x, y;
    float width, height;
    float tWidth, tHeight;
    PImage pic;

    Stand (String name,float tWidth, float tHeight, PImage pic){
        this.uniqueID = System.nanoTime();
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
        p5.text(this.ID, x+width/2, y+height/2);
        p5.popStyle();
    }

    public void setID(int i){
        this.ID = i;
    }

}
