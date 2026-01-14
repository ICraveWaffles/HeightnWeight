import processing.core.PApplet;

enum STATE{NULL, NORM, PLUS};

public class rButton {

    public float x, y, w, h;
    public int fillColor, strokeColor;
    public int fillColorOver, fillColorDisabled;
    public final String token;
    public String text;
    public int cText;
    public boolean enabled;
    STATE state;

    public rButton(PApplet p5,String token, float x, float y, float w, float h, int f, int s, int t){
        this.token = token;
        this.text = Languages.translate(this.token, 2);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.enabled = true;
        this.fillColor = f;
        this.fillColorOver = f+1;
        this.strokeColor = s;
        this.cText = t;
        this.state = STATE.NORM;
    }

    public void display(PApplet p5){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if (this.state == STATE.NORM) {
            if (!enabled) {
                p5.fill(Colors.getThisColor(0));
            } else if (mouseOverButton(p5)) {
                p5.fill(Colors.getThisColor(fillColorOver));
            } else {
                p5.fill(Colors.getThisColor(fillColor));
            }
            p5.stroke(Colors.getThisColor(strokeColor));
            p5.strokeWeight(2);
            p5.rect(this.x, this.y, this.w, this.h, 10);

            p5.fill(Colors.getThisColor(cText));
            p5.textFont(Fonts.getThisFont(1));
            p5.text(text, this.x, this.y);
        } else if (this.state == STATE.PLUS){
            p5.fill(Colors.getThisColor(5));
            p5.stroke(Colors.getThisColor(strokeColor));
            p5.strokeWeight(2);
            p5.rect(this.x, this.y, this.w, this.h, 10);


            p5.fill(Colors.getThisColor(cText));
            p5.textFont(Fonts.getThisFont(1));
            p5.textSize(50);
            p5.text("+", this.x, this.y);
        }
        p5.popStyle();
    }

    public void display(PApplet p5, float x){
        this.x = x + 30 + this.w / 2;

        p5.pushStyle();
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if (!enabled) {
            p5.fill(Colors.getThisColor(0));
        } else if (mouseOverButton(p5)) {
            p5.fill(Colors.getThisColor(fillColorOver));
        } else {
            p5.fill(Colors.getThisColor(fillColor));
        }

        p5.stroke(Colors.getThisColor(strokeColor));
        p5.strokeWeight(2);
        p5.rect(this.x, this.y, this.w, this.h, 10);

        p5.fill(Colors.getThisColor(cText));
        p5.textFont(Fonts.getThisFont(1));
        p5.text(text, this.x, this.y);

        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5){
        return (p5.mouseX >= this.x-this.w/2) && (p5.mouseX <= this.x + this.w/2) &&
                (p5.mouseY >= this.y-this.h/2) && (p5.mouseY <= this.y + this.h/2);

    }

    public void updateSceneButton(int buttonIndex) {
        int i = buttonIndex % 5;
        int j = (buttonIndex / 5) % 3;

        int x = i * 250 + 120;
        int y = j * 212 + 200;

        this.x = x;
        this.y = y;
    }

    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }



}