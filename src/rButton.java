import processing.core.PApplet;

enum STATE{NULL, NORM, PLUS, BASE};

public class rButton {

    public float x, y, w, h;
    public int fillColor, strokeColor;
    public int fillColorOver, fillColorDisabled;
    public int ID;
    public final String token;
    public String text;
    public int cText;
    public boolean enabled;
    STATE state;
    boolean lastPress = false;

    public rButton(String token, float x, float y, float w, float h, int f, int s, int t){
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
        this.state = STATE.BASE;
    }

    public void display(PApplet p5, boolean b){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if (this.state == STATE.NORM || this.state == STATE.BASE) {
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

            if (token.equals("COLORPICK")){
                for (int i = 0; i < 2*w/3; i++) {
                    float t = i / (2*w/3);
                    p5.stroke(p5.lerpColor(Colors.getThisColor(7), Colors.getThisColor(3), t));
                    p5.line(x+i-w/3, y-h/3, x+i-w/3, y+h/3);
                }
            } else if (token.equals("GRID")){
                float margin = 6;
                float gridW = w - margin*2;
                float gridH = h - margin*2;
                float left = x - gridW/2;
                float top  = y - gridH/2;
                float stepX = gridW / 5.0f;
                float stepY = gridH / 5.0f;
                for (int i = 0; i <= 5; i++) {
                    float lx = left + i * stepX;
                    p5.line(lx, top, lx, top + gridH);
                    float ly = top + i * stepY;
                    p5.line(left, ly, left + gridW, ly);
                }
            } else if (token.equals("SCREENSHOT")){
                float inner = w * 0.65f;
                float left = x - inner/2;
                float right = x + inner/2;
                float top = y - inner/2;
                float bottom = y + inner/2;
                float corner = inner * 0.22f;

                p5.strokeCap(PApplet.ROUND);
                p5.strokeJoin(PApplet.ROUND);
                p5.noFill();

                p5.line(left, top, left + corner, top);
                p5.line(left, top, left, top + corner);
                p5.line(right - corner, top, right, top);
                p5.line(right, top, right, top + corner);
                p5.line(left, bottom - corner, left, bottom);
                p5.line(left, bottom, left + corner, bottom);
                p5.line(right - corner, bottom, right, bottom);
                p5.line(right, bottom - corner, right, bottom);

                p5.circle(x, y, inner * 0.28f);
            } else if (token.equals("DEL")) {
                float inner = w * 0.65f;
                float left = x - inner/2;
                float right = x + inner/2;
                float top = y - inner/2;
                float bottom = y + inner/2;
                float cross = inner * 0.2f;

                p5.line(left + cross,  top + cross,    right - cross, bottom - cross);
                p5.line(right - cross, top + cross,    left + cross,  bottom - cross);
            } else if (token.equals("COPY")){
                float inner = w * 0.65f;
                float s = inner * 0.7f;
                float r = s * 0.18f;
                float x1 = x - s*0.15f;
                float y1 = y - s*0.15f;
                float x2 = x + s*0.15f;
                float y2 = y + s*0.15f;
                p5.noFill();
                p5.rectMode(PApplet.CENTER);
                p5.rect(x1, y1, s, s, r);
                p5.fill(Colors.getThisColor(7));
                p5.rect(x2, y2, s, s, r);
            } else if (token.equals("EXIT")){
                float inner = w * 0.7f;
                float left   = x - inner/2;
                float right  = x + inner/2;
                float top    = y - inner/2;
                float bottom = y + inner/2;
                float gap = inner * 0.22f;
                p5.noFill();
                p5.strokeCap(PApplet.ROUND);
                p5.strokeJoin(PApplet.ROUND);
                p5.beginShape();
                p5.vertex(left + gap, top);
                p5.vertex(right, top);
                p5.vertex(right, bottom);
                p5.vertex(left + gap, bottom);
                p5.endShape();
                float a = inner * 0.28f;
                p5.line(x - a, y, x + a, y);
                p5.line(x + a, y, x + a*0.35f, y - a*0.65f);
                p5.line(x + a, y, x + a*0.35f, y + a*0.65f);
            }
            else {
                p5.textFont(Fonts.getThisFont(1));
                p5.text(this.state == STATE.BASE ? Languages.translate(text, b ? 1 : 2) : "", this.x, this.y - (this.state == STATE.NORM ? 30 : 0));
            }
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

    public void display (PApplet p5, Scene sc, int l){
        p5.pushStyle();
        p5.textAlign(p5.CENTER);
        p5.fill(Colors.getThisColor(sc.nObjects != 0? 7 : 8));
        p5.text(Languages.translate("OCS", l) +sc.nObjects, this.x, this.y-10);
        p5.text((Languages.translate("TALL", l)) , this.x, this.y + 30);
        p5.text(sc.getTallestObject(), this.x, this.y + 60);
        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5){
        boolean thing = (p5.mouseX >= this.x-this.w/2) && (p5.mouseX <= this.x + this.w/2) &&
                (p5.mouseY >= this.y-this.h/2) && (p5.mouseY <= this.y + this.h/2);
        if (thing && p5.mousePressed && !lastPress){
            Sounds.emit(1);
        }
        lastPress = p5.mousePressed;
        return thing;

    }

    public void updateSceneButton(int buttonIndex) {
        int i = buttonIndex % 5;
        int j = (buttonIndex / 5) % 3;
        int x = i * 250 + 120;
        int y = j * 212 + 200;
        this.x = x;
        this.y = y;
    }
}