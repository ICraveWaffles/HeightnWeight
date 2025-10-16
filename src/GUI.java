import processing.core.PApplet;
import processing.core.PFont;

public class GUI {

    Button plog1, plog2;



    public enum SCREEN { PRELOGIN, LOGIN, SIGNUP, MAIN, QNA, SCENESELECTOR, SCENEEDITOR, OCVIEWER }

    public SCREEN currentScreen;

    public GUI(PApplet p5) {
        currentScreen = SCREEN.PRELOGIN;
        Colors.instanceColors(p5);

        plog1 = new Button(p5, "Iniciar sesión", 640, 440, 600, 70, 3, 7, 6);
        plog2 = new Button(p5, "No tienes cuenta? Crear cuenta", 640, 540, 600, 70, 3, 7, 6);
    }

    public void drawLogo(PApplet p5, int y) {
        p5.pushStyle();

        p5.noStroke();
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.textFont(Fonts.getThisFont(0));
        p5.fill(Colors.getThisColor(7));
        p5.circle(640, y, 170);

        p5.fill(Colors.getThisColor(6));
        p5.text("OCBox", 640, y);

        p5.popStyle();
    }

    public void drawStartButton(PApplet p5, float x, float y, String s) {

        p5.pushStyle();

        p5.noStroke();
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);

        p5.textFont(Fonts.getThisFont(1));
        p5.fill(Colors.getThisColor(4));

        p5.rect(x, y, 600, 70, 5);
        p5.fill(Colors.getThisColor(7));
        p5.text(s, x, y);

