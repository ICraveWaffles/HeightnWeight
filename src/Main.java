import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Main extends PApplet {
    Fonts fonts;
    GUI gui;
    boolean sceneEditorInitialized = false;
    public Slider selectedSl;
    public int scedPage = 0;
    Stand banana, cabinet, door;
    public ArrayList<Scene> scenes = new ArrayList<>();
    public Scene scene;
    public Stand[] allStands = new Stand[3];
    public OC[] allOCs;
    public InfoSlab[] slabs = new InfoSlab[0];
    public SelectSlab[] selects = new SelectSlab[0];
    public SelectSlab searchedOC;
    public SelectSlab[] searchSelects;
    public boolean firstClick = false;
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
        scenes.add(new Scene(0));
        scene = scenes.getFirst();
    }

    public void setup() {
        fonts = new Fonts(this);
        gui = new GUI(this);
        try {
            Languages.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        translateEverything();

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
            case SCENESELECTOR -> {
                gui.drawSCENESELECTOR(this);
                if (scenes.size() < 15) {
                    gui.nav1.setEnabled(false);
                    gui.nav2.setEnabled(false);
                    gui.nav3.setEnabled(false);
                    gui.nav4.setEnabled(false);
                } else {
                    gui.nav1.setEnabled(true);
                    gui.nav2.setEnabled(true);
                    gui.nav3.setEnabled(true);
                    gui.nav4.setEnabled(true);
                }
            }
            case SCENEEDITOR -> {
                gui.drawSCENEEDITOR(this, scene);

                pushStyle();
                textSize(24);
                fill(0);
                if (gui.lang == LANG.ESP) {
                    text("Peso(kg): ", 25, 210-4);
                    text("Ancho(m): ", 25, 330-4);
                } else {
                    text("Weight(kg): ", 25, 210-4);
                    text("Width(m): ", 25, 330-4);
                }
                popStyle();


                if (!sceneEditorInitialized) {
                    initializeSceneEditorValues();
                    sceneEditorInitialized = true;
                }

                if (scene.sel == Scene.scInstance.DISPLAY) {
                    if (scene.nObjects == 0) {
                        gui.sced3.setEnabled(false);
                        gui.sced4.setEnabled(false);
                        gui.sced5.setEnabled(false);
                    } else if (scene.nObjects < 2) {
                        gui.sced3.setEnabled(false);
                        gui.sced4.setEnabled(false);
                        gui.sced5.setEnabled(true);
                    } else {
                        gui.sced3.setEnabled(true);
                        gui.sced4.setEnabled(true);
                        gui.sced5.setEnabled(true);
                    }
                    checkSelectStatus();
                } else {
                    gui.sced3.setEnabled(true);
                    gui.sced4.setEnabled(true);

                    int start = scene.selPage * 10;
                    int end;

                    if (gui.tfSelectSearch.text.equals("")) {
                        end = Math.min(selects.length, start + 10);
                        for (int i = start; i < end; i++) {
                            selects[i].display(this);
                        }
                    } else {
                        if (searchedOC != null) searchedOC.display(this);
                    }
                    gui.sced3.setEnabled(scene.selPage != 0);
                    if (gui.tfSelectSearch.text.equals("")) {
                        int maxPage = (nAllOCs - 1) / 10;
                        gui.sced4.setEnabled(scene.selPage < maxPage);
                    } else if (searchSelects != null) {
                        int maxPage = (searchSelects.length - 1) / 10;
                        gui.sced4.setEnabled(scene.selPage < maxPage);
                    }
                }
            }
            case OCVIEWER -> {
                gui.drawOCVIEWER(this);
                int start = scedPage * 5;
                int end = Math.min(slabs.length, start + 5);
                for (int i = start; i < end; i++) {
                    slabs[i].display(this, scedPage);
                }
            }
            case SETTINGS -> gui.drawSETTINGS(this);
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

    public void redoSceneEditorValues() {
        if (scene.currentObject != -1) {
            OC pHolder = (OC) scene.stands[scene.currentObject];
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
    }

    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
                if (gui.plog1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.LOGIN;
                if (gui.plog2.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SIGNUP;
                if (gui.exit.mouseOverButton(this)) exit();
            }
            else if (gui.currentScreen == GUI.SCREEN.LOGIN) {
                if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
                if (gui.login.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
                gui.tflogin1.isPressed(this);
                gui.tflogin2.isPressed(this);
                if (!gui.tflogin1.mouseOverTextField(this) && gui.tflogin1.selected) gui.tflogin1.keyPressed('0', ENTER);
                if (!gui.tflogin2.mouseOverTextField(this) && gui.tflogin2.selected) gui.tflogin2.keyPressed('0', ENTER);
            }
            else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
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
            }
            else if (gui.currentScreen == GUI.SCREEN.MAIN) {
                if (gui.q1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.QNA;
                if (gui.s1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SETTINGS;
                if (gui.m1.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
                if (gui.m2.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.OCVIEWER;
                if (gui.m3.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.PRELOGIN;
            }
            else if (gui.currentScreen == GUI.SCREEN.QNA) {
                if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            }
            else if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {
                if (gui.exit.mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.MAIN;
                    gui.page = 0;
                }
                if (gui.nav1.mouseOverButton(this) && gui.nav1.enabled) gui.page = 0;
                if (gui.nav2.mouseOverButton(this) && gui.nav2.enabled && gui.page > 0) gui.page--;
                if (gui.nav3.mouseOverButton(this) && gui.nav3.enabled && gui.page < 9) gui.page++;
                if (gui.nav4.mouseOverButton(this) && gui.nav4.enabled) gui.page = 9;

                for (int i = 15 * gui.page; i < Math.min(15 * (gui.page + 1), gui.scenes.size()); i++) {
                    if (gui.scenes.get(i).mouseOverButton(this)) {
                        if (gui.scenes.get(i).state != STATE.NULL) {
                            if (gui.scenes.get(i).state == STATE.PLUS) {
                                scenes.add(new Scene(scenes.size()));
                                gui.scenes.get(i).state = STATE.NORM;
                                scenes.get(i).name = gui.scenes.get(i).token;
                                gui.scenes.get(i + 1).state = STATE.PLUS;
                                gui.scenes.get(scenes.size()).updateSceneButton(scenes.size());
                            } else {
                                scenes.get(i).name = gui.scenes.get(i).token;
                                gui.scName.text = gui.scenes.get(i).text;
                                scene = scenes.get(i);
                                if (scene.nObjects == 0) {
                                    instanceZwolf();
                                }
                                redoSceneEditorValues();
                                gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
                                scene.selPage = 0;
                                sceneEditorInitialized = false;
                            }
                        }
                    }
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
                if (firstClick) {
                    if (scene.sel != Scene.scInstance.DISPLAY) {
                        if (gui.tfSelectSearch.text.equals("")) {
                            int start = scene.selPage * 10;
                            int end = Math.min(selects.length, start + 10);
                            for (int i = start; i < end; i++) {
                                if (selects[i].mouseOverButton(this) && selects[i].isEnabled) {
                                    addThisOC(selects[i].oc);
                                    selects[i].isEnabled = false;
                                }
                            }
                        } else {
                            if (searchedOC != null)
                                if (searchedOC.mouseOverButton(this) && searchedOC.isEnabled) {
                                    addThisOC(searchedOC.oc);
                                    selects[searchedOC.oc.ID].isEnabled = false;
                                    gui.tfSelectSearch.text = "";
                                }
                        }
                    }
                    gui.scName.isPressed(this);
                    if (!gui.scName.mouseOverTextField(this) && gui.scName.selected) gui.scName.keyPressed('0', ENTER);

                    if (gui.exit.mouseOverButton(this)) {
                        scene.sel = Scene.scInstance.DISPLAY;
                        scene.selPage = 0;

                        gui.tfSelectSearch.text = "";

                        gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
                        gui.scenes.get(scene.ID).text = gui.scName.text;

                        if (scene.nObjects == 0) {
                            deleteScene(scene);
                        }

                        scene = null;
                        firstClick = false;
                    }
                    if (gui.rsced1.mouseOverButton(this)) gui.gridon = !gui.gridon;

                    for (int i = 0; i < gui.tfsced.length; i++) {
                        if (i != 2 && i != 4) {
                            gui.tfsced[i].isPressed(this);
                            if (!gui.tfsced[i].mouseOverTextField(this) && gui.tfsced[i].selected) gui.tfsced[i].keyPressed('0', ENTER);
                        }
                    }

                    if (this.scene != null) {
                        if (scene.sel != Scene.scInstance.OCSELECT) {
                            for (int i = 1; i < gui.slSced.length; i++) {
                                if (i == 2 || i == 4) continue;
                                if (gui.slSced[i].mouseOnSlider(this, scene)) {
                                    gui.slSced[i].checkSlider(this, scene);
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
                        } else {
                            gui.tfSelectSearch.isPressed(this);
                        }

                        if (gui.sced1.mouseOverButton(this)) instanceOC();
                        if (gui.sced2.mouseOverButton(this)) scene.sel = Scene.scInstance.OCSELECT;

                        if (gui.sced4.enabled && gui.sced4.mouseOverButton(this)) {
                            if (scene.sel == Scene.scInstance.DISPLAY) {
                                if (scene.nObjects > 0) {
                                    scene.currentObject = (scene.currentObject + 1) % scene.nObjects;
                                    if (scene.stands[scene.currentObject] instanceof OC pHolder) {
                                        changeTFValues(pHolder);
                                        updateSlidersFromOC(pHolder);
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
                                        updateSlidersFromOC(pHolder);
                                        selectedSl = null;
                                        updateCalculatedValues();
                                    }
                                }
                            } else {
                                if (scene.selPage != 0) scene.selPage--;
                            }
                        }

                        if (gui.sced5.mouseOverButton(this) && gui.sced5.enabled) {
                            if (scene.sel == Scene.scInstance.OCSELECT) {
                                scene.sel = Scene.scInstance.DISPLAY;
                                gui.tfSelectSearch.text = "";
                            } else {
                                scene.deleteObject(scene.stands[scene.currentObject]);
                                if (scene.currentObject != -1) {
                                    OC pHolder = (OC) scene.stands[scene.currentObject];
                                    updateSlidersFromOC(pHolder);
                                    changeTFValues(pHolder);
                                }
                            }
                        }
                    }
                    if (scene != null) updateCalculatedValues();
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {
                gui.tfInfoSearch.isPressed(this);
                if (gui.nav1.mouseOverButton(this)) scedPage = 0;
                if (gui.nav2.mouseOverButton(this) && scedPage > 0) scedPage--;
                if (gui.nav3.mouseOverButton(this) && scedPage < (slabs.length - 1) / 5) scedPage++;
                if (gui.nav4.mouseOverButton(this)) scedPage = (slabs.length - 1) / 5;
                if (gui.exit.mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.MAIN;
                    gui.tfInfoSearch.text = "";
                    scedPage = 0;
                }
                for (int i = 0; i < nAllOCs; i++) {
                    if (slabs[i].delete.mouseOverButton(this) && slabs[i].page == scedPage) {
                        deleteOCfromBase(allOCs[i]);
                        break;
                    }
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.SETTINGS) {
                if (gui.sLang.mouseOverButton(this)) {
                    gui.sLang.toggle();
                    gui.lang = (gui.lang == LANG.ESP) ? LANG.ENG : LANG.ESP;
                    translateEverything();
                }
                if (gui.sCol.mouseOverButton(this)) {
                    gui.sCol.toggle();
                    Colors.switchMode();
                }
                if (gui.deleteEverything.mouseOverButton(this)) reset();
                if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            }
        }
        else if (mouseButton == RIGHT) {
            if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {
                for (int i = 15 * gui.page; i < Math.min(15 * (gui.page + 1), gui.scenes.size()); i++) {
                    if (gui.scenes.get(i).mouseOverButton(this)) {
                        if (gui.scenes.get(i).state == STATE.NORM) {
                            deleteScene(scenes.get(i));
                        }
                    }
                }
            }
        }
    }

    private void updateSlidersFromOC(OC pHolder) {
        gui.slSced[1].v = pHolder.tHeight;
        gui.slSced[2].v = pHolder.weight;
        gui.slSced[3].v = pHolder.BMI;
        gui.slSced[4].v = pHolder.tWidth;
        gui.slSced[5].v = pHolder.bhratio;
        gui.slSced[6].v = pHolder.age;
        gui.slSced[7].v = pHolder.r;
        gui.slSced[8].v = pHolder.g;
        gui.slSced[9].v = pHolder.b;
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
            if (scene.sel != Scene.scInstance.OCSELECT) {
                if (gui.scName.selected) gui.scName.keyPressed(key, keyCode);
                for (int i = 0; i < gui.tfsced.length; i++) {
                    if (i == 2 || i == 4) continue;
                    gui.tfsced[i].keyPressed(key, keyCode);
                    if (gui.tfsced[i].selected && keyCode == ENTER) {
                        String txt = gui.tfsced[i].getText().replace(',', '.');
                        if (txt.matches("\\d*(\\.\\d{0,3})?") && !txt.isEmpty()) {
                            if (i >= 6 && i < 10) {
                                int value = (int) constrain(Float.parseFloat(txt), gui.slSced[i].minV, gui.slSced[i].maxV);
                                gui.slSced[i].v = value;
                                gui.tfsced[i].setText(String.valueOf(value));
                            } else {
                                float value = constrain(Float.parseFloat(txt), gui.slSced[i].minV, gui.slSced[i].maxV);
                                gui.slSced[i].v = value;
                                gui.tfsced[i].setText(String.format("%.3f", value));
                            }
                        }
                    }
                }
                updateCalculatedValues();
            } else {
                if (gui.tfSelectSearch.selected) {
                    if (key != ',' && key != '.') {
                        gui.tfSelectSearch.keyPressed(key, keyCode);
                    }
                    scene.selPage = 0;
                    if (gui.tfSelectSearch.text.length() == 6) {
                        updateSearchArr(gui.tfSelectSearch.text);
                    }
                }
            }
        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER){
            if (gui.tfInfoSearch.selected) {
                gui.tfInfoSearch.keyPressed(key, keyCode);
                scedPage = 0;
            }
        }
    }

    public void mouseDragged() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            if (firstClick) {
                for (int i = 0; i < gui.slSced.length; i++) {
                    if (i == 2 || i == 4) continue;
                    if (gui.slSced[i] != null && gui.slSced[i].mouseDraggingOnSlider(this)) {
                        gui.slSced[i].checkSlider(this, scene);
                        if (i >= 7 && i < 10) gui.tfsced[i].setText(String.valueOf((int) gui.slSced[i].v));
                        else gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].v));
                    }
                }
            }
            updateCalculatedValues();
        } else if (gui.currentScreen == GUI.SCREEN.SETTINGS) {
            gui.slVolume.checkSlider(this);
        }
    }

    public void mouseReleased() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            updateCalculatedValues();
            if (!firstClick) firstClick = true;
        }
    }

    public void addNewOCtoBase(OC oc) {
        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs + 1);
        slabs = java.util.Arrays.copyOf(slabs, nAllOCs + 1);
        selects = java.util.Arrays.copyOf(selects, nAllOCs + 1);
        oc.ID = nAllOCs;
        allOCs[nAllOCs] = oc;
        slabs[nAllOCs] = new InfoSlab(oc, this);
        selects[nAllOCs] = new SelectSlab(this, oc);
        selects[nAllOCs].isEnabled = false;
        nAllOCs++;
    }

    public void deleteOCfromBase(OC oc) {
        int index = -1;
        for (int i = 0; i < nAllOCs; i++) {
            if (allOCs[i] == oc) {
                index = i;
                break;
            }
        }
        print(index);
        if (index == -1) return;

        for (Scene s : scenes) {
            if (s == null) continue;
            for (int i = s.nObjects - 1; i >= 0; i--) {
                Stand st = s.stands[i];
                if (st.equals(oc) || st.ID == oc.ID || st.uniqueID == oc.ID) {
                    s.deleteObject(oc);
                    if (s.currentObject != -1) {
                        OC pHolder = (OC) s.stands[s.currentObject];
                        updateSlidersFromOC(pHolder);
                        changeTFValues(pHolder);
                    }
                }
            }
        }

        for (int i = index; i < nAllOCs - 1; i++) {
            allOCs[i] = allOCs[i + 1];
            allOCs[i].ID--;
            slabs[i] = slabs[i + 1];
            slabs[i].page = (slabs[i].oc.ID) / 5;
            selects[i] = selects[i + 1];
        }
        nAllOCs--;
        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs);
        slabs = java.util.Arrays.copyOf(slabs, nAllOCs);
        selects = java.util.Arrays.copyOf(selects, nAllOCs);
    }

    public void deleteScene(Scene sc) {
        int index = -1;
        for (int i = 0; i < scenes.size(); i++) {
            if (sc.ID == scenes.get(i).ID) {
                index = i;
                break;
            }
        }
        if (index == -1) return;

        scenes.remove(index);
        gui.scenes.remove(index);

        for (int i = index; i < scenes.size(); i++) {
            scenes.get(i).ID = i;
            gui.scenes.get(i).updateSceneButton(i);
        }
        gui.scenes.get(scenes.size()).updateSceneButton(scenes.size());

        for (Scene s : scenes) {
            int k = s.ID % 5;
            int j = (s.ID / 5) % 3;
            gui.scenes.get(s.ID).x = k * 250 + 120;
            gui.scenes.get(s.ID).y = j * 212 + 200;
        }
    }

    public void reset() {
        allOCs = new OC[0];
        scenes = new ArrayList<>();
        slabs = new InfoSlab[0];
        selects = new SelectSlab[0];
        nAllOCs = 0;
        for (int i = 1; i < gui.scenes.size(); i++) {
            if (gui.scenes.get(i).state != STATE.NULL) {
                gui.scenes.get(i).state = STATE.NULL;
                gui.scenes.get(i).text = "{}";
            }
        }
        gui.scenes.getFirst().state = STATE.PLUS;
        gui.scenes.getFirst().text = "{}";
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

        gui.slSced[4].v = constrain((float)(height * Math.pow(bmi, 0.7979) / 81.906),
                (float)(height * Math.pow(1, 0.7979) / 81.906),
                (float)(height * Math.pow(250, 0.7979) / 81.906));
        gui.slSced[4].v = round(gui.slSced[4].v, 3);

        if (!gui.tfsced[4].selected) gui.tfsced[4].setText(String.format("%.3f", gui.slSced[4].v));
        if (!gui.tfsced[5].selected) gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));
        if (!gui.tfsced[6].selected) gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));

        if (selectedSl == gui.slSced[5]) {
            gui.tfsced[5].setEnabled(true);
            gui.tfsced[6].setEnabled(false);
            float ratio = gui.slSced[5].v;
            gui.slSced[6].v = Math.abs(-(1/0.0741f) * (float)Math.log((ratio - 0.125f) / 0.125f));
        } else if (selectedSl == gui.slSced[6]) {
            gui.tfsced[5].setEnabled(false);
            gui.tfsced[6].setEnabled(true);
            float age = gui.slSced[6].v;
            gui.slSced[5].v = 0.125f + 0.125f * (float)Math.exp(-0.0741f * age);
        }


        gui.slSced[6].v = constrain(Math.round(gui.slSced[6].v), 0, 80);
        if (!gui.tfsced[6].selected) gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));

        if (!gui.tfsced[7].text.isEmpty() && !gui.tfsced[8].text.isEmpty() && !gui.tfsced[9].text.isEmpty()) {
            if (Integer.parseInt(gui.tfsced[7].text) < 0 || Integer.parseInt(gui.tfsced[7].text) > 256) {
                gui.slSced[7].v = constrain(Math.round(gui.slSced[7].v), 0, 255);
                if (!gui.tfsced[7].selected) gui.tfsced[7].setText(String.format("%.0f", gui.slSced[7].v));
            }
            if (Integer.parseInt(gui.tfsced[8].text) < 0 || Integer.parseInt(gui.tfsced[7].text) > 256) {
                gui.slSced[8].v = constrain(Math.round(gui.slSced[8].v), 0, 255);
                if (!gui.tfsced[8].selected) gui.tfsced[8].setText(String.format("%.0f", gui.slSced[8].v));
            }
            if (Integer.parseInt(gui.tfsced[9].text) < 0 || Integer.parseInt(gui.tfsced[7].text) > 256) {
                gui.slSced[9].v = constrain(Math.round(gui.slSced[9].v), 0, 255);
                if (!gui.tfsced[9].selected) gui.tfsced[9].setText(String.format("%.0f", gui.slSced[9].v));
            }
        } else {
            if (gui.tfsced[7].text.isEmpty()){ gui.tfsced[7].text = "0"; gui.slSced[7].v = 0;}
            if (gui.tfsced[8].text.isEmpty()){ gui.tfsced[8].text = "0"; gui.slSced[8].v = 0;}
            if (gui.tfsced[9].text.isEmpty()){ gui.tfsced[9].text = "0"; gui.slSced[9].v = 0;}
        }




        gui.slSced[5].v = constrain(round(gui.slSced[5].v, 3), 0.125f, 0.25f);
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
        setZwolfDefaults(pHolder);
        pHolder.name = "Zwolf" + pHolder.ID;
        changeTFValues(pHolder);
        addNewOCtoBase(pHolder);
        scene.addObject(pHolder);

    }

    public void instanceZwolf() {
        OC pHolder = new OC(nAllOCs);
        setZwolfDefaults(pHolder);
        pHolder.ID = -1;
        pHolder.name = "Zwolf" + pHolder.ID;
        changeTFValues(pHolder);
        scene.addObject(pHolder);
    }

    private void setZwolfDefaults(OC pHolder) {
        gui.slSced[1].v = 1.83f;
        gui.slSced[2].v = (float) Math.pow(1.83f, 2) * 25;
        gui.slSced[3].v = 25;
        gui.slSced[4].v = (float) Math.pow(25, 0.7979) / 81.906f;
        gui.slSced[6].v = 25;
        gui.slSced[5].v = 0.1295f;
        gui.slSced[7].v = 127;
        gui.slSced[8].v = 127;
        gui.slSced[9].v = 127;

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

    public void addThisOC(OC pHolder) {
        scene.addObject(pHolder);
        updateSlidersFromOC(pHolder);
        gui.tfName.text = (pHolder.name);
        changeTFValues(pHolder);
    }

    public void checkSelectStatus() {
        for (int i = 0; i < nAllOCs; i++) {
            boolean found = false;
            for (int j = 0; j < scene.nObjects; j++) {
                if (scene.stands[j].equals(selects[i].oc) || scene.stands[j].ID == selects[i].oc.ID) {
                    found = true;
                    break;
                }
            }
            selects[i].isEnabled = !found;
        }
    }

    public void updateSearchArr(String str) {
        long idLong = Long.parseLong(str);

        boolean found = false;
        for (int i = 0; i < selects.length; i++) {
            if (selects[i].oc.uniqueID == idLong) {
                searchedOC = new SelectSlab (this, selects[i].oc);
                searchedOC.y = 132;
                found = true;
                break;
            }
        }
        if (!found) searchedOC = null;
    }


    private float round(float value, int decimals) {
        float factor = (float) Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    public void translateEverything() {
        int l = (gui.lang == LANG.ESP) ? 2 : 1;

        gui.plog1.text = Languages.translate("PLOG1", l);
        gui.plog2.text = Languages.translate("PLOG2", l);
        gui.login.text = Languages.translate("LOGIN", l);
        gui.signup.text = Languages.translate("SIGNUP", l);
        gui.deleteEverything.text = Languages.translate("RESET", l);

        gui.m1.text = Languages.translate("M1", l);
        gui.m2.text = Languages.translate("M2", l);
        gui.m3.text = Languages.translate("M3", l);
        gui.rsced1.text = Languages.translate("GRID", l);
        gui.rsced2.text = Languages.translate("SCREENSHOT", l);

        if (gui.exit != null) gui.exit.text = Languages.translate("EXIT", l);

        gui.tfsignup1.text = Languages.translate("USERNAME", l);
        gui.tfsignup2.text = Languages.translate("EMAIL", l);
        gui.tfsignup3.text = Languages.translate("PASSWORD(1)", l);
        gui.tfsignup4.text = Languages.translate("PASSWORD(2)", l);
        gui.tfsignup1.trueText = Languages.translate("USERNAME", l);
        gui.tfsignup2.trueText = Languages.translate("EMAIL", l);
        gui.tfsignup3.trueText = Languages.translate("PASSWORD(1)", l);
        gui.tfsignup4.trueText = Languages.translate("PASSWORD(2)", l);

        gui.tflogin1.text = Languages.translate("USERNAME", l);
        gui.tflogin2.text = Languages.translate("PASSWORD", l);
        gui.tflogin1.trueText = Languages.translate("USERNAME", l);
        gui.tflogin2.trueText = Languages.translate("PASSWORD", l);


        gui.slVolume.s = Languages.translate("SOUND", l);
        gui.slHeight.s = Languages.translate("HEIGHT", l);
        gui.slWeight.s = Languages.translate("WEIGHT", l);
        gui.slBMI.s = Languages.translate("BMI", l);
        gui.slWidth.s = Languages.translate("WIDTH", l);
        gui.slBHRatio.s = Languages.translate("BHRATIO", l);
        gui.slAge.s = Languages.translate("AGE", l);
        gui.slRed.s = Languages.translate("R", l);
        gui.slGreen.s = Languages.translate("G", l);
        gui.slBlue.s = Languages.translate("B", l);

        gui.tfHeight.text = Languages.translate("HEIGHT", l);
        gui.tfWeight.text = Languages.translate("WEIGHT", l);
        gui.tfBMI.text = Languages.translate("BMI", l);
        gui.tfWidth.text = Languages.translate("WIDTH", l);
        gui.tfBHRatio.text = Languages.translate("BHRATIO", l);
        gui.tfAge.text = Languages.translate("AGE", l);
        gui.tfRed.text = Languages.translate("R", l);
        gui.tfGreen.text = Languages.translate("G", l);
        gui.tfBlue.text = Languages.translate("B", l);
    }
}