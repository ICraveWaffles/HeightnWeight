import processing.core.PApplet;
import java.util.ArrayList;

enum LANG {ENG, ESP}

public class GUI {

    public LANG lang;
    rButton plog1, plog2;
    rButton signup;
    rButton login;
    rButton m1, m2, m3;
    cButton q1, q2, q3, s1;
    rSwitch sLang, sCol;


    cButton[] navigation = new cButton[4];
    cButton nav1, nav2, nav3, nav4;
    public ArrayList<rButton> scenes = new ArrayList<>();
    rButton exit;
    rButton deleteEverything;

    cButton sced1, sced2, sced3, sced4, sced5;
    rButton rsced1, rsced2;

    TextField tfsignup1, tfsignup2, tfsignup3, tfsignup4;
    TextField tflogin1, tflogin2;
    TextField scName;
    TextField tfName, tfHeight, tfWeight, tfBMI, tfWidth, tfBHRatio, tfAge, tfRed, tfGreen, tfBlue;
    TextField[] tfsced = new TextField[10];
    TextField tfSelectSearch, tfInfoSearch;


    Slider slVolume, slHeight, slWeight, slBMI, slWidth,slBHRatio,slAge, slRed, slGreen, slBlue;
    Slider slSced[] = new Slider[10];

    int page;
    boolean gridon;

    public enum SCREEN { PRELOGIN, LOGIN, SIGNUP, MAIN, QNA, SETTINGS,  SCENESELECTOR, SCENEEDITOR, OCVIEWER }

    public SCREEN currentScreen;

