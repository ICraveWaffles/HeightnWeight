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

    public boolean mouseOverButton(PApplet p5){
        boolean over = p5.dist(this.x, this.y, p5.mouseX, p5.mouseY) <= this.d/2;
        if (bText.contains("LINK")&&over){
            if (bText.contains("ES")){
                copyLink("Insert Spanish Link Here");
            } else if (bText.contains("EN")) {
                copyLink("Insert English Link Here");
            } else if (bText.contains("RICK")){
                copyLink("https://www.youtube.com/watch?v=iik25wqIuFo&list=RDiik25wqIuFo&start_radio=1");
            }
        }

        if (over && enabled && p5.mousePressed && !lastPress) {
            Sounds.emit(0);
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