

public class OC extends Stand{

    float weight;
    float BMI;

    float bhratio;
    float age;

    byte r;
    byte g;
    byte b;

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
}
