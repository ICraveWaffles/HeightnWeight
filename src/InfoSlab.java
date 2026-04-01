import processing.core.PApplet;

public class InfoSlab {

    public int page;
    public OC oc;
    public int ID;
    public int x;
    public rButton delete;

    public InfoSlab(OC oc, PApplet p5) {

        this.oc = oc;
        this.ID = oc.ID;
        this.page = (oc.ID) / 5;

        this.x = 720 + ((ID - 1) - page * 5) * 300;

        this.delete = new rButton(
                "DELETE",
                525 + ((oc.ID - 1) * 300),
                840,
                180,
                60,
                6,7,7
        );
    }

    public void display(PApplet p5) {
        p5.pushStyle();

        p5.textFont(Fonts.getThisFont((oc.name.length() < 12) ? 1 : 2));
        p5.textMode(p5.CENTER);

        p5.stroke(Colors.getThisColor(7));
        p5.strokeWeight(4.5f);

        p5.fill(oc.r, oc.g, oc.b);

        float slabW = (ID % 5 == 4) ? 294 : 270;

        p5.rect(this.x,114,slabW,948,22.5f);
        p5.line(this.x,192,this.x+slabW,192);
        p5.line(this.x,186,this.x+slabW,186);

        if(oc.r+oc.g+oc.b>480) p5.fill(0);
        else p5.fill(255);

        p5.text(oc.name,x+15,165);

        p5.textFont(Fonts.getThisFont(1));

        p5.text(String.valueOf(oc.tHeight),x+15,255);
        p5.text(String.valueOf(oc.weight),x+15,345);
        p5.text(String.valueOf(oc.BMI),x+15,435);
        p5.text(String.valueOf(oc.tWidth),x+15,540);
        p5.text(String.valueOf(oc.bhratio),x+15,630);
        p5.text(String.valueOf(oc.age),x+15,720);

        p5.text(String.valueOf(this.oc.uniqueID),x+15,1005);

        delete.display(p5,x);

        p5.popStyle();
    }
}