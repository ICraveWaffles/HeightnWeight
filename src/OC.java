import processing.core.PApplet;
import java.util.Random;

public class OC extends Stand {

    float weight;
    float BMI;

    float bhratio;
    float age;

    int r;
    int g;
    int b;

    public OC(int ID) {
        this.ID = ID;
        Random random = new Random();
        this.uniqueID = 100000 + random.nextInt(900000);


        this.name = "Zwolf";

        this.tHeight = 1.828f;
        this.BMI = 25f;
        this.weight = (float) Math.pow(this.tHeight, 2) * this.BMI;
        this.tWidth = (float) Math.pow(this.BMI, 0.7979f) / 81.906f;

        this.age = 25f;
        this.bhratio = 0.5f;

        this.r = 127;
        this.g = 127;
        this.b = 127;
    }

    public OC(String name, float tHeight, float BMI, float weight, float tWidth,
              float age, float bhratio, int r, int g, int b) {

        this.ID = -1;


        this.name = name;

        this.tHeight = tHeight;
        this.BMI = BMI;
        this.weight = weight;
        this.tWidth = tWidth;

        this.age = age;

        if (bhratio < 0.01f) bhratio = 0.01f;
        this.bhratio = bhratio;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void display(PApplet p5) {
        p5.pushMatrix();

        p5.rectMode(PApplet.CORNER);
        p5.stroke(255);
        p5.strokeWeight(3);
        p5.fill(r & 0xFF, g & 0xFF, b & 0xFF);

        float headH = height * bhratio;
        float bodyH = height - headH;

        p5.rect(x, y + headH, width, bodyH, 10);

        if (width > headH) {
            p5.ellipse(x + width / 2f, y + headH / 2f, headH, headH);
        } else {
            p5.ellipse(x + width / 2f, y + headH / 2f, width, headH);
        }

        p5.fill(255);
        p5.popMatrix();
    }
}
