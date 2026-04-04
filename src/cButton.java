import processing.core.PApplet;
import java.awt.*;
import java.awt.datatransfer.*;

public class cButton {

    public float x, y, d;
    public int fillColor, strokeColor;
    public int fillColorOver, fillColorDisabled;

    public String bText;
    public int cText;
    public boolean enabled;
    public boolean lastPress = false;

    /**
     * Constructor de botones redondos.
     * @param text tóken del botón.
     * @param x posición x del botón.
     * @param y posición y del botón.
     * @param d diámetro del botón.
     * @param f color del centro del botón.
     * @param s color del borde del botón.
     * @param t color del texto del botón.
     */
    public cButton(String text, float x, float y, float d, int f, int s, int t){
        this.bText = text;
        this.x = x;
        this.y = y;
        this.d = d;
        this.enabled = true;
        this.fillColor = f;
        this.fillColorOver = f+1;
        this.fillColorDisabled = 0;
        this.strokeColor = s;
        this.cText = t;
    }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        if(!enabled){
            p5.fill(Colors.getThisColor(fillColorDisabled));
        }
        else if(mouseOverButton(p5)){
            p5.fill(Colors.getThisColor(fillColorOver));
        }
        else{
            p5.fill(Colors.getThisColor(fillColor));
        }
        p5.stroke(Colors.getThisColor(strokeColor));
        p5.strokeWeight(2);
        p5.circle(this.x, this.y, this.d);

        if (!this.bText.contains("LINK")) {
            p5.fill(Colors.getThisColor(cText));
            p5.textFont(Fonts.getThisFont(0));
            p5.text(bText, this.x, this.y - 2);
        }
        p5.popStyle();
    }

    /**
     * Método que detecta si el botón está siendo pulsado, y copia un enlace si el texto del botón contiene "LINK".
     * @param p5 PApplet necesario para realizar la acción.
     * @return true si está pulsado, false si no.
     */
    public boolean mouseOverButton(PApplet p5){
        boolean over = p5.dist(this.x, this.y, p5.mouseX, p5.mouseY) <= this.d/2;
        if (over && enabled && p5.mousePressed && !lastPress) {
            Sounds.emit(0);
            if (bText.contains("LINK")){
                if (bText.contains("ES")){
                    copyLink("Insert Spanish Link Here");
                } else if (bText.contains("EN")) {
                    copyLink("Insert English Link Here");
                } else if (bText.contains("RICK")){
                    copyLink("https://www.youtube.com/watch?v=iik25wqIuFo");
                }
            }
        }

        lastPress = p5.mousePressed;
        return over;
    }

    private void copyLink(String txt) {
        StringSelection selection = new StringSelection(txt);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}