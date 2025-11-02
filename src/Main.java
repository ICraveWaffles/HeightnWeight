import processing.core.PApplet;

public class Main extends PApplet {

    Fonts fonts;
    GUI gui;
    float scaleFactor;
    int baseWidth = 1920, baseHeight = 1080;
    TextField selectedtf;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        fullScreen();
        smooth(100);
    }

    public void setup() {
        fonts = new Fonts(this);
        gui = new GUI(this);
    }

    public void draw() {
        switch (gui.currentScreen) {
            case PRELOGIN -> gui.drawPRELOGIN(this);
            case LOGIN -> gui.drawLOGIN(this);
            case SIGNUP -> gui.drawSIGNUP(this);
            case MAIN -> gui.drawMAIN(this);
            case QNA -> gui.drawQNA(this);
            case SCENESELECTOR -> gui.drawSCENESELECTOR(this);
            case SCENEEDITOR -> gui.drawSCENEEDITOR(this);
            case OCVIEWER -> gui.drawOCVIEWER(this);
        }
        noFill();
        rect(0, 0, 1280, 720);
    }

    public void mousePressed() {
        if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
            if (gui.plog1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.LOGIN;
            if (gui.plog2.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SIGNUP;
            if (gui.exit.mouseOverButton(this)) exit();
        } else if (gui.currentScreen == GUI.SCREEN.LOGIN) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
            if (gui.login.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            gui.tflogin1.isPressed(this);
            gui.tflogin2.isPressed(this);
        } else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
            if (gui.signup.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            gui.tfsignup1.isPressed(this);
            gui.tfsignup2.isPressed(this);
            gui.tfsignup3.isPressed(this);
            gui.tfsignup4.isPressed(this);
        } else if (gui.currentScreen == GUI.SCREEN.MAIN) {
            if (gui.q1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.QNA;
            if (gui.m1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
            if (gui.m2.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.OCVIEWER;
            if (gui.m3.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
        } else if (gui.currentScreen == GUI.SCREEN.QNA) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
        } else if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {
            if (gui.exit.mouseOverButton(this)) { gui.currentScreen = GUI.SCREEN.MAIN; gui.page = 0; }
            if (gui.nav1.mouseOverButton(this)) gui.page = 0;
            if (gui.nav2.mouseOverButton(this) && gui.page > 0) gui.page--;
            if (gui.nav3.mouseOverButton(this) && gui.page < 9) gui.page++;
            if (gui.nav4.mouseOverButton(this)) gui.page = 9;
            for (int i = 0; i < gui.scenes.length; i++)
                if (gui.scenes[i].mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
            if (gui.rsced1.mouseOverButton(this)) gui.gridon = !gui.gridon;

            for (int i = 0; i < 10; i++) {
                if (gui.lbsced[i].mouseOverButton(this)) gui.lbsced[i].mousePressed(this);
                if (!gui.lbsced[i].isLocked()) gui.tfsced[i].isPressed(this);
            }

            for (int i = 1; i < gui.slSced.length; i++) {
                if (!gui.lbsced[i].isLocked() && gui.slSced[i].mouseOnSlider(this))
                    gui.slSced[i].checkSlider(this);
                else
                    gui.tfsced[i].text = String.format("%.2f", gui.slSced[i].v);
            }

            float altura = gui.slSced[1].v;
            float bmi = gui.slSced[3].v;

            gui.slSced[3].minV = 1;
            gui.slSced[3].maxV = 250;
            gui.slSced[1].minV = 0.01f;
            gui.slSced[1].maxV = 10;

            gui.slSced[2].minV = 1 * pow(altura, 2);
            gui.slSced[2].maxV = 250 * pow(altura, 2);
            gui.slSced[2].v = constrain(bmi * pow(altura, 2), gui.slSced[2].minV, gui.slSced[2].maxV);

            gui.slSced[4].minV = (float) (altura * pow(1, 0.7979f) / 81.906);
            gui.slSced[4].maxV = (float) (altura * pow(250, 0.7979f) / 81.906);
            gui.slSced[4].v = constrain((float) (altura * pow(bmi, 0.7979f) / 81.906), gui.slSced[4].minV, gui.slSced[4].maxV);

            gui.tfsced[2].setText(String.format("%.2f", gui.slSced[2].v));
            gui.tfsced[4].setText(String.format("%.2f", gui.slSced[4].v));
        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
        }
    }

    public void keyPressed() {
        if (gui.currentScreen == GUI.SCREEN.LOGIN) {
            gui.tflogin1.keyPressed(key, keyCode);
            gui.tflogin2.keyPressed(key, keyCode);
        } else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
            gui.tfsignup1.keyPressed(key, keyCode);
            gui.tfsignup2.keyPressed(key, keyCode);
            gui.tfsignup3.keyPressed(key, keyCode);
            gui.tfsignup4.keyPressed(key, keyCode);
        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            gui.tfsced[0].keyPressed(key, keyCode);
            for (int i = 1; i < 10; i++) {
                if (!gui.lbsced[i].isLocked()) {
                    gui.tfsced[i].keyPressed(key, keyCode);
                    if (gui.tfsced[i].selected && keyCode == ENTER) {
                        String txt = gui.tfsced[i].getText().replace(',', '.');
                        if (txt.matches("\\d*(\\.\\d{0,2})?") && !txt.isEmpty()) {
                            float value = Float.parseFloat(txt);
                            value = constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);
                            gui.slSced[i].v = value;
                            gui.tfsced[i].setText(String.format("%.2f", value));
                        }
                    }
                }
            }
        }
    }

    public void mouseDragged() {
        if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
            if (gui.slZero.mouseDraggingOnSlider(this)) gui.slZero.checkSlider(this);
        }
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            for (int i = 1; i < gui.slSced.length; i++)
                if (!gui.lbsced[i].isLocked() && gui.slSced[i].mouseDraggingOnSlider(this))
                    gui.slSced[i].checkSlider(this);
        }
    }

    public void mouseReleased() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {

            float altura = gui.slSced[1].v;
            float bmi = gui.slSced[3].v;

            gui.slSced[3].minV = 1;
            gui.slSced[3].maxV = 250;
            gui.slSced[1].minV = 0.01f;
            gui.slSced[1].maxV = 10;

            gui.slSced[2].minV = 1 * pow(altura, 2);
            gui.slSced[2].maxV = 250 * pow(altura, 2);
            gui.slSced[2].v = constrain(bmi * pow(altura, 2), gui.slSced[2].minV, gui.slSced[2].maxV);

            gui.slSced[4].minV = (float) (altura * pow(1, 0.7979f) / 81.906);
            gui.slSced[4].maxV = (float) (altura * pow(250, 0.7979f) / 81.906);
            gui.slSced[4].v = constrain((float) (altura * pow(bmi, 0.7979f) / 81.906), gui.slSced[4].minV, gui.slSced[4].maxV);

            gui.tfsced[2].setText(String.format("%.2f", gui.slSced[2].v));
            gui.tfsced[4].setText(String.format("%.2f", gui.slSced[4].v));

            for (int i = 1; i < gui.slSced.length; i++) {
                if (!gui.lbsced[i].isLocked()) {
                    if (gui.slSced[i].mouseOnSlider(this) || gui.slSced[i].mouseDraggingOnSlider(this)) {
                        gui.slSced[i].updateSlider(this);
                    }
                }
                gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].getValue()));
            }
        }
    }
    }
