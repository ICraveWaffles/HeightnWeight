import processing.core.PApplet;

public class rButton {


    float x, y, w, h;
    int fillColor, strokeColor;
    int fillColorOver, fillColorDisabled;
    String bText;
    int cText;
    public boolean enabled;

    public rButton(PApplet p5, String text, float x, float y, float w, float h, int f, int s, int t){
        this.bText = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.enabled = true;
        this.fillColor = Colors.getThisColor(f);
        this.fillColorOver = Colors.getThisColor(f+1);
        this.fillColorDisabled = Colors.getThisColor(0);
        this.strokeColor = Colors.getThisColor(s);
        this.cText = Colors.getThisColor(t);
    }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public void setColors(int cFill, int cStroke, int cOver, int cDisabled){
        this.fillColor = cFill;
        this.strokeColor = cStroke;
        this.fillColorOver = cOver;
        this.fillColorDisabled = cDisabled;
    }

    public boolean isEnabled(){
        return  this.enabled;
    }


    public void display(PApplet p5){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if(!enabled){
            p5.fill(fillColorDisabled);
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);
        }
        else{
            p5.fill(fillColor);
        }
        p5.stroke(strokeColor);
        p5.strokeWeight(2);
        p5.rect(this.x, this.y, this.w, this.h, 10);


        p5.fill(cText);
        p5.textFont(Fonts.getThisFont(1));
        p5.text(bText, this.x , this.y);
        p5.popStyle();
    }

    public void display(PApplet p5, float x){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if(!enabled){
            p5.fill(fillColorDisabled);
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);
        }
        else{
            p5.fill(fillColor);
        }
        p5.stroke(strokeColor);
        p5.strokeWeight(2);



        p5.rect(x+30+this.w/2, this.y, this.w, this.h, 10);


        p5.fill(cText);
        p5.textFont(Fonts.getThisFont(1));
        p5.text(bText, x+30+this.w/2 , this.y);
        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5){
        return (p5.mouseX >= this.x-this.w/2) && (p5.mouseX <= this.x + this.w/2) &&
                (p5.mouseY >= this.y-this.h/2) && (p5.mouseY <= this.y + this.h/2);

    }


    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}