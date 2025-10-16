import processing.core.PApplet;

public class Colors {

    private static int[] colors;

    public static void instanceColors(PApplet p5) {
        colors = new int[9];
        colors[0] = p5.color(0xFF414141);
        colors[1] = p5.color(0xFF534559);
        colors[2] = p5.color(0xFFD9D9D9);
        colors[3] = p5.color(0xFF440D6E);
        colors[4] = p5.color(0xFF7D67E3);
        colors[5] = p5.color(0xFFEED6FF);
        colors[6] = p5.color(0xFF000000);
        colors[7] = p5.color(0xFFFFFFFF);
        colors[8] = p5.color(0xFFFF0000);
    }

    public static int getThisColor(int i) {
        if (colors == null) return 0xFFFFFFFF;
        return colors[i];
    }
}
