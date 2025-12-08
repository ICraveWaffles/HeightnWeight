import processing.core.PApplet;

public class SelectSlab {

    OC oc;

    public SelectSlab(PApplet p5, OC oc) {
        this.oc = oc;
    }

    public void display(PApplet p5){
        p5.pushMatrix();
        p5.textFont(Fonts.getThisFont((oc.name.length()<12)?1:2));
        p5.textMode(p5.CENTER);
        p5.stroke(255);
        p5.strokeWeight(3);
        p5.fill(oc.r, oc.g, oc.b);

        int y = 132 + (oc.ID % 10) * 66;

        p5.rect(10, y, 280, 50, 10);

        if (oc.r + oc.g + oc.b > 480) {
            p5.fill(0);
        } else {
            p5.fill(255);
        }

        p5.text(oc.name, 15, y + 25);
        p5.textFont(Fonts.getThisFont(1));
        p5.popMatrix();
    }

    public boolean mouseOverButton(PApplet p5) {
        int y = 132 + (oc.ID % 10) * 66;
        return
                p5.mouseX >= 10 &&
                        p5.mouseX <= 290 &&
                        p5.mouseY >= y &&
                        p5.mouseY <= y + 50;
    }
}
