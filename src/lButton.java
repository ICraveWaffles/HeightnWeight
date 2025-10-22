import processing.core.PApplet;

public class lButton extends cButton {

    private boolean locked;


    public lButton(PApplet p5, boolean l, String text, float x, float y, float d, int f, int s, int t) {
        super(p5, text, x, y, d, f, s, t);
        this.locked = l;
        updateVisualState();
    }

    public void mousePressed(PApplet p5) {
        if (enabled && mouseOverButton(p5)) changeLock();
    }

    public void changeLock() {
        locked = !locked;
        updateVisualState();
    }

    private void updateVisualState() {
        if (locked) {
            bText = "·";
            fillColor = Colors.getThisColor(8);
        } else {
            bText = "";
            fillColor = Colors.getThisColor(6);
        }
    }

    public boolean isLocked() {
        return locked;
    }

}
