import processing.core.PApplet;
import static processing.core.PConstants.BACKSPACE;

public class TextField {

    public int x, y, h, w;
    public int bgColor, fgColor, selectedColor, borderColor;
    public int borderWeight = 1;
    public String token;
    public String text = "";
    public String trueText;
    public int textSize = 30;
    public boolean selected = false;
    public boolean enabled = true;
    public boolean numOnly;

    /**
     * Constructor de TextField.
     * @param p5 PApplet necesario para la creación del campo de texto.
     * @param token tóken del campo de texto.
     * @param x posición x del campo de texto.
     * @param y posición y del campo de texto.
     * @param w anchura del campo de texto.
     * @param h altura del campo de texto.
     * @param numOnly si sólo acepta valores numéricos.
     */
    public TextField(PApplet p5, String token, int x, int y, int w, int h, boolean numOnly) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.token = token;
        this.trueText = Languages.translate(token, 1);
        this.text = trueText;
        this.bgColor = p5.color(192, 255, 255);
        this.fgColor = p5.color(32, 127, 127);
        this.selectedColor = p5.color(255, 255, 255);
        this.borderColor = p5.color(127, 255, 255);
        this.numOnly = numOnly;
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

        if (text.isEmpty() && !selected && trueText != null) {
            p5.text(trueText, x, y);
        } else {
            p5.text(text, x, y);
        }

        p5.popStyle();
    }

    public void keyPressed(char key, int keyCode) {
        if (!enabled || !selected) return;

        if (keyCode == BACKSPACE) {
            removeText();
            Sounds.emit(1);
            return;
        }

        boolean isLetter = (key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z');
        boolean isNumber = (key >= '0' && key <= '9');
        boolean isSymbol = "!@#$%^&*()-_+=[]{};:'\"\\|,.<>/?`~·".indexOf(key) >= 0;

        if (numOnly) {
            if (isNumber || key == '.' || key == ',') addText(key);
        } else {
            if (isLetter || isNumber || isSymbol || key == ' ') addText(key);
        }

        if (keyCode != 108) {
            if (keyCode == 128) addText('[');
            if (keyCode == 129) addText('{');
        }
    }

    public void addText(char c) {

        boolean written = false;

        if (numOnly) {

            if (c == '.') c = ',';

            if (!((c >= '0' && c <= '9') || c == ',')) return;

            if (c == ',' && text.contains(",")) return;
        }

        if (numOnly && text.equals("0") && c != ',') {
            text = String.valueOf(c);
            written = true;
        } else if (text.length() < (w / (textSize * 0.6))) {
            text += c;
            written = true;
        }

        if (written) {
            Sounds.emit(0);
        }
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
        return p5.mouseX >= x - w / 2f &&
                p5.mouseX <= x + w / 2f &&
                p5.mouseY >= y - h / 2f &&
                p5.mouseY <= y + h / 2f;
    }

    public void isPressed(PApplet p5) {

        if (!enabled) {
            selected = false;
            return;
        }

        selected = mouseOverTextField(p5);

        if (text.equals(trueText)) {
            this.text = "";
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
