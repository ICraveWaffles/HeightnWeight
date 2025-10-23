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

    public TextField(PApplet p5, String text, int x, int y, int w, int h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.text = text;
        this.trueText = this.text;
        this.bgColor = p5.color(192, 255, 255);
        this.fgColor = p5.color(32, 127, 127);
        this.selectedColor = p5.color(255, 255, 255);
        this.borderColor = p5.color(127, 255, 255);
        this.borderWeight = 1;
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
        p5.textSize(textSize);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(text, x , y);
        p5.popStyle();
    }

    public void keyPressed(char key, int keyCode, boolean intonly) {
        if (!enabled) return;
        if (selected) {
            if (keyCode == (int) BACKSPACE) removeText();
            else if (keyCode == 32 && !intonly) addText(' ');
            else {
                boolean isKeyCapitalLetter = (key >= 'A' && key <= 'Z');
                boolean isKeySmallLetter = (key >= 'a' && key <= 'z');
                boolean isKeyNumber = (key >= '0' && key <= '9');
                String specialSymbols = "!@#$%^&*()-_+=[]{};:'\"\\|,.<>/?`~";

                if (intonly) {
                    if (!isKeyCapitalLetter && !isKeySmallLetter && keyCode != 128 && keyCode!=129 &&!(specialSymbols.indexOf(key) >= 0)||(isKeyNumber || key == '.' || key == ',')) addText(key);
                } else {
                    if (isKeyCapitalLetter||isKeySmallLetter||isKeyNumber||(specialSymbols.indexOf(key) >= 0)) {
                        addText(key);
                    }
                }
                if (keyCode == 128){
                    addText('[');
                }
                if (keyCode == 129){
                    addText('{');
                }

            }
        }
    }

    public void addText(char c) {
        if (this.text.length() < (w / (textSize * 0.6))) this.text += c;
    }

    public void removeText() {
        if (text.length() > 0) text = text.substring(0, text.length() - 1);
    }

    public void removeAllText() {
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public void setText(String t) {
        this.text = t;
    }

    public boolean mouseOverTextField(PApplet p5) {
        return (p5.mouseX >= this.x - this.w / 2f &&
                p5.mouseX <= this.x + this.w / 2f &&
                p5.mouseY >= this.y - this.h / 2f &&
                p5.mouseY <= this.y + this.h / 2f);
    }

    public void isPressed(PApplet p5) {
        if (!enabled) {
            selected = false;
            return;
        }
        if (mouseOverTextField(p5)) {
            selected = true;
            if (this.text.equals(this.trueText)) {this.text = "";}
        } else {
            if (this.text.equals("")) {text = trueText;}
            selected = false;
        }
    }

    public void setEnabled(boolean e) {
        this.enabled = e;
        if (!e) selected = false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
