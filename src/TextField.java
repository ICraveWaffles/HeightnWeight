import processing.core.PApplet;
import static processing.core.PConstants.BACKSPACE;


public class TextField {

    int x, y, h, w;
    int bgColor, fgColor, selectedColor, borderColor;
    int borderWeight = 1;
    public String text = "";
    public String trueText;
    int textSize = 20;
    boolean selected = false;
    boolean enabled = true;
    boolean intOnly = false;

    public TextField(PApplet p5, String text, int x, int y, int w, int h, boolean intOnly) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.trueText = text;
        this.bgColor = p5.color(192, 255, 255);
        this.fgColor = p5.color(32, 127, 127);
        this.selectedColor = p5.color(255, 255, 255);
        this.borderColor = p5.color(127, 255, 255);
        this.borderWeight = 1;
        this.intOnly = intOnly;
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        p5.rectMode(p5.CENTER);
        if (!enabled) p5.fill(220);
        else if (selected) p5.fill(selectedColor);
        else p5.fill(bgColor);
        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rect(x, y, w, h, 5);
        p5.fill(fgColor);
        if (this.text != null){
            p5.textSize(textSize);
            p5.textAlign(p5.CENTER, p5.CENTER);
            p5.text(text, x, y);
        }
        p5.popStyle();
    }

    public void keyPressed(char key, int keyCode) {
        if (!enabled || !selected) return;
        if (keyCode == BACKSPACE) {
            removeText();
            return;
        }
        if (keyCode == 32 && !intOnly) {
            addText(' ');
            return;
        }
        boolean isLetter = (key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z');
        boolean isNumber = (key >= '0' && key <= '9');
        boolean isSymbol = "!@#$%^&*()-_+=[]{};:'\"\\|,.<>/?`~·".indexOf(key) >= 0;
        if (intOnly) {
            if (isNumber || key == '.' || key == ',') addText(key);
        } else {
            if (isLetter || isNumber || isSymbol) addText(key);
        }
        if (keyCode != 108) {
            if (keyCode == 128) addText('[');
            if (keyCode == 129) addText('{');
        }
    }

    public void addText(char c) {
        if (c == ',') c = '.';
        if (text.length() < (w / (textSize * 0.6))) text += c;
    }

    public void removeText() {
        if (!text.isEmpty()) text = text.substring(0, text.length() - 1);
    }

    public void removeAllText() {
        text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String t) {
        text = t;
    }

    public boolean mouseOverTextField(PApplet p5) {
            return (p5.mouseX >= x - w / 2f &&
                    p5.mouseX <= x + w / 2f &&
                    p5.mouseY >= y - h / 2f &&
                    p5.mouseY <= y + h / 2f);

    }

    public void isPressed(PApplet p5) {
        if (!enabled) {
            selected = false;
            return;
        }
        if (mouseOverTextField(p5)) {
            selected = true;

            if (this.text.equals(trueText)) {
                this.text = "";
            }
        } else {
            selected = false;
            if (this.text.isEmpty() && this.trueText != null) {
                this.text = trueText;
            }
        }
    }


    public void setEnabled(boolean e) {
        enabled = e;
        if (!e) selected = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
