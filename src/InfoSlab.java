import processing.core.PApplet;
public class InfoSlab {

    int ID;
    int page;
    OC oc;
    rButton delete;

    public InfoSlab(int ID, OC oc, PApplet p5){
        this.ID = ID;
        this.page = (ID - 1) / 5;
        this.oc = (oc == null) ? new OC() : oc;
        this.delete = new rButton(p5, "OUT", 350 + ((ID - 1)  * 200), 560, 120, 40, 7,6,6);
    }


    public void display(PApplet p5, int currentPage){
        p5.pushMatrix();
        p5.textFont(Fonts.getThisFont(2));
        p5.stroke(255);
        p5.strokeWeight(3);
        p5.fill(oc.r, oc.g, oc.b);
        int x = 260 + ((ID - 1) - currentPage * 5) * 200;
        p5.rect(x, 76, 180, 632, 15);
        if ((oc.r + oc.g + oc.b > 480)) {
            p5.fill(0);
        } else {
            p5.fill(255);
        }

        p5.text(oc.name, x + 10, 110);
        p5.text(String.valueOf(oc.tHeight), x + 10, 170);
        p5.text(String.valueOf(oc.weight), x + 10, 230);
        p5.text(String.valueOf(oc.BMI), x + 10, 290);
        p5.text(String.valueOf(oc.tWidth), x + 10, 360);
        p5.text(String.valueOf(oc.bhratio), x + 10, 420);
        p5.text(String.valueOf(oc.age), x + 10, 480);

        delete.display(p5, 260 + ((ID - 1) - currentPage * 5) * 200);

        p5.popMatrix();
    }
}

