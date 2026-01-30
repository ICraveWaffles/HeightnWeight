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

    public OC(OC other) {
        this.tHeight = other.tHeight;
        this.weight = other.weight;
        this.BMI = other.BMI;
        this.tWidth = other.tWidth;
        this.bhratio = other.bhratio;
        this.age = other.age;
        this.r = other.r;
        this.g = other.g;
        this.b = other.b;
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

    public void display(PApplet p5, Scene sc) {
        p5.pushMatrix();
        p5.rectMode(PApplet.CENTER);
        p5.stroke(0);
        p5.strokeWeight(2);

        float headH = height * bhratio;
        float remainingH = height - headH;
        float neckH = remainingH * 0.05f;
        float bodyH = remainingH * 0.95f;

        float torsoTotalH = bodyH * 0.40f;
        float pelvisH = bodyH * 0.10f;
        float legsTotalH = bodyH * 0.50f;

        float bmi = PApplet.max(1.1f, this.BMI);
        float bmiFactor = PApplet.log(bmi);

        float armInset = bmiFactor * (width * 0.02f);

        float trueTotalWidth = PApplet.max(
                width,
                2f * ((width / 2f) - armInset)
        );

        float shoulderW = width * 0.55f;
        float waistW = width * 0.40f;
        float hipsW = width * 0.50f;

        float centerX = x + trueTotalWidth / 2f;
        p5.translate(centerX, y);

        float headScaleY = headH > shoulderW ? shoulderW / headH : 1f;
        p5.fill(r, g, b);
        p5.ellipse(0, headH / 2f, headH * headScaleY, headH);

        p5.translate(0, headH + neckH);

        float chestH = torsoTotalH * 0.4f;
        float absH = torsoTotalH * 0.3f;
        float lowerTorsoH = torsoTotalH * 0.3f;

        float armOffset = (shoulderW/1.3f) - armInset;

        drawArmSegments(p5, -armOffset, torsoTotalH, legsTotalH);
        drawArmSegments(p5, armOffset, torsoTotalH, legsTotalH);

        p5.fill(r, g, b);
        drawTrapezoid(p5, shoulderW, waistW, chestH);
        p5.translate(0, chestH);

        p5.fill(r - 10, g - 10, b - 10);
        drawTrapezoid(p5, waistW, waistW * 0.9f, absH);
        p5.translate(0, absH);

        p5.fill(r, g, b);
        drawTrapezoid(p5, waistW * 0.9f, hipsW, lowerTorsoH);
        p5.translate(0, lowerTorsoH);

        p5.fill(r - 5, g - 5, b - 5);
        drawTrapezoid(p5, hipsW, hipsW * 0.8f, pelvisH);
        p5.translate(0, pelvisH);

        drawLegSegments(p5, -hipsW / 3f, legsTotalH);
        drawLegSegments(p5, hipsW / 3f, legsTotalH);

        p5.popMatrix();
    }


    private void drawTrapezoid(PApplet p5, float wTop, float wBottom, float h) {
        p5.beginShape();
        p5.vertex(-wTop / 2f, 0);
        p5.vertex(wTop / 2f, 0);
        p5.vertex(wBottom / 2f, h);
        p5.vertex(-wBottom / 2f, h);
        p5.endShape(PApplet.CLOSE);
    }

    private void drawArmSegments(PApplet p5, float offsetX, float torsoH, float legH) {
        float armLen = (torsoH + legH * 0.4f) / 2.2f;
        float thickness = width * 0.225f;

        p5.pushMatrix();
        p5.translate(offsetX, (float) 0);


        p5.fill(r, g, b);
        p5.rect(0, armLen / 2f, thickness, armLen, 5);

        p5.fill(r - 30, g - 30, b - 30);
        p5.ellipse(0, 0, thickness * 0.8f, height/14f);

        p5.translate(0, armLen);

        p5.fill(r, g, b);
        p5.rect(0, armLen / 2f, thickness * 0.8f, armLen, 5);

        p5.fill(r - 30, g - 30, b - 30);
        p5.ellipse(0, 0, thickness * 0.7f, height/16f);

        p5.translate(0, armLen);
        p5.fill(r - 15, g - 15, b - 15);
        p5.rect(0, 0, thickness, height/10, 2);

        p5.popMatrix();
    }

    private void drawLegSegments(PApplet p5, float offsetX, float totalLegLen) {
        float footH = height/12f;
        float segmentLen = (totalLegLen - footH) / 2.1f;
        float thickness = width * 0.3f;

        p5.pushMatrix();
        p5.translate(offsetX, (float) 0);

        p5.fill(r, g, b);
        p5.rect(0, segmentLen / 2f, thickness, segmentLen, 5);

        p5.fill(r - 30, g - 30, b - 30);
        p5.ellipse(0, 0, thickness * 0.5f, height/20f);

        p5.translate(0, segmentLen);

        p5.fill(r, g, b);
        p5.rect(0, segmentLen*1.2f / 2f, thickness * 0.8f, segmentLen*1.2f, 5);

        p5.fill(r - 30, g - 30, b - 30);
        p5.ellipse(0, 0, thickness * 0.5f, height/16f);

        p5.translate(0, segmentLen - footH / 2f);
        p5.fill(r - 20, g - 20, b - 20);
        p5.rect(0, height/10, thickness * 1.05f, footH, 2);

        p5.popMatrix();
    }

}
