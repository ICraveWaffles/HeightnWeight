import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

    Fonts fonts;
    GUI gui;
    boolean sceneEditorInitialized = false;

    Stand[] furniture;
    Stand banana, cabinet, door;
    Scene scene;


    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        fullScreen();
        smooth(100);


        PImage bananaPic = loadImage("data/Bananana.png");
        PImage gabinett = loadImage("data/gabinett.png");
        PImage dooor = loadImage("data/door.jpg");
        banana = new Stand ("Plátano", 0.2f, 0.07f, bananaPic );
        cabinet = new Stand ("Gabinete", 0.5f, 0.8f, gabinett);
        door = new Stand("Puerta", 0.6f , 2, dooor);
        scene = new Scene(1);

    }

    public void setup() {
        fonts = new Fonts(this);
        gui = new GUI(this);
        gui.tfsced[2].setEnabled(false);
        gui.tfsced[4].setEnabled(false);
        gui.slSced[2].setEnabled(false);
        gui.slSced[4].setEnabled(false);

    }

    public void draw() {
        switch (gui.currentScreen) {
            case PRELOGIN: gui.drawPRELOGIN(this); break;
            case LOGIN: gui.drawLOGIN(this); break;
            case SIGNUP: gui.drawSIGNUP(this); break;
            case MAIN: gui.drawMAIN(this); break;
            case QNA: gui.drawQNA(this); break;
            case SCENESELECTOR: gui.drawSCENESELECTOR(this); break;
            case SCENEEDITOR:
                gui.drawSCENEEDITOR(this, scene);
                if (!sceneEditorInitialized) {
                    initializeSceneEditorValues();
                    sceneEditorInitialized = true;
                }
                if (scene.nObjects<2) {
                    gui.sced3.setEnabled(false);
                    gui.sced4.setEnabled(false);
                } else {
                    gui.sced3.setEnabled(true);
                    gui.sced4.setEnabled(true);
                }
                break;
            case OCVIEWER: gui.drawOCVIEWER(this); break;
        }

        noFill();
        rect(0, 0, 1280, 720);
        scene.designLayout();
    }

    public void initializeSceneEditorValues() {
        for (int i = 0; i < gui.slSced.length; i++) {
            if (gui.slSced[i] != null && gui.tfsced[i] != null) {
                gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
            }
        }
        updateCalculatedValues();
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
            if (gui.exit.mouseOverButton(this)) {
                gui.currentScreen = GUI.SCREEN.MAIN;
                gui.page = 0;
            }
            if (gui.nav1.mouseOverButton(this)) gui.page = 0;
            if (gui.nav2.mouseOverButton(this) && gui.page > 0) gui.page--;
            if (gui.nav3.mouseOverButton(this) && gui.page < 9) gui.page++;
            if (gui.nav4.mouseOverButton(this)) gui.page = 9;
            for (int i = 0; i < gui.scenes.length; i++) {
                if (gui.scenes[i].mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
                    sceneEditorInitialized = false;
                }
            }
        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
            if (gui.rsced1.mouseOverButton(this)) gui.gridon = !gui.gridon;
            for (int i = 0; i < gui.tfsced.length; i++) {
                if (i != 2 && i != 4) gui.tfsced[i].isPressed(this);
            }
            for (int i = 1; i < gui.slSced.length; i++) {
                if (i == 2 || i == 4) continue;
                if (gui.slSced[i].mouseOnSlider(this)) {
                    gui.slSced[i].checkSlider(this);
                    gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                }
            }
            if (gui.sced2.mouseOverButton(this)){
                scene.addObject(banana);
                scene.addObject(cabinet);
                scene.addObject(door);
                print("Object has been added!");
            }
            if (gui.sced4.enabled) {
                if (gui.sced4.mouseOverButton(this)) {
                    if (scene.nObjects == 0) {
                        scene.currentObject = -1;
                    } else {
                        if (scene.currentObject >= scene.nObjects - 1) {
                            scene.currentObject = 0;
                        } else {
                            scene.currentObject++;
                        }
                    }
                }
            }

            if (gui.sced3.enabled) {
                if (gui.sced3.mouseOverButton(this)) {
                    if (scene.nObjects == 0) {
                        scene.currentObject = -1;
                    } else {
                        if (scene.currentObject <= 0) {
                            scene.currentObject = scene.nObjects - 1;
                        } else {
                            scene.currentObject--;
                        }
                    }
                }
            }

            if (gui.sced5.mouseOverButton(this)) {
                scene.deleteObject(scene.currentObject);
                print("Object has been deleted!");
            }
            updateCalculatedValues();
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
            for (int i = 0; i < gui.tfsced.length; i++) {
                if (i == 2 || i == 4) continue;
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
            updateCalculatedValues();
        }
    }

    public void mouseDragged() {
        if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
            if (gui.slZero.mouseDraggingOnSlider(this)) gui.slZero.checkSlider(this);
        }
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            for (int i = 0; i < gui.slSced.length; i++) {
                if (i == 2 || i == 4) continue;
                if (gui.slSced[i] != null && gui.slSced[i].mouseDraggingOnSlider(this)) {
                    gui.slSced[i].checkSlider(this);
                    gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                    if (i == 6) {
                        float age = gui.slSced[6].v;
                        gui.slSced[5].v = 0.125f + 0.125f * (float) Math.exp(-0.0741f * age);
                        gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));
                    } else if (i == 5) {
                        float ratio = gui.slSced[5].v;
                        gui.slSced[6].v = -(1 / 0.0741f) * (float) Math.log((ratio - 0.125f) / 0.125f);
                        gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));
                    }
                }
            }
            updateCalculatedValues();
        }
    }

    public void mouseReleased() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) updateCalculatedValues();
    }

    public void updateCalculatedValues() {
        float altura = gui.slSced[1].v;
        float bmi = gui.slSced[3].v;
        gui.slSced[2].minV = pow(altura, 2);
        gui.slSced[2].maxV = 250 * pow(altura, 2);
        gui.slSced[2].v = constrain(bmi * pow(altura, 2), gui.slSced[2].minV, gui.slSced[2].maxV);
        gui.slSced[4].minV = (float) (altura * pow(1, 0.7979f) / 81.906);
        gui.slSced[4].maxV = (float) (altura * pow(250, 0.7979f) / 81.906);
        gui.slSced[4].v = constrain((float) (altura * pow(bmi, 0.7979f) / 81.906), gui.slSced[4].minV, gui.slSced[4].maxV);
        gui.tfsced[2].setText(String.format("%.2f", gui.slSced[2].v));
        gui.tfsced[4].setText(String.format("%.2f", gui.slSced[4].v));
        gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));
        for (int i = 7; i < 10; i++){
            gui.slSced[i].v = (int) gui.slSced[i].v;
            gui.tfsced[i].setText(String.format("%.0f", gui.slSced[i].v));
        }

    }
}
