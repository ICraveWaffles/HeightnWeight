import processing.core.PApplet;

public class OC extends Stand{

    float weight;
    float BMI;

    float bhratio;
    float age;

    int r;
    int g;
    int b;

    public OC() {
        this.name = "Zwolf";
        this.tHeight = 1.828f;
        this.BMI = 25;
        this.weight = (float) Math.pow(this.tHeight,2)*this.BMI;
        this.tWidth = (float) Math.pow(this.BMI, 0.7979)/81.906f;

        this.age = 25;
        this.bhratio = Math.abs(-(1 / 0.0741f) * (float) Math.log((bhratio - 0.125f) / 0.125f));

        this.r = 127;
        this.g = 127;
        this.b = 127;
    }

    public void display(PApplet p5){
        p5.pushMatrix();

        p5.rectMode(p5.CORNER);
        p5.stroke(255);
        p5.strokeWeight(3);
        p5.fill(r & 0xFF,g & 0xFF,b & 0xFF);
        p5.rect(x, y+(height*bhratio), width, height*(1-bhratio), 10);
        if (width > height*bhratio){
            p5.ellipse(x+width / 2, y + (height*bhratio)/2, height*bhratio, height*bhratio);
        } else {
            p5.ellipse(x+width / 2, y + (height*bhratio)/2, width, height*bhratio);
        }


        p5.popMatrix();
    }
}
