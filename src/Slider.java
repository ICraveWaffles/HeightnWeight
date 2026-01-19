import processing.core.PApplet;

public class Slider {

    public String token;
    String s;
    float x, y, w, h;
    float minV, maxV, v;
    public boolean light;
    public boolean log; // Ahora esta variable determinará el comportamiento
    boolean enabled = true;

    public Slider(PApplet p5, String s, float x, float y, float w, float h, float minV, float maxV, float val, boolean light, boolean isLog) {
        this.token = s;
        this.s = Languages.translate(token, 1);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.minV = minV;
        this.maxV = maxV;
        this.light = light;
        this.log = isLog;

        if (this.log && this.minV <= 0) this.minV = 0.0001f;

        this.v = PApplet.constrain(val, minV, maxV);
    }

    public Slider(PApplet p5, String s, float x, float y, float w, float h, float minV, float maxV, float val) {
        this(p5, s, x, y, w, h, minV, maxV, val, true, false);
    }

    public float getValue() { return v; }
    public void setValue(float val) { v = PApplet.constrain(val, minV, maxV); }
    public void setEnabled(boolean e) { enabled = e; }
    public boolean isEnabled() { return enabled; }

    public void setLog(boolean isLog) {
        this.log = isLog;
        if (this.log && this.minV <= 0) this.minV = 0.0001f;
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        p5.rectMode(p5.CENTER);
        p5.stroke(100);
        p5.fill(light ? Colors.getThisColor(6) : Colors.getThisColor(7));
        p5.rect(x, y, w, h, 5);

        float knobX;
        if (log) {
            float logMin = (float) Math.log(minV);
            float logMax = (float) Math.log(maxV);
            float logVal = (float) Math.log(v);
            float t = (logVal - logMin) / (logMax - logMin);
            knobX = x - w / 2 + t * w;
        } else {
            knobX = x - w / 2 + p5.map(v, minV, maxV, 0, w);
        }

        p5.noStroke();
        p5.fill(light ? Colors.getThisColor(7) : Colors.getThisColor(6));

        p5.circle(knobX, y, h * 1.5f);

        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.fill(light ? Colors.getThisColor(6) : Colors.getThisColor(7));
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

    public boolean mouseOnSlider(PApplet p5) {
        return p5.mouseX > x - w / 2f - 20 && p5.mouseX < x + w / 2f + 5 && p5.mouseY > y - h - 5 && p5.mouseY < y + h + 5;
    }

    public void updateSlider(PApplet p5) {
        if (!enabled) return;
        float t = p5.map(p5.mouseX, x - w / 2f, x + w / 2f, 0f, 1f);
        t = PApplet.constrain(t, 0f, 1f);

        if (log) {
            float logMin = (float) Math.log(minV);
            float logMax = (float) Math.log(maxV);
            v = (float) Math.exp(PApplet.lerp(logMin, logMax, t));
        } else {
            v = p5.map(t, 0, 1, minV, maxV);
        }

        v = PApplet.constrain(v, minV, maxV);
        if(!log) v = Math.round(v * 1000f) / 1000f;
    }


    public void checkSlider(PApplet p5, Scene scene) {
        if (!enabled) return;
        if (p5.mousePressed && mouseOnSlider(p5, scene)) updateSlider(p5);
    }

    public void checkSlider(PApplet p5) {
        if (!enabled) return;
        if (p5.mousePressed && mouseOnSlider(p5)) updateSlider(p5);
    }
}
