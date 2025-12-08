import processing.core.PApplet;
import processing.core.PImage;


public class Main extends PApplet {

    Fonts fonts;
    GUI gui;
    boolean sceneEditorInitialized = false;
    public Slider selectedSl;
    public int scedPage = 0;

    Stand banana, cabinet, door;
    public Scene[] scenes = new Scene[150];
    public Scene scene;
    public Stand[] allStands = new Stand[3];
    public OC[] allOCs;
    public InfoSlab[] slabs = new InfoSlab[0];
    public SelectSlab[] selects = new SelectSlab[0];

    public int nAllOCs = 0;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        fullScreen();
        smooth(100);

        PImage bananaPic = loadImage("data/Bananana.png");
        PImage gabinett = loadImage("data/gabinett.png");
        PImage dooor = loadImage("data/door.jpg");
        banana = new Stand("Plátano", 0.2f, 0.07f, bananaPic);
        cabinet = new Stand("Gabinete", 0.5f, 0.8f, gabinett);
        door = new Stand("Puerta", 0.6f, 2f, dooor);
        allStands[0] = banana;
        allStands[1] = cabinet;
        allStands[2] = door;
        allOCs = new OC[0];
        for (int i = 0; i < scenes.length; i++) scenes[i] = new Scene(i);
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
            case PRELOGIN -> gui.drawPRELOGIN(this);
            case LOGIN -> gui.drawLOGIN(this);
            case SIGNUP -> gui.drawSIGNUP(this);
            case MAIN -> gui.drawMAIN(this);
            case QNA -> gui.drawQNA(this);
            case SCENESELECTOR -> gui.drawSCENESELECTOR(this);
            case SCENEEDITOR -> {
                gui.drawSCENEEDITOR(this, scene);
                if (!sceneEditorInitialized) {
                    initializeSceneEditorValues();
                    sceneEditorInitialized = true;
                }
                if (scene.nObjects < 2) {
                    gui.sced3.setEnabled(false);
                    gui.sced4.setEnabled(false);
                } else {
                    gui.sced3.setEnabled(true);
                    gui.sced4.setEnabled(true);
                }
                if (scene.sel == Scene.scInstance.OCSELECT) {
                    int start = scene.selPage * 10;
                    int end = Math.min(selects.length, start + 10);
                    for (int i = start; i < end; i++) selects[i].display(this);
                }
            }
            case OCVIEWER -> {
                gui.drawOCVIEWER(this);
                int start = scedPage * 5;
                int end = Math.min(slabs.length, start + 5);
                for (int i = start; i < end; i++) slabs[i].display(this, scedPage);
            }
        }

        if (scene != null) scene.designLayout();
    }

    public void initializeSceneEditorValues() {
        for (int i = 0; i < gui.slSced.length; i++) {
            if (gui.slSced[i] != null && gui.tfsced[i] != null) {
                if (i >= 7 && i < 10) {
                    gui.slSced[i].v = (int) gui.slSced[i].v;
                    gui.tfsced[i].setText(String.valueOf((int) gui.slSced[i].v));
                } else {
                    gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                }
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
            if (!gui.tflogin1.mouseOverTextField(this) && gui.tflogin1.selected) gui.tflogin1.keyPressed('0', ENTER);
            if (!gui.tflogin2.mouseOverTextField(this) && gui.tflogin2.selected) gui.tflogin2.keyPressed('0', ENTER);
        } else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
            if (gui.signup.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            gui.tfsignup1.isPressed(this);
            gui.tfsignup2.isPressed(this);
            gui.tfsignup3.isPressed(this);
            gui.tfsignup4.isPressed(this);
            if (!gui.tfsignup1.mouseOverTextField(this) && gui.tfsignup1.selected) gui.tfsignup1.keyPressed('0', ENTER);
            if (!gui.tfsignup2.mouseOverTextField(this) && gui.tfsignup2.selected) gui.tfsignup2.keyPressed('0', ENTER);
            if (!gui.tfsignup3.mouseOverTextField(this) && gui.tfsignup3.selected) gui.tfsignup3.keyPressed('0', ENTER);
            if (!gui.tfsignup4.mouseOverTextField(this) && gui.tfsignup4.selected) gui.tfsignup4.keyPressed('0', ENTER);
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
                    scene = scenes[i];
                    gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
                    scene.selPage = 0;
                    sceneEditorInitialized = false;
                }
            }
        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            int start = scene.selPage * 10;
            int end = Math.min(selects.length, start + 10);
            for (int i = start; i < end; i++) {
                if (selects[i].mouseOverButton(this)){
                    addThisOC(selects[i].oc);
                }
            }
            if (gui.exit.mouseOverButton(this)) {
                scene.sel = Scene.scInstance.DISPLAY;
                scene.selPage = 0;
                gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
                scene = null;
            }
            if (gui.rsced1.mouseOverButton(this)) gui.gridon = !gui.gridon;
            for (int i = 0; i < gui.tfsced.length; i++) {
                if (i != 2 && i != 4) {
                    gui.tfsced[i].isPressed(this);
                    if (!gui.tfsced[i].mouseOverTextField(this) && gui.tfsced[i].selected) gui.tfsced[i].keyPressed('0', ENTER);
                }
            }
            for (int i = 1; i < gui.slSced.length; i++) {
                if (i == 2 || i == 4) continue;
                if (gui.slSced[i].mouseOnSlider(this)) {
                    gui.slSced[i].checkSlider(this);
                    selectedSl = gui.slSced[i];
                    if (i >= 7 && i < 10) gui.tfsced[i].setText(String.valueOf((int) gui.slSced[i].v));
                    else gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                } else {
                    String txt = gui.tfsced[i].getText().replace(',', '.');
                    if (txt.matches("\\d*(\\.\\d{0,2})?") && !txt.isEmpty()) {
                        if (i >= 6 && i < 10) {
                            int value = (int) Float.parseFloat(txt);
                            value = (int) constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);
                            gui.slSced[i].v = value;
                            gui.tfsced[i].setText(String.valueOf(value));
                        } else {
                            float value = Float.parseFloat(txt);
                            value = constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);
                            gui.slSced[i].v = value;
                            gui.tfsced[i].setText(String.format("%.2f", value));
                        }
                    }
                    updateCalculatedValues();
                }
            }
            if (gui.sced1.mouseOverButton(this)) instanceOC();
            if (gui.sced2.mouseOverButton(this)) {
                scene.sel = Scene.scInstance.OCSELECT;
            }
            if (gui.sced4.enabled && gui.sced4.mouseOverButton(this)) {
                if (scene.sel == Scene.scInstance.DISPLAY) {
                    if (scene.nObjects > 0) {
                        scene.currentObject = (scene.currentObject + 1) % scene.nObjects;
                        if (scene.stands[scene.currentObject] instanceof OC pHolder) {
                            changeTFValues(pHolder);
                            gui.slSced[1].v = pHolder.tHeight;
                            gui.slSced[2].v = pHolder.weight;
                            gui.slSced[3].v = pHolder.BMI;
                            gui.slSced[4].v = pHolder.tWidth;
                            gui.slSced[5].v = pHolder.bhratio;
                            gui.slSced[6].v = pHolder.age;
                            gui.slSced[7].v = pHolder.r;
                            gui.slSced[8].v = pHolder.g;
                            gui.slSced[9].v = pHolder.b;
                            selectedSl = null;
                            updateCalculatedValues();
                        }
                    }
                } else {
                    scene.selPage++;
                }
            }

            if (gui.sced3.enabled && gui.sced3.mouseOverButton(this)) {
                if (scene.sel == Scene.scInstance.DISPLAY) {
                    if (scene.nObjects > 0) {
                        scene.currentObject = (scene.currentObject - 1 + scene.nObjects) % scene.nObjects;
                        if (scene.stands[scene.currentObject] instanceof OC pHolder) {
                            changeTFValues(pHolder);
                            gui.slSced[1].v = pHolder.tHeight;
                            gui.slSced[2].v = pHolder.weight;
                            gui.slSced[3].v = pHolder.BMI;
                            gui.slSced[4].v = pHolder.tWidth;
                            gui.slSced[5].v = pHolder.bhratio;
                            gui.slSced[6].v = pHolder.age;
                            gui.slSced[7].v = pHolder.r;
                            gui.slSced[8].v = pHolder.g;
                            gui.slSced[9].v = pHolder.b;
                            selectedSl = null;
                            updateCalculatedValues();
                        }
                    }
                } else {
                    if (scene.selPage != 0) scene.selPage--;
                }
            }

            if (gui.sced5.mouseOverButton(this)){
                if (scene.sel == Scene.scInstance.OCSELECT){
                    scene.sel = Scene.scInstance.DISPLAY;
                } else {
                    scene.deleteObject(scene.currentObject);
                }
            }

            if (scene != null) updateCalculatedValues();
        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {
            if (gui.nav1.mouseOverButton(this)) scedPage = 0;
            if (gui.nav2.mouseOverButton(this) && scedPage > 0) scedPage--;
            if (gui.nav3.mouseOverButton(this) && scedPage < (slabs.length - 1) / 5) scedPage++;
            if (gui.nav4.mouseOverButton(this)) scedPage = (slabs.length - 1) / 5;
            if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            for (int i = 0; i < nAllOCs; i++) if (slabs[i].delete.mouseOverButton(this)) deleteOCfromBase(allOCs[i]);
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
                    if (txt.matches("\\d*(\\.\\d{0,3})?") && !txt.isEmpty()) {
                        if (i >= 6 && i < 10) {
                            int value = (int) Float.parseFloat(txt);
                            value = (int) constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);
                            gui.slSced[i].v = value;
                            gui.tfsced[i].setText(String.valueOf(value));
                        } else {
                            float value = Float.parseFloat(txt);
                            value = constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);
                            gui.slSced[i].v = value;
                            gui.tfsced[i].setText(String.format("%.3f", value));
                        }
                    }
                }
            }
            updateCalculatedValues();
        }
        if (key == 'z' || key == 'Z') {
            if (scene != null) for (int i = 0; i < 21; i++) instanceOC();
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
                    if (i >= 7 && i < 10) gui.tfsced[i].setText(String.valueOf((int) gui.slSced[i].v));
                    else gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                }
            }
            updateCalculatedValues();
        }
    }

    public void mouseReleased() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) updateCalculatedValues();
    }

    public void addNewOCtoBase(OC oc) {
        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs + 1);
        slabs = java.util.Arrays.copyOf(slabs, nAllOCs + 1);
        selects = java.util.Arrays.copyOf(selects, nAllOCs + 1);

        oc.ID = nAllOCs;
        oc.uniqueID = (long) (System.nanoTime() + Math.random() * 1000);

        allOCs[nAllOCs] = oc;
        slabs[nAllOCs] = new InfoSlab(oc, this);
        selects[nAllOCs] = new SelectSlab(this, oc);

        nAllOCs++;
    }

    public void deleteOCfromBase(OC oc) {
        long neededID = oc.uniqueID;
        int index = -1;

        for (int i = 0; i < nAllOCs; i++) {
            if (allOCs[i].uniqueID == neededID) {
                index = i;
                break;
            }
        }
        if (index == -1) return;

        for (int i = index; i < nAllOCs - 1; i++) {
            allOCs[i] = allOCs[i + 1];
            allOCs[i].ID = i;
            slabs[i] = slabs[i + 1];
            selects[i] = selects[i + 1];
        }

        allOCs[nAllOCs - 1] = null;
        slabs[nAllOCs - 1] = null;
        selects[nAllOCs - 1] = null;

        nAllOCs--;

        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs);
        slabs = java.util.Arrays.copyOf(slabs, nAllOCs);
        selects = java.util.Arrays.copyOf(selects, nAllOCs);

        for (Scene s : scenes) {
            if (s == null) continue;
            for (int j = s.nObjects - 1; j >= 0; j--) {
                Stand st = s.stands[j];
                if (st instanceof OC oc2 && oc2.uniqueID == neededID) {
                    s.deleteObject(j);
                }
            }
        }
    }

    public void updateCalculatedValues() {
        float height = round(gui.slSced[1].v, 3);
        gui.slSced[1].v = height;
        if (!gui.tfsced[1].selected) gui.tfsced[1].setText(String.format("%.3f", height));

        float bmi = gui.slSced[3].v;
        gui.slSced[2].minV = height * height;
        gui.slSced[2].maxV = 250 * height * height;
        gui.slSced[2].v = constrain(bmi * height * height, gui.slSced[2].minV, gui.slSced[2].maxV);
        gui.slSced[2].v = round(gui.slSced[2].v, 2);
        if (!gui.tfsced[2].selected) gui.tfsced[2].setText(String.format("%.2f", gui.slSced[2].v));

        gui.slSced[4].v = constrain((float) (height * Math.pow(bmi, 0.7979) / 81.906),
                (float) (height * Math.pow(1, 0.7979) / 81.906),
                (float) (height * Math.pow(250, 0.7979) / 81.906));
        gui.slSced[4].v = round(gui.slSced[4].v, 3);
        if (!gui.tfsced[4].selected) gui.tfsced[4].setText(String.format("%.3f", gui.slSced[4].v));

        if (!gui.tfsced[5].selected) gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));
        if (!gui.tfsced[6].selected) gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));

        if (selectedSl == gui.slSced[5]) {
            gui.tfsced[5].setEnabled(true);
            gui.tfsced[6].setEnabled(false);
            float ratio = gui.slSced[5].v;
            gui.slSced[6].v = Math.abs(-(1 / 0.0741f) * (float) Math.log((ratio - 0.125f) / 0.125f));
        } else if (selectedSl == gui.slSced[6]) {
            gui.tfsced[5].setEnabled(false);
            gui.tfsced[6].setEnabled(true);
            float age = gui.slSced[6].v;
            gui.slSced[5].v = 0.125f + 0.125f * (float) Math.exp(-0.0741f * age);
        }

        gui.slSced[6].v = constrain(gui.slSced[6].v, 0, 80);
        gui.slSced[6].v = Math.round(gui.slSced[6].v);
        if (!gui.tfsced[6].selected) gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));

        gui.slSced[5].v = constrain(gui.slSced[5].v, 0.125f, 0.25f);
        gui.slSced[5].v = round(gui.slSced[5].v, 3);
        if (!gui.tfsced[5].selected) gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));

        if (scene != null && scene.nObjects > 0 && scene.stands[scene.currentObject] instanceof OC pHolder) {
            pHolder.name = gui.tfsced[0].text;
            pHolder.tHeight = gui.slSced[1].v;
            pHolder.weight = gui.slSced[2].v;
            pHolder.BMI = gui.slSced[3].v;
            pHolder.tWidth = gui.slSced[4].v;
            pHolder.bhratio = gui.slSced[5].v;
            pHolder.age = (int) gui.slSced[6].v;
            pHolder.r = (int) gui.slSced[7].v;
            pHolder.g = (int) gui.slSced[8].v;
            pHolder.b = (int) gui.slSced[9].v;
        }
    }

    public void changeTFValues(OC pHolder) {
        if (!gui.tfsced[0].selected) gui.tfsced[0].text = pHolder.name;
        if (!gui.tfsced[1].selected) gui.tfsced[1].text = String.valueOf(pHolder.tHeight);
        if (!gui.tfsced[2].selected) gui.tfsced[2].text = String.valueOf(pHolder.weight);
        if (!gui.tfsced[3].selected) gui.tfsced[3].text = String.valueOf(pHolder.BMI);
        if (!gui.tfsced[4].selected) gui.tfsced[4].text = String.valueOf(pHolder.tWidth);
        if (!gui.tfsced[5].selected) gui.tfsced[5].text = String.valueOf(pHolder.bhratio);
        if (!gui.tfsced[6].selected) gui.tfsced[6].text = String.valueOf(pHolder.age);
        if (!gui.tfsced[7].selected) gui.tfsced[7].text = String.valueOf(pHolder.r);
        if (!gui.tfsced[8].selected) gui.tfsced[8].text = String.valueOf(pHolder.g);
        if (!gui.tfsced[9].selected) gui.tfsced[9].text = String.valueOf(pHolder.b);
    }

    public void instanceOC() {
        OC pHolder = new OC(nAllOCs);

        gui.slSced[1].v = 1.83f;
        gui.slSced[2].v = (float) Math.pow(1.83f, 2) * 25;
        gui.slSced[3].v = 25;
        gui.slSced[4].v = (float) Math.pow(25, 0.7979) / 81.906f;
        gui.slSced[6].v = 25;
        gui.slSced[5].v = 0.1295f;
        gui.slSced[7].v = 127;
        gui.slSced[8].v = 127;
        gui.slSced[9].v = 127;

        pHolder.name = "Zwolf";
        pHolder.tHeight = gui.slSced[1].v;
        pHolder.weight = gui.slSced[2].v;
        pHolder.BMI = gui.slSced[3].v;
        pHolder.tWidth = gui.slSced[4].v;
        pHolder.bhratio = gui.slSced[5].v;
        pHolder.age = (int) gui.slSced[6].v;
        pHolder.r = (int) gui.slSced[7].v;
        pHolder.g = (int) gui.slSced[8].v;
        pHolder.b = (int) gui.slSced[9].v;

        changeTFValues(pHolder);
        addNewOCtoBase(pHolder);
        scene.addObject(pHolder);
    }

    public void addThisOC(OC pHolder) {
        scene.addObject(pHolder);

        gui.slSced[1].v = pHolder.tHeight;
        gui.slSced[2].v = pHolder.weight;
        gui.slSced[3].v = pHolder.BMI;
        gui.slSced[4].v = pHolder.tWidth;
        gui.slSced[5].v = pHolder.bhratio;
        gui.slSced[6].v = pHolder.age;
        gui.slSced[7].v = pHolder.r;
        gui.slSced[8].v = pHolder.g;
        gui.slSced[9].v = pHolder.b;

        changeTFValues(pHolder);
    }

    private float round(float value, int decimals) {
        float factor = (float) Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

}
