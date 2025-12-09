import processing.core.PApplet;

public class Slider {

    String s;
    float x, y, w, h;
    float minV, maxV, v;
    int barColor, knobColor, textColor;
    boolean enabled = true;

    public Slider(PApplet p5, String s, float x, float y, float w, float h, float minV, float maxV, float val) {
        this.s = s;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.minV = minV;
        this.maxV = maxV;
        this.v = PApplet.constrain(val, minV, maxV);
        this.barColor = Colors.getThisColor(1);
        this.knobColor = Colors.getThisColor(8);
        this.textColor = Colors.getThisColor(6);
    }

    public float getValue() { return v; }
    public void setValue(float val) { v = PApplet.constrain(val, minV, maxV); }
    public void setEnabled(boolean e) { enabled = e; }
    public boolean isEnabled() { return enabled; }

    public void display(PApplet p5) {
        p5.pushStyle();
        p5.rectMode(p5.CENTER);
        p5.stroke(100);
        p5.fill(enabled ? barColor : 180);
        p5.rect(x, y, w, h, 5);

        float knobX = x - w / 2 + p5.map(v, minV, maxV, 0, w);
        p5.noStroke();
        p5.fill(enabled ? knobColor : 150);
        p5.circle(knobX, y, h * 1.5f);

        p5.fill(textColor);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.textSize(24);
        p5.text(s + ": ", x - w / 2, y - h - 15);
        p5.popStyle();
    }

    public boolean mouseOnSlider(PApplet p5, Scene scene) {
        if (scene.sel != Scene.scInstance.OCSELECT) {
            return p5.mouseX > x - w / 2f - 20 && p5.mouseX < x + w / 2f + 5 && p5.mouseY > y - h - 5 && p5.mouseY < y + h + 5;
        }
        else return false;
    }

    public boolean mouseDraggingOnSlider(PApplet p5) {
        return p5.mousePressed && p5.mouseX > x - w / 2f-20 && p5.mouseX < x + w / 2 +20 && p5.mouseY > y - h * 2f - 5 && p5.mouseY < y + h * 2 +   5;
    }

    public void updateSlider(PApplet p5) {
        if (!enabled) return;
        v = p5.map(p5.mouseX, x - w / 2f + 1, x + w / 2f - 1, minV, maxV);
        v = PApplet.constrain(v, minV, maxV);
        v = Math.round(v * 1000.0f) / 1000.0f;
    }

    public void checkSlider(PApplet p5, Scene scene) {
        if (!enabled) return;
        if (p5.mousePressed && mouseOnSlider(p5, scene)) updateSlider(p5);
    }
}
