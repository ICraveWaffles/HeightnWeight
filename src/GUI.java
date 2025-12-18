import processing.core.PApplet;
import java.util.ArrayList;

public class GUI {

    rButton plog1, plog2;
    rButton signup;
    rButton login;
    rButton m1, m2, m3;
    cButton q1, q2, q3;

    cButton[] navigation = new cButton[4];
    cButton nav1, nav2, nav3, nav4;
    public ArrayList<rButton> scenes = new ArrayList<>();
    rButton exit;

    cButton sced1, sced2, sced3, sced4, sced5;
    rButton rsced1, rsced2;

    TextField tfsignup1, tfsignup2, tfsignup3, tfsignup4;
    TextField tflogin1, tflogin2;
    TextField scName;
    TextField tfName, tfHeight, tfWeight, tfBMI, tfWidth, tfBHRatio, tfAge, tfRed, tfGreen, tfBlue;
    TextField[] tfsced = new TextField[10];




    Slider slZero, slHeight, slWeight, slBMI, slWidth,slBHRatio,slAge, slRed, slGreen, slBlue;
    Slider slSced[] = new Slider[10];

    int page;
    boolean gridon;

    public enum SCREEN { PRELOGIN, LOGIN, SIGNUP, MAIN, QNA, SCENESELECTOR, SCENEEDITOR, OCVIEWER }

    public SCREEN currentScreen;

