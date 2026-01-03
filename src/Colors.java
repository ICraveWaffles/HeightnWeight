import processing.core.PApplet;

public class Colors {

    private static int[] colors;
    private static int[] dayMode;
    private static int[] nightMode;
    private static boolean isNight;

    public static void instanceColors(PApplet p5) {
        colors = new int[9];
        nightMode = new int[9];
        dayMode = new int[9];
        nightMode[0] = p5.color(0xFF414141);
        nightMode[1] = p5.color(0xFF534559);
        nightMode[2] = p5.color(0xFFD9D9D9);
        nightMode[3] = p5.color(0xFF440D6E);
        nightMode[4] = p5.color(0xFF7D67E3);
        nightMode[5] = p5.color(0xFFEED6FF);
        nightMode[6] = p5.color(0xFF000000);
        nightMode[7] = p5.color(0xFFFFFFFF);
        nightMode[8] = p5.color(0xFFFF0000);

        dayMode[0] = p5.color(0xFFBEBEBE);
        dayMode[1] = p5.color(0xFFACBAA6);
        dayMode[2] = p5.color(0xFF262626);
        dayMode[3] = p5.color(0xFFBBF291);
        dayMode[4] = p5.color(0xFF82981C);
        dayMode[5] = p5.color(0xFF112900);
        dayMode[6] = p5.color(0xFFFFFFFF);
        dayMode[7] = p5.color(0xFF000000);
        dayMode[8] = p5.color(0xFFFF0000);

        colors = nightMode;
    }

    public static int getThisColor(int i) {
        if (colors == null) return 0xFFFFFFFF;
        return colors[i];
    }
    public static void switchMode(){
        if (isNight) colors = dayMode;
        else colors = nightMode;
        isNight = !isNight;
    }
}
