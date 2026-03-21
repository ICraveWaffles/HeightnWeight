import processing.core.PApplet;

public class Notification {

    String msg;
    int x, y, size;
    boolean good;
    float phase;
    float dy;
    boolean on;

    public Notification() {
        this.on = false;
        this.phase = 0;
    }

    public void trigger(String msg, int x, int y, boolean good, int size, int lang) {
        this.msg = Languages.translate(msg, lang);
        this.x = x;
        this.y = y;
        this.good = good;
        this.size = size;
        this.phase = 255;
        this.dy = 20;
        this.on = true;
    }

    public void display(PApplet p5) {
        if (!this.on) return;

        p5.pushStyle();

        p5.textFont(Fonts.getThisFont(this.size));
        p5.textAlign(p5.CENTER, p5.CENTER);

        p5.noStroke();
        p5.fill(20, this.phase * 0.85f);
        p5.rectMode(p5.CENTER);
        p5.rect(this.x, this.y - this.dy, p5.textWidth(this.msg) + 40, this.size * 1.8f, 8);

        if (this.good) {
            p5.fill(Colors.getThisColor(9), this.phase);
        } else {
            p5.fill(Colors.getThisColor(8), this.phase);
        }

        p5.text(this.msg, this.x, this.y - this.dy - (this.size * 0.1f));

        this.phase -= 2.5f;
        this.dy = p5.lerp(this.dy, 0, 0.15f);

        if (this.phase <= 0) {
            this.on = false;
        }

        p5.popStyle();
    }
}
