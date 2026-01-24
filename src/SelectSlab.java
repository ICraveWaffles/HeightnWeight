import processing.core.PApplet;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.CENTER;

public class SelectSlab {

    OC oc;
    boolean isEnabled;
    boolean isSearched;
    int page;
    int y;

    public SelectSlab(PApplet p5, OC oc) {
        this.oc = oc;
        this.isEnabled = true;
        this.isSearched = true;
        this.y = 132 + (oc.ID % 10) * 58;
        this.page = oc.ID / 10;
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        p5.textFont(Fonts.getThisFont((oc.name.length() < 12) ? 1 : 2));
        p5.textAlign(CENTER, CENTER);
        p5.stroke(255);
        p5.strokeWeight(3);
        p5.fill(oc.r, oc.g, oc.b);

        p5.rect(12, y, 280, 50, 10);

        if (oc.r + oc.g + oc.b > 480) p5.fill(0);
        else p5.fill(255);

        String txt = (this.isEnabled) ? oc.name: "[[" + oc.name + "]]";
        p5.text(txt, 150, y + 22);
        p5.textFont(Fonts.getThisFont(1));
        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5) {
        if (this.isEnabled && this.isSearched) {
            return p5.mouseX >= 12 && p5.mouseX <= 292 && p5.mouseY >= y && p5.mouseY <= y + 50;
        }
        return false;
    }
}