import processing.core.PApplet;

public class cButton {


    public float x, y, d;
    public int fillColor, strokeColor;
    public int fillColorOver, fillColorDisabled;

    String bText;
    int cText;
    public boolean enabled;

    public cButton(PApplet p5, String text, float x, float y, float d, int f, int s, int t){
        this.bText = text;
        this.x = x;
        this.y = y;
        this.d = d;
        this.enabled = true;
        this.fillColor = f;
        this.fillColorOver = f+1;
        this.fillColorDisabled = 0;
        this.strokeColor = s;
        this.cText = t;
    }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public boolean isEnabled(){
        return  this.enabled;
    }


    public void display(PApplet p5){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if(!enabled){
            p5.fill(Colors.getThisColor(fillColorDisabled));
        }
        else if(mouseOverButton(p5)){
            p5.fill(Colors.getThisColor(fillColorOver));
        }
        else{
            p5.fill(Colors.getThisColor(fillColor));
        }
        p5.stroke(Colors.getThisColor(strokeColor));
        p5.strokeWeight(2);
        p5.circle(this.x, this.y, this.d);


        p5.fill(Colors.getThisColor(cText));
        p5.textFont(Fonts.getThisFont(0));
        p5.text(bText, this.x , this.y-2);
        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5){
        return p5.dist(this.x, this.y, p5.mouseX, p5.mouseY)<= this.d/2;
    }
}