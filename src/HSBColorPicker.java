import processing.core.PApplet;

public class HSBColorPicker {

    float x, y, w, h;
    HueSlider hueSlider;
    float saturationValue = 0;
    float brightnessValue = 0;
    float hueValue;

    public int selectedColor;
    float xClick, yClick;

    boolean draggingSlider = false;
    boolean draggingPicker = false;

    public HSBColorPicker(PApplet p5, float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hueSlider = new HueSlider(p5, x, y + h, w, h/4);
        this.selectedColor = p5.color(255, 255, 255);

        xClick = x + w;
        yClick = y + 80;

        updateColor(p5);
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        this.hueSlider.display(p5);

        p5.colorMode(p5.HSB, 255);
        for (float xm = x; xm < x + w; xm += 1.0f) {
            float bri = p5.map(xm, x, x + w, 0, 255);
            for (float ym = y + 80; ym < y + h + 80; ym += 1.0f) {
                float sat = p5.map(ym, y + 80, y + h + 80, 0, 255);
                p5.stroke(hueValue, sat, bri);
                p5.point(xm, ym);
            }
        }

        p5.stroke(brightnessValue < 128 ? 255 : 0);
        p5.line(xClick, yClick - 4, xClick, yClick + 4);
        p5.line(xClick - 4, yClick, xClick + 4, yClick);
        p5.popStyle();
    }

    public void displayColors(PApplet p5){
        p5.pushStyle();
        float previewX = x - w - 15;
        float previewY = y + 80;
        float rectSize = 60;
        p5.fill(selectedColor);
        p5.rect(previewX, previewY, rectSize, rectSize);

        int r = (int) p5.red(selectedColor);
        int g = (int) p5.green(selectedColor);
        int b = (int) p5.blue(selectedColor);

        p5.fill(Colors.getThisColor(7));
        p5.text("R: " + r, previewX + rectSize + 10, previewY);
        p5.text("G: " + g, previewX + rectSize + 10, previewY + 20);
        p5.text("B: " + b, previewX + rectSize + 10, previewY + 40);
        p5.popStyle();
    }

    public void updateColor(PApplet p5) {
        p5.pushStyle();
        p5.colorMode(p5.HSB, 255);
        brightnessValue = p5.map(xClick, x, x + w, 0, 255);
        saturationValue = p5.map(yClick, y + 80, y + h + 80, 0, 255);
        selectedColor = p5.color(hueValue, saturationValue, brightnessValue);
        p5.popStyle();
    }

    private boolean isOverPicker(PApplet p5) {
        return p5.mouseX >= x && p5.mouseX <= x + w && p5.mouseY >= y + 80 && p5.mouseY <= y + h + 80;
    }

    private boolean isOverSlider(PApplet p5) {
        return p5.mouseX >= hueSlider.x && p5.mouseX <= hueSlider.x + hueSlider.w &&
                p5.mouseY >= hueSlider.y && p5.mouseY <= hueSlider.y + hueSlider.h;
    }

    public void mousePressed(PApplet p5) {
        if (isOverSlider(p5)) {
            draggingSlider = true;
            this.hueSlider.checkSlider(p5);
            hueValue = this.hueSlider.getValue();
        } else if (isOverPicker(p5)) {
            draggingPicker = true;
            xClick = p5.mouseX;
            yClick = p5.mouseY;
        }
        updateColor(p5);
    }

    public void mouseDragged(PApplet p5) {
        if (draggingSlider) {
            this.hueSlider.checkSlider(p5);
            hueValue = this.hueSlider.getValue();
        } else if (draggingPicker) {
            xClick = p5.constrain(p5.mouseX, x, x + w);
            yClick = p5.constrain(p5.mouseY, y + 80, y + h + 80);
        }
        updateColor(p5);
    }

    public void mouseReleased() {
        draggingSlider = false;
        draggingPicker = false;
    }
}