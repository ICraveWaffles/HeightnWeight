import processing.core.PApplet;

public class rSwitch extends rButton {

    String textOn;
    String textOff;
    boolean on;

    public rSwitch(PApplet p5, String textOn, String textOff,
                   float x, float y, float w, float h,
                   int f, int s, int t) {

        super(p5, textOn, x, y, w, h, f, s, t);
        this.textOn = textOn;
        this.textOff = textOff;
        this.on = true;
    }

    public void toggle() {
        if (!enabled) return;
        on = !on;
    }

    public boolean isOn() {
        return on;
    }

    public void display(PApplet p5) {
        super.display(p5);
    }
}