    public GUI(PApplet p5) {
        currentScreen = SCREEN.PRELOGIN;
        Colors.instanceColors(p5);

        plog1 = new rButton(p5, "Iniciar sesión", 640, 440, 600, 70, 4, 7, 7);
        plog2 = new rButton(p5, "No tienes cuenta? Crear cuenta", 640, 540, 600, 70, 4, 7, 7);

        login = new rButton(p5, "INICIAR SESIÓN",640, 435, 300, 100, 3, 7,  7);
        signup = new rButton(p5, "CREAR CUENTA",640, 565, 300, 100, 3, 7,  7);

        m1 = new rButton(p5, "Mis escenas",640, 340, 640, 60, 3, 7,7 );
        m2 = new rButton(p5, "Mis OCs",640, 440, 640, 60, 3, 7,7 );
        m3 = new rButton(p5, "Salir",640, 540, 640, 60, 3, 7,7 );
        q1 = new cButton(p5, "?", 30, 30, 50, 3, 7,7);

        q2 = new cButton(p5, "<", 50, 360, 50, 3, 7,7);
        q3 = new cButton(p5, ">", 1230, 370, 50, 3, 7,7);

        nav1 = new cButton(p5, "<<",550, 30, 50, 3, 7, 7);
        nav2 = new cButton(p5, "<",610, 30, 50, 3, 7, 7);
        nav3 = new cButton(p5, ">",670, 30, 50, 3, 7, 7);
        nav4 = new cButton(p5, ">>",730, 30, 50, 3, 7, 7);

        tfsignup1 = new TextField(p5, "Nombre de usuario", 640, 120, 540, 60, false);
        tfsignup2 = new TextField(p5, "Correo electrónico", 640, 200, 540, 60, false);
        tfsignup3 = new TextField(p5, "Contraseña (primera vez)", 640, 360, 540, 60, false);
        tfsignup4 = new TextField(p5, "Contraseña (segunda vez)", 640, 440, 540, 60, false);

        tflogin1 = new TextField(p5, "Nombre de usuario", 640, 230, 540, 60, false);
        tflogin2 = new TextField(p5, "Contraseña", 640, 310, 540, 60, false);

        tfName = new TextField(p5, "Nombre", 150, 80, 250, 30, false);
        tfHeight = new TextField(p5, "Altura", 220, 130, 110, 20, true);
        tfWeight = new TextField(p5, "Peso", 220, 200, 110, 20, true);
        tfBMI = new TextField(p5, "IMC", 220, 250, 110, 20, true);
        tfWidth = new TextField(p5, "Anchura", 220, 320, 110, 20, true);
        tfBHRatio = new TextField(p5, "Proporción C/C", 220, 400, 110, 20, true);
        tfAge = new TextField(p5, "Edad estimada", 220, 460, 110, 20, true);
        tfRed = new TextField(p5, "R", 220, 550, 110, 20, true);
        tfGreen = new TextField(p5, "G", 220, 610, 110, 20, true);
        tfBlue = new TextField(p5, "B", 220, 670, 110, 20, true);

        slHeight = new Slider (p5, "Altura(m)", 150,160, 250, 8, 0.01f, 10 , 1.83f);
        slWeight = new Slider (p5, "Peso", 150,220, 250, 8, 0.01f, 25000 , 83.7f);
        slBMI = new Slider (p5, "IMC", 150,280, 250, 8, 1, 250 , 25);
        slWidth = new Slider (p5, "Anchura", 150,340, 250, 8, 2, 10 , 5);
        slBHRatio = new Slider (p5, "Ratio C/C", 150,430, 250, 8, 0.125f, 0.25f , 0.15f);
        slAge = new Slider (p5, "Edad", 150,490, 250, 8, 0, 80 , 20);
        slRed = new Slider (p5, "R", 150,580, 250, 8, 0, 255 , 127);
        slGreen = new Slider (p5, "G", 150,640, 250, 8, 0, 255 , 127);
        slBlue = new Slider (p5, "B", 150,700, 250, 8, 0, 255 , 127);

        int buttonIndex = 0;
        for (int h = 0; h < 30; h++) {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 5; i++) {
                    int x = i * 250 + 120;
                    int y = j * 212 + 200;
                    scenes.add(buttonIndex,new rButton(p5, "Sin título", x, y, 200, 180, 4, 7, 6));
                    if (buttonIndex !=0) scenes.get(buttonIndex).state = STATE.NULL;
                    if (buttonIndex == 1) scenes.get(buttonIndex).state = STATE.PLUS;
                    buttonIndex++;
                }
            }
        }

        navigation[0] = nav1; navigation[1] = nav2; navigation[2] = nav3; navigation[3] = nav4;

        tfsced[0] = tfName; tfsced[1] = tfHeight; tfsced[2] = tfWeight; tfsced[3] = tfBMI; tfsced[4] = tfWidth;
        tfsced[5] = tfBHRatio; tfsced[6] = tfAge; tfsced[7] = tfRed; tfsced[8] = tfGreen; tfsced[9] = tfBlue;


        slSced[1]=slHeight; slSced[2]=slWeight; slSced[3]=slBMI; slSced[4]=slWidth;
        slSced[5]=slBHRatio; slSced[6]=slAge; slSced[7]=slRed; slSced[8]=slGreen; slSced[9]=slBlue;

        scName = new TextField (p5, null, 494, 34, 260, 50, false);
        rsced1 = new rButton (p5, "Rejilla", 978, 34, 200, 60,3,7,7);
        rsced2 = new rButton (p5, "Pantallazo", 776, 34, 200, 60,3,7,7);

        sced1 = new cButton(p5,"+",30, 28,50, 3,7,7);
        sced2 = new cButton(p5,"*",90, 28,50, 3,7,7);
        sced3 = new cButton(p5,"<",150, 28, 50, 3,7,7);
        sced4 = new cButton(p5,">",210, 28, 50, 3,7,7);
        sced5 = new cButton(p5,"X",270, 28, 50, 3,7,7);

        exit = new rButton(p5, "SALIR", 1178, 34, 196, 60, 3, 7,7 );

        page = 0; gridon = true;
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

        drawTextField(p5, 230, "Nombre de usuario / Correo electrónico");
        drawTextField(p5, 310, "Contraseña");

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

        for (int i = (page*15); i < (page+1)*15; i++){
            if (scenes.get(i).state == STATE.NORM)  scenes.get(i).display(p5);
            if (scenes.get(i).state == STATE.PLUS)  scenes.get(i).display(p5);
        }

        for (int i = 0; i < navigation.length; i++){
            navigation[i].display(p5);
        }
        exit.display(p5);

        p5.popStyle();
    }

    public void drawSCENEEDITOR(PApplet p5, Scene scene){
        p5.pushStyle();

        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.textFont(Fonts.getThisFont(1));

        p5.background(Colors.getThisColor(1));

        scName.display(p5);
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
        if (scene.nObjects != 0) {
            if (scene.sel != Scene.scInstance.OCSELECT) {
                if (scene.stands[scene.currentObject] instanceof OC) {
                    for (int i = 0; i < 10; i++) {
                        tfsced[i].display(p5);
                    }
                    for (int i = 5; i < 10; i++) {
                        if (slSced[i] != null) {
                            slSced[i].display(p5);
                        }
                    }
                    slSced[1].display(p5);
                    slSced[3].display(p5);
                    p5.text("Peso(kg): ", 25, 200);
                    p5.text("Ancho(m): ", 25, 320);
                }
            }
        }

        p5.fill(Colors.getThisColor(7));
        p5.rect(354, 114, 920, 600);

        if (gridon) {
            p5.strokeWeight(2);

            for (int i = 354; i <= 1274; i += 20) {
                p5.stroke(Colors.getThisColor(6));
                p5.line(i, 114, i, 714);
                p5.stroke(Colors.getThisColor(7));
                p5.line(i, 94, i, 104);
            }
            for (int j = 114; j <= 720; j += 20) {
                p5.stroke(Colors.getThisColor(6));
                p5.line(354, j, 1274, j);
                p5.stroke(Colors.getThisColor(7));
                p5.line(334, j, 344, j);
            }
        }

        p5.fill(Colors.getThisColor(6));


        //p5.text("Name: []",10, 80 );
        // p5.text("Height: []",10, 130);
        //p5.text("0.1 <===O===> 20",10, 155);
        // p5.text("0.1 <===O===> 20",10, 215);
        //p5.text("IMC: ",10, 250);
        //p5.text("0.1 <===O===> 20",10, 275);
        /*p5.text("0.1 <===O===> 20",10, 335);

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

        */

        for (int i = 0; i < scene.nObjects;i++){
            scene.stands[i].display(p5);
        }

        float fase = 255 *p5.sin(p5.frameCount*0.1f);

        if (scene.nObjects>0) {
            p5.noFill();
            p5.stroke(255, 0, 0, fase);
            p5.strokeWeight(2);
            p5.rectMode(p5.CORNER);

            if (scene.stands[scene.currentObject] != null && !(scene.stands[scene.currentObject] instanceof OC)) {
                p5.rect(scene.stands[scene.currentObject].x - 5,
                        scene.stands[scene.currentObject].y - 5,
                        scene.stands[scene.currentObject].width + 10,
                        scene.stands[scene.currentObject].height + 10);
            } else if (scene.stands[scene.currentObject] instanceof OC){
                OC oc = (OC)scene.stands[scene.currentObject];
                if (scene.stands[scene.currentObject].width > scene.stands[scene.currentObject].height*oc.bhratio){
                    p5.ellipse(oc.x+oc.width / 2, oc.y + (oc.height*oc.bhratio)/2, oc.height*oc.bhratio+5, oc.height*oc.bhratio+5);
                } else {
                    p5.ellipse(oc.x+oc.width / 2, oc.y + (oc.height*oc.bhratio)/2, oc.width+5, oc.height*oc.bhratio+5);
                }
            }
        }

        p5.popStyle();
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