        p5.popStyle();
    }

    public void drawDarkButton(PApplet p5, int x, int y, int w, int h, String s){
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.textFont(Fonts.getThisFont(0));
        p5.stroke(Colors.getThisColor(7));
        p5.strokeWeight(4);
        p5.fill(Colors.getThisColor(3));

        p5.rect(x, y, w, h, 10);
        p5.fill(Colors.getThisColor(7));
        p5.text(s, x, y);

        p5.popStyle();
    }

    public void drawExit(PApplet p5){
        drawDarkButton(p5, 1180, 34, 200, 60, "SALIR");
    }

    public void drawTextField(PApplet p5, int y, String s) {
        p5.pushStyle();

        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.textFont(Fonts.getThisFont(2));

        p5.fill(Colors.getThisColor(5));
        p5.stroke(Colors.getThisColor(6));
        p5.strokeWeight(1);


        p5.rect(640, y, 540, 60, 2);
        p5.fill(Colors.getThisColor(6));
        p5.text(s, 640, y);

        p5.popStyle();
    }

    public void drawSymbolButton(PApplet p5, int x, int y, String c) {
        p5.pushStyle();

        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.textFont(Fonts.getThisFont(1));

        p5.fill(Colors.getThisColor(3));
        p5.stroke(Colors.getThisColor(7));
        p5.strokeWeight(3);


        p5.circle(x, y, 50);
        p5.fill(Colors.getThisColor(7));
        p5.text(c, x, y);

        p5.popStyle();
    }

    public void drawNavigation(PApplet p5){
        drawSymbolButton(p5, 550, 30, "<<<");
        drawSymbolButton(p5, 610, 30, "<");
        drawSymbolButton(p5, 670, 30, ">");
        drawSymbolButton(p5, 730, 30, ">>>");

    }



    public void drawPRELOGIN(PApplet p5) {

        p5.pushStyle();

        p5.background(Colors.getThisColor(2));
        drawLogo(p5, 260);
        plog1.display(p5);
        plog2.display(p5);

        p5.popStyle();

    }

    public void drawLOGIN(PApplet p5) {

        p5.pushStyle();

        p5.background(Colors.getThisColor(2));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(Colors.getThisColor(4));
        p5.noStroke();

        p5.rect(640, 360, 600,360, 20);

        drawTextField(p5, 230, "Nombre de usuario / Correo electrónico");
        drawTextField(p5, 310, "Contraseña");

        drawDarkButton(p5, 640, 440, 300, 100, "INICIAR SESIÓN");

        p5.popStyle();

    }

    public void drawSIGNUP(PApplet p5) {
        p5.pushStyle();

        p5.background(Colors.getThisColor(2));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(Colors.getThisColor(4));
        p5.noStroke();

        p5.rect(640, 360, 600,600, 20);

        drawTextField(p5, 120, "Nombre de usuario");
        drawTextField(p5, 200, "Correo electrónico");
        drawTextField(p5, 360, "Contraseña (1)");
        drawTextField(p5, 440, "Contraseña (2)");

        drawDarkButton(p5, 640, 560, 300, 100, "CREAR CUENTA");

        p5.popStyle();
    }

    public void drawMAIN(PApplet p5) {
        p5.pushStyle();

        p5.background(Colors.getThisColor(1));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(Colors.getThisColor(4));
        p5.noStroke();

        drawLogo(p5, 140);

        drawDarkButton(p5, 640, 340, 640, 60, "Mis escenas");
        drawDarkButton(p5, 640, 430, 640, 60, "Mis OCs");
        drawDarkButton(p5, 640, 520, 640, 60, "Salir");

        drawDarkButton(p5, 1120, 50, 240, 80, "Nombre de Usuario");
        drawSymbolButton(p5, 30, 30, "?");

        p5.popStyle();
    }

    public void drawQNA(PApplet p5) {
        p5.pushStyle();

        p5.background(Colors.getThisColor(1));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(Colors.getThisColor(5));
        p5.noStroke();

        p5.rect(640, 360, 1080,560, 20);

        drawTextField(p5, 230, "Lorem ipsum etcetcetc.");
        drawSymbolButton(p5, 50, 360, "<");
        drawSymbolButton(p5, 1230, 360, ">");
        drawExit(p5);

        p5.popStyle();
    }

    public void drawSCENESELECTOR(PApplet p5){

        p5.pushStyle();

        p5.background(Colors.getThisColor(1));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.stroke(Colors.getThisColor(7));
        p5.strokeWeight(4);
        p5.textFont(Fonts.getThisFont(1));

        for (int i = 1; i < 6; i++){
            for (int j = 1; j < 4; j++){
                p5.fill(Colors.getThisColor(5));
                p5.rect((i-1)*250 + 120,(j-1)*212 + 188, 200, 180, 20 );
                p5.fill(Colors.getThisColor(6));
                p5.text("Escena "+(i+(j-1)*5), (i-1)*250+120, (j-1)*212+200);
            }
        }
        drawNavigation(p5);
        drawExit(p5);

        p5.popStyle();
    }

    public void drawSCENEEDITOR(PApplet p5){
        p5.pushStyle();

        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.textFont(Fonts.getThisFont(1));

        p5.background(Colors.getThisColor(1));
        drawDarkButton(p5, 978, 34, 200, 60, "PANTALLAZO");
        drawDarkButton(p5, 776, 34, 200, 60, "REJILLA:ON");
        drawExit(p5);

        p5.fill(Colors.getThisColor(5));
        p5.strokeWeight(4);
        p5.stroke(Colors.getThisColor(7));
        p5.rect(6, 60, 292, 652, 20);


        drawSymbolButton(p5, 30, 26, "+");
        drawSymbolButton(p5, 90, 26, "*");
        drawSymbolButton(p5, 150, 26, "<");
        drawSymbolButton(p5, 210, 26, ">");
        drawSymbolButton(p5, 270, 26, "x");

        p5.fill(Colors.getThisColor(7));
        p5.rect(354, 114, 920, 600);
        p5.strokeWeight(2);

        for (int i = 354; i <= 1274;i+=20){
            p5.stroke(Colors.getThisColor(6));
            p5.line(i, 114, i, 714);
            p5.stroke(Colors.getThisColor(7));
            p5.line(i, 94, i,104 );
        }
        for (int j = 114; j <= 720;j+=20){
            p5.stroke(Colors.getThisColor(6));
            p5.line(354, j, 1274, j);
            p5.stroke(Colors.getThisColor(7));
            p5.line(334, j, 344, j);
        }

        p5.fill(Colors.getThisColor(6));
        p5.text("Name: []",10, 80 );
        p5.text("Height: []",10, 130);
        p5.text("0.1 <===O===> 20",10, 155);
        p5.text("Weight: []",10, 190);
        p5.text("0.1 <===O===> 20",10, 215);
        p5.text("IMC: []",10, 250);
        p5.text("0.1 <===O===> 20",10, 275);
        p5.text("Width: []",10, 310);
        p5.text("0.1 <===O===> 20",10, 335);

        p5.text("Head/Body Ratio: []",10, 400);
        p5.text("0.1 <===O===> 20",10, 425);
        p5.text("Estimated age: []",10, 460);
        p5.text("0.1 <===O===> 20",10, 485);

        p5.text("R: []", 10, 550);
        p5.text("0 <===O===> 255", 10, 575);
        p5.text("G: []", 10, 610);
        p5.text("0 <===O===> 255", 10, 635);
        p5.text("B: []", 10, 670);
        p5.text("0 <===O===> 255", 10, 695);



        p5.popStyle();
    }

    public void drawOCVIEWER(PApplet p5){
        p5.pushStyle();

        p5.background(Colors.getThisColor(1));

        p5.popStyle();
    }

}
