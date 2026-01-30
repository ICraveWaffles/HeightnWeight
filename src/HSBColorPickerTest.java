import processing.core.PApplet;

public class HSBColorPickerTest extends PApplet {

    HSBColorPicker sX;

    public static void main(String[] args) {
        PApplet.main("HSBColorPickerTest", args);
    }

    public void settings(){
        size(800, 800);
        smooth(10);
    }

    public void setup(){
        sX = new HSBColorPicker(this,10, 10, 256, 64);
    }

    public void draw(){

        background(255);

        sX.display(this);


    }


    public void keyPressed(){
    }


    public void mousePressed(){
        sX.mousePressed(this);
    }

    public void mouseDragged(){
        sX.mouseDragged(this);
    }


}