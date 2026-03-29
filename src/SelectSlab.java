import processing.core.PApplet;

public class SelectSlab {

    public OC oc;
    public boolean isEnabled;
    public boolean isSearched;
    public int page;
    public int ID;
    public int y;

    public SelectSlab(PApplet p5, OC oc) {

        this.oc = oc;
        this.ID = oc.ID;

        this.isEnabled = true;
        this.isSearched = true;
        this.y = 198 + (oc.ID % 10) * 87;

        this.page = oc.ID / 10;
    }

    public void display(PApplet p5) {

        p5.pushStyle();

        p5.textFont(Fonts.getThisFont((oc.name.length() < 12) ? 1 : 2));
        p5.textAlign(PApplet.CENTER,PApplet.CENTER);

        p5.stroke(255);
        p5.strokeWeight(4.5f);

        p5.fill(oc.r,oc.g,oc.b);

        p5.rect(18,y,420,75,15);

        if(oc.r+oc.g+oc.b>480) p5.fill(0);
        else p5.fill(255);

        String txt = (this.isEnabled) ? oc.name : "[["+oc.name+"]]";

        p5.text(txt,225,198+(ID%10)*87+33);

        p5.textFont(Fonts.getThisFont(1));

        p5.popStyle();
    }

    public boolean mouseOverButton(PApplet p5){

        if(this.isEnabled && this.isSearched){

            return p5.mouseX>=18 &&
                    p5.mouseX<=438 &&
                    p5.mouseY>=y &&
                    p5.mouseY<=y+75;
        }

        return false;
    }
}