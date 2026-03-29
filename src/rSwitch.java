import processing.core.PApplet;

public class rSwitch extends rButton {

    public String textOn;
    public String textOff;
    public boolean on;

    public rSwitch(String textOn, String textOff,
                   float x, float y, float w, float h,
                   int f, int s, int t) {

        super(textOn, x, y, w, h, f, s, t);
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

    public void display(PApplet p5, int l) {
        if (this.on){ this.text = textOn;}
        else this.text = textOff;
        super.display(p5, l==1);
    }
}
