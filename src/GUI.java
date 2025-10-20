import processing.core.PApplet;

public class GUI {

    rButton plog1, plog2;
    rButton signup;
    rButton login;
    rButton m1, m2, m3;
    cButton q1, q2, q3;

    cButton[] navigation = new cButton[4];
    cButton nav1, nav2, nav3, nav4;
    rButton[] scenes = new rButton[150];
    rButton exit;

    cButton sced1, sced2, sced3, sced4, sced5;
    rButton rsced1, rsced2;

    TextField tfsignup1, tfsignup2, tfsignup3, tfsignup4;
    TextField tflogin1, tflogin2;

    TextField tfName, tfHeight, tfWeight, tfBMI, tfWidth, tfBHRatio, tfAge, tfRed,tfGreen, tfBlue;
    TextField[] tfSced = new TextField[10];
    int page;

    public enum SCREEN { PRELOGIN, LOGIN, SIGNUP, MAIN, QNA, SCENESELECTOR, SCENEEDITOR, OCVIEWER }

    public SCREEN currentScreen;

    public GUI(PApplet p5) {
        currentScreen = SCREEN.PRELOGIN;
        Colors.instanceColors(p5);

        plog1 = new rButton(p5, "Iniciar sesión", 640, 440, 600, 70, 4, 7, 7);
        plog2 = new rButton(p5, "No tienes cuenta? Crear cuenta", 640, 540, 600, 70, 4, 7, 7);

        login = new rButton(p5, "INICIAR SESIÓN",640, 360+75, 300, 100, 3, 7,  7);
        signup = new rButton(p5, "CREAR CUENTA",640, 440+125, 300, 100, 3, 7,  7);

        m1 = new rButton(p5, "Mis escenas",640, 340, 640, 60, 3, 7,7 );
        m2 = new rButton(p5, "Mis OCs",640, 440, 640, 60, 3, 7,7 );
        m3 = new rButton(p5, "Salir",640, 540, 640, 60, 3, 7,7 );
        q1 = new cButton(p5, "?", 30, 30, 50, 3, 7,7);

        q2 = new cButton(p5, "<", 50, 360, 50, 3, 7,7);
        q3 = new cButton(p5, ">", 1230, 370, 50, 3, 7,7);

        nav1 = new cButton(p5, "<<",550, 55, 50, 3, 7, 7);
        nav2 = new cButton(p5, "<",610, 55, 50, 3, 7, 7);
        nav3 = new cButton(p5, ">",670, 55, 50, 3, 7, 7);
        nav4 = new cButton(p5, ">>",730, 55, 50, 3, 7, 7);

        tfsignup1 = new TextField(p5, "Nombre de usuario",640, 120, 540, 60);
        tfsignup2 = new TextField(p5, "Correo electrónico",640, 200, 540, 60);
        tfsignup3 = new TextField(p5, "Contraseña (primera vez)",640, 360, 540, 60);
        tfsignup4 = new TextField(p5, "Contraseña (segunda vez)",640, 440, 540, 60);

        tflogin1 =  new TextField(p5, "Nombre de usuario",640, 230, 540, 60);
        tflogin2 =  new TextField(p5, "Contraseña",640, 310, 540, 60);

        tfName = new TextField(p5, "Name", 150, 80, 180, 40);
        tfHeight = new TextField(p5, "Height", 150, 130, 180, 40);
        tfWeight = new TextField(p5, "Weight", 150, 190, 180, 40);
        tfBMI = new TextField(p5, "IMC", 150, 250, 180, 40);
        tfWidth = new TextField(p5, "Width", 150, 310, 180, 40);
        tfBHRatio = new TextField(p5, "Head/Body Ratio", 150, 400, 180, 40);
        tfAge = new TextField(p5, "Estimated age", 150, 460, 180, 40);
        tfRed = new TextField(p5, "R", 150, 550, 180, 40);
        tfGreen = new TextField(p5, "G", 150, 610, 180, 40);
        tfBlue = new TextField(p5, "B", 150, 670, 180, 40);

        int buttonIndex = 0;
        for (int h = 0; h < 10; h++) {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 5; i++) {
                    int x = i * 250 + 120;
                    int y = j * 212 + 200;
                    scenes[buttonIndex] = new rButton(p5, "Escena " + (buttonIndex + 1), x, y, 200, 180, 4, 7, 6);
                    buttonIndex++;
                }
            }
        }

        navigation[0] = nav1;
        navigation[1] = nav2;
        navigation[2] = nav3;
        navigation[3] = nav4;

        tfSced[1] = tfHeight; tfSced[2] = tfWeight; tfSced[3] = tfBMI; tfSced[4] = tfWidth; tfSced[5] = tfBHRatio;
        tfSced[6] = tfAge; tfSced[7] = tfRed; tfSced[8] = tfGreen; tfSced[9] = tfBlue; tfSced[0] = tfName;


        rsced1 = new rButton (p5, "Rejilla", 978, 34, 200, 60,3,7,7);
        rsced2 = new rButton (p5, "Pantallazo", 776, 34, 200, 60,3,7,7);

        sced1 = new cButton(p5,"+",30, 28,50, 3,7,7);
        sced2 = new cButton(p5,"*",90, 28,50, 3,7,7);
        sced3 = new cButton(p5,"<",150, 28, 50, 3,7,7);
        sced4 = new cButton(p5,">",210, 28, 50, 3,7,7);
        sced5 = new cButton(p5,"X",270, 28, 50, 3,7,7);

        exit = new rButton(p5, "SALIR", 1178, 34, 200, 60, 3, 7,7 );

        page = 0;
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
        exit.display(p5);
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
        tflogin1.display(p5);
        tflogin2.display(p5);
        login.display(p5);
        exit.display(p5);
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
        tfsignup1.display(p5);
        tfsignup2.display(p5);
        tfsignup3.display(p5);
        tfsignup4.display(p5);
        signup.display(p5);
        exit.display(p5);
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
        m1.display(p5);
        m2.display(p5);
        m3.display(p5);
        q1.display(p5);
        drawDarkButton(p5, 1120, 50, 240, 80, "Nombre de Usuario");
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
        q2.display(p5);
        q3.display(p5);
        exit.display(p5);
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
        for (int i = 0+(page*15); i < (page+1)*15; i++){
            scenes[i].display(p5);
        }
        for (int i = 0; i < navigation.length; i++){
            navigation[i].display(p5);
        }
        exit.display(p5);
        p5.popStyle();
    }

    public void drawSCENEEDITOR(PApplet p5){
        p5.pushStyle();
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.textFont(Fonts.getThisFont(1));
        p5.background(Colors.getThisColor(1));
        rsced1.display(p5);
        rsced2.display(p5);
        exit.display(p5);
        p5.fill(Colors.getThisColor(5));
        p5.strokeWeight(4);
        p5.stroke(Colors.getThisColor(7));
        p5.rect(6, 60, 292, 652, 20);
        sced1.display(p5);
        sced2.display(p5);
        sced3.display(p5);
        sced4.display(p5);
        sced5.display(p5);
        p5.fill(Colors.getThisColor(7));
        p5.rect(354, 114, 920, 600);
        p5.strokeWeight(2);
        for (int i = 354; i <= 1274; i += 20){
            p5.stroke(Colors.getThisColor(6));
            p5.line(i, 114, i, 714);
            p5.stroke(Colors.getThisColor(7));
            p5.line(i, 94, i, 104);
        }
        for (int j = 114; j <= 720; j += 20){
            p5.stroke(Colors.getThisColor(6));
            p5.line(354, j, 1274, j);
            p5.stroke(Colors.getThisColor(7));
            p5.line(334, j, 344, j);
        }
        p5.fill(Colors.getThisColor(6));
        p5.text("0.1 <===O===> 20",10, 155);
        p5.text("0.1 <===O===> 20",10, 215);
        p5.text("0.1 <===O===> 20",10, 275);
        p5.text("0.1 <===O===> 20",10, 335);
        p5.text("0.1 <===O===> 20",10, 425);
        p5.text("0.1 <===O===> 20",10, 485);
        p5.text("0 <===O===> 255",10, 575);
        p5.text("0 <===O===> 255",10, 635);
        p5.text("0 <===O===> 255",10, 695);
        for (int i = 0; i < 10; i++){
            tfSced[i].display(p5);
        }
    }


    public void drawOCVIEWER(PApplet p5){
        p5.pushStyle();
        p5.background(Colors.getThisColor(1));
        for (int i = 0; i < navigation.length; i++){
            navigation[i].display(p5);
        }
        exit.display(p5);
        p5.popStyle();
    }
}
