import processing.core.PApplet;
import processing.core.PImage;

public class HSBColorPicker {

    public float x, y, w, h;
    public HueSlider hueSlider;

    public float saturationValue = 0;
    public float brightnessValue = 0;
    public float hueValue;

    public int selectedColor;
    public float xClick, yClick;

    public boolean draggingSlider = false;
    public boolean draggingPicker = false;
    public final float pickerYOffset = 120;

    public PImage pickerImage;
    public boolean pickerDirty = true;

    public HSBColorPicker(PApplet p5, float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.hueSlider = new HueSlider(p5, x, y + h, w, h/4f);

        this.selectedColor = p5.color(255,255,255);

        xClick = x + w;
        yClick = y + pickerYOffset;

        updateColor(p5);
    }

    public void display(PApplet p5) {
        p5.pushStyle();

        hueSlider.display(p5);

        if(pickerDirty){
            updatePickerImage(p5);
        }

        p5.image(pickerImage, x, y + pickerYOffset);

        p5.stroke(brightnessValue < 128 ? 255 : 0);
        p5.line(xClick, yClick-6, xClick, yClick+6);
        p5.line(xClick-6, yClick, xClick+6, yClick);

        p5.popStyle();
    }

    public void displayColors(PApplet p5){

        p5.pushStyle();

        float previewX = x - w - 22.5f;
        float previewY = y + pickerYOffset;
        float rectSize = 90;

        p5.fill(selectedColor);
        p5.rect(previewX, previewY-15, rectSize, rectSize);

        int r = (int)p5.red(selectedColor);
        int g = (int)p5.green(selectedColor);
        int b = (int)p5.blue(selectedColor);

        p5.fill(Colors.getThisColor(7));

        p5.text("R: "+r, previewX+rectSize+15, previewY);
        p5.text("G: "+g, previewX+rectSize+15, previewY+30);
        p5.text("B: "+b, previewX+rectSize+15, previewY+60);

        p5.popStyle();
    }

    private void updatePickerImage(PApplet p5){

        int wInt = (int)w;
        int hInt = (int)h;

        pickerImage = p5.createImage(wInt, hInt, p5.RGB);

        p5.colorMode(p5.HSB,255);

        pickerImage.loadPixels();

        for(int ix = 0; ix < wInt; ix++){

            float bri = p5.map(ix,0,wInt,0,255);

            for(int iy = 0; iy < hInt; iy++){

                float sat = p5.map(iy,0,hInt,0,255);

                pickerImage.pixels[iy*wInt + ix] =
                        p5.color(hueValue, sat, bri);
            }
        }

        pickerImage.updatePixels();
        pickerDirty = false;
    }

    public void updateColor(PApplet p5){

        p5.pushStyle();

        p5.colorMode(p5.HSB,255);

        brightnessValue = p5.map(xClick, x, x+w, 0,255);
        saturationValue = p5.map(yClick, y + pickerYOffset, y + h + pickerYOffset, 0,255);

        selectedColor = p5.color(hueValue, saturationValue, brightnessValue);

        p5.popStyle();
    }

    private boolean isOverPicker(PApplet p5){

        return p5.mouseX >= x &&
                p5.mouseX <= x+w &&
                p5.mouseY >= y + pickerYOffset &&
                p5.mouseY <= y + h + pickerYOffset;
    }

    private boolean isOverSlider(PApplet p5){

        return p5.mouseX >= hueSlider.x &&
                p5.mouseX <= hueSlider.x + hueSlider.w &&
                p5.mouseY >= hueSlider.y &&
                p5.mouseY <= hueSlider.y + hueSlider.h;
    }

    public void mousePressed(PApplet p5){

        if(isOverSlider(p5)){
            draggingSlider = true;
            hueSlider.checkSlider(p5);
            hueValue = hueSlider.getValue();
            pickerDirty = true;
        }
        else if(isOverPicker(p5)){
            draggingPicker = true;
            xClick = p5.mouseX;
            yClick = p5.mouseY;
        }

        updateColor(p5);
    }

    public void mouseDragged(PApplet p5){

        if(draggingSlider){
            hueSlider.checkSlider(p5);
            hueValue = hueSlider.getValue();
            pickerDirty = true;
        }
        else if(draggingPicker){
            xClick = p5.constrain(p5.mouseX, x, x+w);
            yClick = p5.constrain(p5.mouseY, y + pickerYOffset, y + h + pickerYOffset);
        }

        updateColor(p5);
    }

    public void mouseReleased(){
        draggingSlider=false;
        draggingPicker=false;
    }
}