    public GUI(PApplet p5) {
        currentScreen = SCREEN.PRELOGIN;
        lang = LANG.ESP;
        Colors.instanceColors(p5);

        plog1 = new rButton(p5, "PLOG1", 640, 440, 600, 70, 4, 7, 7);
        plog2 = new rButton(p5, "PLOG2", 640, 540, 600, 70, 4, 7, 7);

        login = new rButton(p5, "LOGIN",640, 435, 300, 100, 3, 7,  7);
        signup = new rButton(p5, "SIGNUP",640, 565, 300, 100, 3, 7,  7);

        m1 = new rButton(p5, "M1",640, 340, 640, 60, 3, 7,7 );
        m2 = new rButton(p5, "M2",640, 440, 640, 60, 3, 7,7 );
        m3 = new rButton(p5, "M3",640, 540, 640, 60, 3, 7,7 );
        q1 = new cButton(p5, "?", 30, 30, 50, 3, 7,7);
        s1 = new cButton(p5, "O", 30, 90, 50, 3,7,7);

        sLang = new rSwitch(p5, "ES", "EN", 840, 360, 100,40,4, 7,7);
        sCol = new rSwitch(p5, "N", "D", 840, 420, 100,40,4, 7,7);
        deleteEverything = new rButton(p5, "RESET", 640, 490, 500, 60, 7,6,6);

        q2 = new cButton(p5, "<", 50, 360, 50, 3, 7,7);
        q3 = new cButton(p5, ">", 1230, 370, 50, 3, 7,7);

        nav1 = new cButton(p5, "<<",550, 30, 50, 3, 7, 7);
        nav2 = new cButton(p5, "<",610, 30, 50, 3, 7, 7);
        nav3 = new cButton(p5, ">",670, 30, 50, 3, 7, 7);
        nav4 = new cButton(p5, ">>",730, 30, 50, 3, 7, 7);

        tfsignup1 = new TextField(p5, "USERNAME", 640, 120, 540, 60, false);
        tfsignup2 = new TextField(p5, "EMAIL", 640, 200, 540, 60, false);
        tfsignup3 = new TextField(p5, "PASSWORD (1)", 640, 360, 540, 60, false);
        tfsignup4 = new TextField(p5, "PASSWORD (2)", 640, 440, 540, 60, false);

        tflogin1 = new TextField(p5, "USERNAME", 640, 230, 540, 60, false);
        tflogin2 = new TextField(p5, "PASSWORD", 640, 310, 540, 60, false);

        tfSelectSearch = new TextField (p5, "", 152, 97, 280, 50, true);
        tfInfoSearch = new TextField (p5, "", 150, 32, 280, 50, false);
        tfName = new TextField(p5, "SOUND", 152, 80, 250, 30, false);
        tfHeight = new TextField(p5, "HEIGHT", 222, 130, 110, 20, true);
        tfWeight = new TextField(p5, "WEIGHT", 222, 200, 110, 20, true);
        tfBMI = new TextField(p5, "BMI", 222, 250, 110, 20, true);
        tfWidth = new TextField(p5, "WIDTH", 222, 320, 110, 20, true);
        tfBHRatio = new TextField(p5, "BHRATIO", 222, 400, 110, 20, true);
        tfAge = new TextField(p5, "AGE", 222, 460, 110, 20, true);
        tfRed = new TextField(p5, "R", 222, 550, 110, 20, true);
        tfGreen = new TextField(p5, "G", 222, 610, 110, 20, true);
        tfBlue = new TextField(p5, "B", 222, 670, 110, 20, true);

        slVolume = new Slider(p5, "SOUND", 640, 300, 500, 12, 0, 100, 50, false);
        slHeight = new Slider (p5, "HEIGHT", 150,160, 250, 8, 0.01f, 10 , 1.83f);
        slWeight = new Slider (p5, "WEIGHT", 150,220, 250, 8, 0.01f, 25000 , 83.7f);
        slBMI = new Slider (p5, "BMI", 150,280, 250, 8, 1, 250 , 25);
        slWidth = new Slider (p5, "WIDTH", 150,340, 250, 8, 2, 10 , 5);
        slBHRatio = new Slider (p5, "BHRATIO", 150,430, 250, 8, 0.125f, 0.25f , 0.15f);
        slAge = new Slider (p5, "AGE", 150,490, 250, 8, 0, 80 , 20);
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
        rsced1 = new rButton (p5, "GRID", 978, 34, 200, 60,3,7,7);
        rsced2 = new rButton (p5, "SCREENSHOT", 776, 34, 200, 60,3,7,7);

        sced1 = new cButton(p5,"+",30, 28,50, 3,7,7);
        sced2 = new cButton(p5,"*",90, 28,50, 3,7,7);
        sced3 = new cButton(p5,"<",150, 28, 50, 3,7,7);
        sced4 = new cButton(p5,">",210, 28, 50, 3,7,7);
        sced5 = new cButton(p5,"X",270, 28, 50, 3,7,7);

        exit = new rButton(p5, "EXIT", 1178, 34, 196, 60, 3, 7,7 );

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
        s1.display(p5);

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

    public void drawSETTINGS(PApplet p5) {
        p5.pushStyle();

        p5.background(Colors.getThisColor(1));
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(Colors.getThisColor(3));
        p5.stroke(Colors.getThisColor(7));
        p5.strokeWeight(2);

        p5.rect(640, 360, 720,360, 20);

        p5.fill(Colors.getThisColor(7));
        if (lang == LANG.ESP) {
            p5.text("AJUSTES", 640, 240);
            p5.text("Idioma:", 448, 360);
            p5.text("Modo:", 434, 420);
        }else{
            p5.text("SETTINGS", 640, 240);
            p5.text("Language:", 462, 360);
            p5.text("Mode:", 434, 420);
        }

        slVolume.display(p5);
        p5.text((int) slVolume.v,  880, 270);
        sCol.display(p5);
        sLang.display(p5);
        deleteEverything.display(p5);
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
                }
            }else {
                tfSelectSearch.display(p5);
            }
        }

        p5.fill(255);
        p5.rect(354, 114, 920, 600);

        if (gridon) {
            p5.strokeWeight(2);

            for (int i = 354; i <= 1274; i += 20) {
                p5.stroke(0);
                p5.line(i, 114, i, 714);
                p5.stroke(Colors.getThisColor(7));
                p5.line(i, 94, i, 104);
            }
            for (int j = 114; j <= 720; j += 20) {
                p5.stroke(0);
                p5.line(354, j, 1274, j);
                p5.stroke(Colors.getThisColor(7));
                p5.line(334, j, 344, j);
            }
        }

        p5.fill(0);


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

        float phase = 255 *p5.sin(p5.frameCount*0.1f);

        if (scene.nObjects>0) {
            p5.noFill();
            p5.stroke(255, 0, 0, phase);
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
        p5.pushMatrix();

        p5.background(Colors.getThisColor(1));

        p5.textFont(Fonts.getThisFont(1));
        p5.textMode(p5.LEFT);
        p5.stroke(Colors.getThisColor(6));
        p5.strokeWeight(3);
        p5.fill(Colors.getThisColor(2));

        int x = 10;
        p5.rect(x, 76, 256, 632, 15);
        p5.line(x, 128, x + (256), 128);
        p5.line(x, 124, x + (256), 124);

        p5.fill(Colors.getThisColor(6));

        p5.textMode(p5.LEFT);
        if (lang == LANG.ESP) {
            p5.text("Nombre", x + 10, 110);
            p5.text("Altura [m]", x + 10, 170);
            p5.text("Peso [kg]", x + 10, 230);
            p5.text("IMC", x + 10, 290);
            p5.text("Anchura [m]", x + 10, 360);
            p5.textFont(Fonts.getThisFont(2));
            p5.text("Ratio cabeza/cuerpo", x + 10, 420);
            p5.text("Edad aproximada [años]", x + 10, 480);
            p5.textFont(Fonts.getThisFont(1));
            p5.text("ID único", x + 10, 670);
        } else if (lang == LANG.ENG) {
            p5.text("Name", x + 10, 110);
            p5.text("Height[m]", x + 10, 170);
            p5.text("Weight[kg]", x + 10, 230);
            p5.text("Body Mass Index", x + 10, 290);
            p5.text("Width [m]", x + 10, 360);
            p5.textFont(Fonts.getThisFont(2));
            p5.text("Head/Body ratio", x + 10, 420);
            p5.text("Average Age [years]", x + 10, 480);
            p5.textFont(Fonts.getThisFont(1));
            p5.text("Unique ID", x + 10, 670);
        }

        for (int i = 0; i < navigation.length; i++){
            navigation[i].display(p5);
        }
        tfInfoSearch.display(p5);
        exit.display(p5);

        p5.popMatrix();
        p5.popStyle();
    }

}