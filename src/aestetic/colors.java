package aestetic;

import processing.core.PApplet;

public class colors {

    public int [] colors;

    public colors (PApplet p5){
        this.instanceColors(p5);
    }

    public void instanceColors(PApplet p5) {
        colors = new int[9];
        colors[0] = p5.color(0XFF414141);
        colors[1] = p5.color(0XFF534559);
        colors[2] = p5.color(0XFFF4F0F7);
        colors[3] = p5.color(0XFF440D6E);
        colors[4] = p5.color(0XFF7D67E3);
        colors[5] = p5.color(0XFFEED6FF);
        colors[6] = p5.color(0XFF000000);
        colors[7] = p5.color(0XFFFFFFFF);
        colors[8] = p5.color(0XFFFF0000);
    }

    public int getThisColor(int i){
        return this.colors[i];
    }
}
