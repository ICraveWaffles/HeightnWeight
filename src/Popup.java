import processing.core.PApplet;

public class Popup {

    public rButton yes, no;
    public boolean on;
    public String msg;

    public Popup(PApplet p5, String msg){

        this.msg = msg;
        this.on = false;

        this.yes = new rButton("YES",675,600,150,60,3,7,7);
        this.no  = new rButton("NO",1245,600,150,60,3,7,7);
    }

    public void activate(){
        this.on=!this.on;
    }

    public int uSure(PApplet p5){

        if(yes.mouseOverButton(p5)){
            this.activate();
            return 1;
        }
        else if(no.mouseOverButton(p5)){
            this.activate();
            return 2;
        }
        else return 0;
    }

    public void display(PApplet p5,String name,int l){

        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER);

        p5.strokeWeight(9);

        p5.fill(Colors.getThisColor(5));
        p5.rect(960,540,900,450,45);

        p5.fill(Colors.getThisColor(6));

        p5.text(Languages.translate(msg,l),960,540);

        p5.textFont(Fonts.getThisFont(0));
        p5.text(name+(name.isEmpty()?"":" ?"),960,576);

        yes.display(p5,l==1);
        no.display(p5,l==1);

        p5.popStyle();
    }
}