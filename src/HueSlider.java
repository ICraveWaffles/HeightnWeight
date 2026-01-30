import processing.core.PApplet;

public class HueSlider {

    float x, y, w, h;
    float minV, maxV, v;

    int color;

    public HueSlider(PApplet p5, float x, float y, float w, float h) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.v = 255; this.minV = 0; this.maxV = 255;
    }

    public float getValue(){
        return this.v;
    }

    public void setColor(int c){
        this.color = c;
    }

    public void display(PApplet p5) {
        p5.pushStyle();

        p5.rect(x, y, w, h, 5);
        p5.colorMode(p5.HSB);
        for(float hue=0; hue<256; hue+=0.5f){
            p5.stroke(hue, 255, 255);
            p5.strokeWeight(1f);
            float xh = p5.map(hue, 0, 255, x, x+w);
            p5.line(xh, y, xh, y+h);
        }

        p5.colorMode(p5.RGB);
        p5.noStroke(); p5.fill(255);
        p5.rect(x + p5.map(v, minV, maxV, 0, w) - 2.5f, y, 5, h);
        p5.popStyle();
    }

    public boolean mouseOnSlider(PApplet p5) {
        return ((p5.mouseX>x) && (p5.mouseX<x+w) &&
                (p5.mouseY>=y)&& (p5.mouseY<=y+h));
    }

    public void updateSlider(PApplet p5) {
        v = p5.map(p5.mouseX, x, x+w, minV, maxV);
        v = p5.constrain(v, minV,maxV);
    }

    public void checkSlider(PApplet p5){
        if(mouseOnSlider(p5)){
            this.updateSlider(p5);
        }
    }

}