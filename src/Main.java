import aestetic.Fonts;

import processing.core.PApplet;


public class Main extends PApplet {

    Fonts fonts;



    public static void main(String[]args){
    PApplet.main("Main");
    }

    public void settings(){
        size(1920, 1080);

        smooth(6);
    }

    public void setup(){
        fonts = new Fonts(this);
    }

    public void draw(){
        background (127);
        textFont(fonts.getThisFont(0));
        text("Consolas test, say hello to the damn camera", 20, 200);
        textFont(fonts.getThisFont(1));
        text("Consolas test, say hello to the damn camera", 40, 300);
        textFont(fonts.getThisFont(2));
        text("Consolas test, say hello to the damn camera", 80, 400);
        textFont(fonts.getThisFont(3));
        text("Consolas test, say hello to the damn camera", 160, 500);


    }
}