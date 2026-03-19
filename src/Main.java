import database.Database;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Comparator;


public class Main extends PApplet {

    Database b;
    String username;
    String email;

    Fonts fonts;
    GUI gui;
    boolean sceneEditorInitialized = false;
    public Slider selectedSl;
    public int scedPage = 0;
    public ArrayList<Scene> scenes = new ArrayList<>();
    public Scene scene;
    public Stand[] allStands = new Stand[3];
    public OC[] allOCs;
    public InfoSlab[] infos = new InfoSlab[0];
    public InfoSlab[] searchInfos = new InfoSlab[0];
    public SelectSlab[] selects = new SelectSlab[0];
    public SelectSlab[] searchSelects;

    public boolean firstClick = false;
    public int nAllOCs = 0;
    PImage bLogo;
    PImage wLogo;
    public int captures = 0;
    OC pendingDeleteOC = null;
    Scene pendingDeleteSc = null;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        fullScreen();
        smooth(100);
        allOCs = new OC[0];
    }

    public void setup() {
        b = new Database("admin", "12345", "ocbase");
        b.connect();


        fonts = new Fonts(this);
        gui = new GUI(this);
        try {
            Languages.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        translateEverything();
        Sounds.instanceSounds();

        gui.tfsced[2].setEnabled(false);
        gui.tfsced[4].setEnabled(false);
        gui.slSced[2].setEnabled(false);
        gui.slSced[4].setEnabled(false);
        bLogo = loadImage("data/ocblack.png");
        wLogo = loadImage("data/ocwhite.png");
        for (int i = 1; i < 4;i++){
            allStands[i-1] = new Stand();
            allStands[i-1].uniqueID = -i;
            allStands[i-1].ID = -i;
            allStands[i-1].tHeight = Float.parseFloat(b.getInfo("stand", "tHeight", "ID", "-"+i));
            allStands[i-1].tWidth = Float.parseFloat(b.getInfo("stand", "tWidth", "ID", "-"+i));
            allStands[i-1].name = b.getInfo("stand", "name", "ID", "-"+i);
        }
        allStands[0].pic = loadImage("data/Bananana.png");;
        allStands[1].pic = loadImage("data/gabinett.png");;
        allStands[2].pic = loadImage("data/door.jpg");
    }


    public void draw() {
        if (!gui.rsced2.mouseOverButton(this)) {
            gui.phase = 255 * PApplet.sin(frameCount * 0.1f);
        } else {
            gui.phase = 0;
        }
        switch (gui.currentScreen) {
            case PRELOGIN -> {
                if (gui.sCol.on) {
                    gui.drawPRELOGIN(this, bLogo);
                } else {
                    gui.drawPRELOGIN(this, wLogo);
                }
            }
            case LOGIN -> gui.drawLOGIN(this);
            case SIGNUP -> gui.drawSIGNUP(this);
            case MAIN -> {
                if (gui.sCol.on) {
                    gui.drawMAIN(this, wLogo);
                } else {
                    gui.drawMAIN(this, bLogo);
                }
                pushStyle();
                textFont(Fonts.getThisFont(0));
                textAlign(CENTER);
                text(username, 1720, 40);
                popStyle();
            }
            case QNA -> gui.drawQNA(this);
            case SCENESELECTOR -> {
                refactorSceneButtons();
                gui.drawSCENESELECTOR(this);

                for (int i = 0; i < scenes.size(); i++) {
                    Scene sc = scenes.get(i);

                    if (sc == null || gui.scenes.get(i).state != STATE.NORM) continue;

                    if (sc.nObjects == 0) {
                        gui.scenes.get(i).cText = 8;
                    } else {
                        gui.scenes.get(i).cText = 6;
                    }
                }

                for (int i = 15 * gui.page; i < Math.min(15 * (gui.page + 1), scenes.size()); i++) {
                    gui.scenes.get(i).display(this, scenes.get(i), gui.lang == LANG.ENG ? 1 : 2);
                }
                int totalSlots = scenes.size() + 1;
                int maxPage = (totalSlots - 1) / 15;

                gui.nav1.setEnabled(gui.page > 0);
                gui.nav2.setEnabled(gui.page > 0);
                gui.nav3.setEnabled(gui.page < maxPage);
                gui.nav4.setEnabled(gui.page < maxPage);
            }

            case SCENEEDITOR -> {
                gui.drawSCENEEDITOR(this, scene);

                pushStyle();
                textSize(24);
                fill(0);
                if (scene.sel != Scene.scInstance.OCSELECT && scene.nObjects != 0 && scene.stands[scene.currentObject] instanceof OC) {
                    String txtWeight = (gui.lang == LANG.ESP) ? "Peso(kg): " : "Weight(kg): ";
                    String txtWidth  = (gui.lang == LANG.ESP) ? "Ancho(m): " : "Width(m): ";

                    text(txtWeight, 37.5f, 309);
                    text(txtWidth,  37.5f, 489);
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
                    gui.sced5.setEnabled(true);
                    gui.tfSelectSearch.setEnabled(true);

                    int start = scene.selPage * 10;
                    int end;

                    if (gui.tfSelectSearch.text.equals("")) {
                        end = Math.min(selects.length, start + 10);
                        for (int i = start; i < end; i++) {
                            selects[i].display(this);
                        }
                    } else {
                        end = Math.min(searchSelects.length, start + 10);
                        for (int i = start; i < end; i++) {
                            searchSelects[i].display(this);
                        }
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
                pushStyle();
                for (int i = 0; i < scene.stands.length; i++) {
                    Stand s = scene.stands[i];
                    if (s instanceof OC oc) {
                        oc.display(this, scene);
                    } else {
                        s.display(this);
                    }
                }
                if (gui.delSc.on){
                    background(Colors.getThisColor(8), 50);
                    gui.delSc.display(this, "", gui.lang == LANG.ESP? 2 : 1);
                }
                popStyle();

            }
            case OCVIEWER -> {
                gui.drawOCVIEWER(this);

                InfoSlab[] currentList;
                if (gui.tfInfoSearch.text.isEmpty()) {
                    currentList = infos;
                } else {
                    currentList = searchInfos;
                }

                int totalSlots = currentList.length;
                int maxPage = (totalSlots == 0) ? 0 : (totalSlots - 1) / 5;

                if (scedPage > maxPage) scedPage = maxPage;

                gui.nav1.setEnabled(scedPage > 0);
                gui.nav2.setEnabled(scedPage > 0);
                gui.nav3.setEnabled(scedPage < maxPage);
                gui.nav4.setEnabled(scedPage < maxPage);

                int start = scedPage * 5;
                int end = Math.min(currentList.length, start + 5);

                for (int i = start; i < end; i++) {
                    currentList[i].display(this);
                }

                if (gui.delOC.on){
                    background(Colors.getThisColor(8), 50);
                    gui.delOC.display(this, pendingDeleteOC.name, gui.lang == LANG.ESP? 2 : 1);
                }
            }
            case SETTINGS -> {
                gui.drawSETTINGS(this);
                if (gui.delAll.on){
                    background(255,0,0);
                    if (gui.delAll.yes.mouseOverButton(this)) {
                        gui.delAll.yes.x = 675 + random(-1, 1);
                        gui.delAll.yes.y = 600 + random(-1, 1);
                    } else {
                        gui.delAll.yes.x = 675;
                        gui.delAll.yes.y = 600;
                    }
                    gui.delAll.display(this, "", gui.lang == LANG.ESP?2:1);
                }
                if (gui.delDelAll.on){
                    background(127, 0, 0);
                    if (gui.delDelAll.yes.mouseOverButton(this)) {
                        gui.delDelAll.yes.x = 675 + random(-2, 2);
                        gui.delDelAll.yes.y = 600 + random(-2, 2);
                    } else {
                        gui.delDelAll.yes.x = 675;
                        gui.delDelAll.yes.y = 600;
                    }
                    gui.delDelAll.display(this, "", gui.lang == LANG.ESP?2:1);
                }
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

    public void redoSceneEditorValues() {
        if (scene.currentObject != -1) {
            if (scene.stands[scene.currentObject] instanceof OC){
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
    }

    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
                if (gui.plog1.mouseOverButton(this)) {gui.currentScreen = GUI.SCREEN.LOGIN; }
                if (gui.plog2.mouseOverButton(this)) {gui.currentScreen = GUI.SCREEN.SIGNUP; }
                if (gui.exit.mouseOverButton(this)) exit();
            }
            else if (gui.currentScreen == GUI.SCREEN.LOGIN) {
                if (gui.exit.mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.PRELOGIN;

                }
                if (gui.login.mouseOverButton(this)) {
                    String input = gui.tflogin1.text;
                    String pass = gui.tflogin2.text;

                    if (pass.equals(b.getInfo("user", "password", "email", input))) {
                        username = b.getInfo("user", "username", "email", input);
                        email = input;
                        retrieve(email);
                        gui.currentScreen = GUI.SCREEN.MAIN;
                        gui.tflogin1.text = gui.tflogin1.trueText;
                        gui.tflogin2.text = gui.tflogin2.trueText;
                    }
                    else if (pass.equals(b.getInfo("user", "password", "username", input))) {
                        username = input;
                        email = b.getInfo("user", "email", "username", input);
                        retrieve(email);
                        gui.currentScreen = GUI.SCREEN.MAIN;
                        gui.tflogin1.text = gui.tflogin1.trueText;
                        gui.tflogin2.text = gui.tflogin2.trueText;
                    }
                    else {
                        print("INCORRECT PASSWORD");
                    }
                }
                gui.tflogin1.isPressed(this);
                gui.tflogin2.isPressed(this);
                if (!gui.tflogin1.mouseOverTextField(this) && gui.tflogin1.selected) gui.tflogin1.keyPressed('0', ENTER);
                if (!gui.tflogin2.mouseOverTextField(this) && gui.tflogin2.selected) gui.tflogin2.keyPressed('0', ENTER);
            }
            else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
                if (gui.exit.mouseOverButton(this)) {gui.currentScreen = GUI.SCREEN.PRELOGIN;}

                if (gui.signup.mouseOverButton(this)) {
                    String user = gui.tfsignup1.text;
                    String mail = gui.tfsignup2.text;
                    String pass1 = gui.tfsignup3.text;
                    String pass2 = gui.tfsignup4.text;

                    if (mail.isEmpty() || user.isEmpty() || pass1.isEmpty()) {
                        println("Error: Campos vacíos");
                    }
                    else if (!isValidEmail(mail)) {
                        println("Error: El formato del E-mail no es válido.");
                    }
                    else if (user.contains("@")) {
                        println("Error: El nombre de usuario no puede contener '@'");
                    }
                    else if (!pass1.equals(pass2)) {
                        println("Error: Las contraseñas no coinciden");
                    }
                    else if (b.exists("user", "email", mail)) {
                        println("Error: El email ya está registrado");
                    }
                    else if (b.exists("user", "username", user)) {
                        println("Error: El nombre de usuario ya existe");
                    }
                    else {
                        b.signup(mail, user, pass1);
                        this.username = user;
                        this.email = mail;
                        gui.currentScreen = GUI.SCREEN.MAIN;
                        gui.tfsignup1.text = gui.tfsignup1.trueText;
                        gui.tfsignup2.text = gui.tfsignup2.trueText;
                        gui.tfsignup3.text = gui.tfsignup3.trueText;
                        gui.tfsignup4.text = gui.tfsignup4.trueText;
                    }
                }

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
                if (gui.m2.mouseOverButton(this)) {gui.currentScreen = GUI.SCREEN.OCVIEWER; Sounds.emit(6);}
                if (gui.m3.mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.PRELOGIN;
                    username = null;
                    email = null;
                    scenes = new ArrayList<>();
                    allOCs = new OC[0];
                    infos = new InfoSlab[0];
                    selects = new SelectSlab[0];
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.QNA) {
                if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
            }
            else if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {
                if (gui.exit.mouseOverButton(this)) {
                    gui.currentScreen = GUI.SCREEN.MAIN;
                    gui.page = 0;
                }
                if (gui.nav1.mouseOverButton(this) && gui.nav1.enabled) {gui.page = 0;Sounds.emit(2);}
                if (gui.nav2.mouseOverButton(this) && gui.nav2.enabled && gui.page > 0) {gui.page--;Sounds.emit(3);}
                if (gui.nav3.mouseOverButton(this) && gui.nav3.enabled && gui.page < 9) {gui.page++;Sounds.emit(4);}
                if (gui.nav4.mouseOverButton(this) && gui.nav4.enabled) {gui.page = scenes.size()/15; Sounds.emit(5);}

                for (int i = 15 * gui.page; i < Math.min(15 * (gui.page + 1), gui.scenes.size()); i++) {
                    if (gui.scenes.get(i).mouseOverButton(this)) {
                        if (gui.scenes.get(i).state != STATE.NULL) {
                            Sounds.emit(6);
                            if (gui.scenes.get(i).state == STATE.PLUS) {

                                Scene newSc = new Scene(scenes.size());
                                scenes.add(newSc);
                                b.newScene(newSc.uniqueID, newSc.ID, email);
                                gui.scenes.get(i).state = STATE.NORM;
                                scenes.get(i).name = gui.scenes.get(i).token;
                                gui.scenes.get(i + 1).state = STATE.PLUS;
                                gui.scenes.get(scenes.size() - 1).updateSceneButton(scenes.size() - 1);
                                if (scenes.size() < gui.scenes.size()) {
                                    gui.scenes.get(scenes.size()).updateSceneButton(scenes.size());
                                }
                            } else {
                                scenes.get(i).name = gui.scenes.get(i).token;
                                scene = scenes.get(i);
                                redoSceneEditorValues();
                                gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
                                scene.selPage = 0;
                                sceneEditorInitialized = false;
                            }
                            break;
                        }
                    }
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
                updateCalculatedValues();
                if (firstClick) {
                    if (gui.cPickOn){
                        gui.cPick.mousePressed(this);
                    }
                    if (scene.sel != Scene.scInstance.DISPLAY) {
                        if (gui.tfSelectSearch.text.equals("")) {
                            int start = scene.selPage * 10;
                            int end = Math.min(selects.length, start + 10);
                            for (int i = start; i < end; i++) {
                                if (selects[i].mouseOverButton(this) && selects[i].isEnabled) {
                                    addThisOC(selects[i].oc);
                                    b.insert("oc_has_scene", "OC_UniqueID, Scene_UniqueID, Pos",(selects[i].oc.uniqueID) + ", " + (scene.uniqueID) + ", " + (scene.nObjects-1));
                                    selects[i].isEnabled = false;
                                }
                            }
                        } else {
                            int start = scene.selPage * 10;
                            int end = Math.min(searchSelects.length, start + 10);
                            for (int i = start; i < end; i++) {
                                if (searchSelects[i].mouseOverButton(this) && searchSelects[i].isEnabled) {
                                    addThisOC(searchSelects[i].oc);
                                    searchSelects[i].isEnabled = false;
                                }
                            }
                        }
                    }

                    if (gui.exit.mouseOverButton(this)) {
                        updateCalculatedValues();

                        if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC pHolder) {
                            updateOCBases(pHolder);
                        }

                        Sounds.emit(7);
                        scene.sel = Scene.scInstance.DISPLAY;
                        scene.selPage = 0;
                        gui.cPickOn = false;
                        gui.tfSelectSearch.text = "";
                        gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
                        scene = null;
                        firstClick = false;
                    }
                    if (gui.rsced1.mouseOverButton(this)) gui.gridon = !gui.gridon;
                    if (gui.rsced2.mouseOverButton(this)){
                        gui.phase = 0;
                        PImage ss = get(scene.scX, scene.scY, scene.scW, scene.scH);
                        ss.save(scene.name+captures+".png");
                        captures++;
                    }
                    if (gui.rsced3.mouseOverButton(this)) copyScene();
                    if (gui.delSc.on) {
                        int result = gui.delSc.uSure(this);

                        if (result == 1) {
                            deleteScene(scene);
                            Sounds.emit(9);
                            gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
                            pendingDeleteSc = null;

                        } else if (result == 2) {
                            pendingDeleteSc = null;
                        }
                    } else if (gui.rsced4.mouseOverButton(this)) {
                        gui.delSc.activate();
                        Sounds.emit(8);
                        pendingDeleteSc = scene;
                    }

                    for (int i = 0; i < gui.tfsced.length; i++) {
                        if (i != 2 && i != 4) {
                            gui.tfsced[i].isPressed(this);
                            if (!gui.tfsced[i].mouseOverTextField(this) && gui.tfsced[i].selected) {
                                gui.tfsced[i].selected = false;
                                updateCalculatedValues();
                                if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC pHolder) {
                                    updateOCBases(pHolder);
                                }
                            }
                        }
                    }

                    if (this.scene != null) {
                        if (scene.sel != Scene.scInstance.OCSELECT) {
                            if (selectedSl != null) {
                                selectedSl.checkSlider(this, scene);
                                for (int i = 1; i < gui.slSced.length; i++) {
                                    if (gui.slSced[i] == selectedSl) {
                                        if (i >= 7 && i < 10) gui.tfsced[i].setText(String.valueOf((int) selectedSl.v));
                                        else gui.tfsced[i].setText(String.format("%.2f", selectedSl.v));
                                        break;
                                    }
                                }
                            } else {
                                for (int i = 1; i < gui.slSced.length; i++) {
                                    if (i == 2 || i == 4) continue;

                                    if (gui.slSced[i].mouseOnSlider(this, scene)) {
                                        selectedSl = gui.slSced[i];
                                        selectedSl.checkSlider(this, scene);
                                        break;
                                    } else {
                                        String txt = gui.tfsced[i].getText().replace(',', '.');
                                        if (txt.matches("\\d*(\\.\\d{0,2})?") && !txt.isEmpty()) {
                                            float value = Float.parseFloat(txt);
                                            value = constrain(value, gui.slSced[i].minV, gui.slSced[i].maxV);

                                            if (i >= 6 && i < 10) {
                                                gui.slSced[i].v = (int) value;
                                                gui.tfsced[i].setText(String.valueOf((int) value));
                                            } else {
                                                gui.slSced[i].v = value;
                                                gui.tfsced[i].setText(String.format("%.2f", value));
                                            }
                                        }
                                        updateCalculatedValues();
                                    }
                                }
                            }
                        } else {
                            gui.tfSelectSearch.isPressed(this);
                        }
                        if (gui.rsced0.mouseOverButton(this)) gui.cPickOn = !gui.cPickOn;
                        if (gui.sced1.mouseOverButton(this)) {instanceOC();}
                        if (gui.sced2.mouseOverButton(this) && nAllOCs != 0) scene.sel = Scene.scInstance.OCSELECT;

                        if (gui.sced4.enabled && gui.sced4.mouseOverButton(this)) {
                            Sounds.emit(4);
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
                            Sounds.emit(3);
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
                                Stand toDelete = scene.stands[scene.currentObject];
                                if (toDelete instanceof OC) {
                                    b.deleteOCFromScene(String.valueOf(scene.uniqueID), String.valueOf(toDelete.uniqueID));
                                } else {
                                    b.deleteStandFromScene(String.valueOf(scene.uniqueID), String.valueOf(toDelete.uniqueID));
                                }

                                scene.deleteObject(toDelete);

                                for (int i = 0; i < scene.nObjects; i++) {
                                    Stand s = scene.stands[i];
                                    if (s instanceof OC) {
                                        b.updateOCPos(i, scene.uniqueID, s.uniqueID);
                                    } else {
                                        b.updateStandPos(i, scene.uniqueID, s.uniqueID);
                                    }
                                }
                                if (scene.currentObject != -1) {
                                    Stand current = scene.stands[scene.currentObject];
                                    if (current instanceof OC pHolder) {
                                        updateSlidersFromOC(pHolder);
                                        changeTFValues(pHolder);
                                    }
                                }
                            }
                        }
                    }
                    if (scene != null) updateCalculatedValues();
                }
            } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {
                gui.tfInfoSearch.isPressed(this);

                InfoSlab[] currentList = gui.tfInfoSearch.text.isEmpty() ? infos : searchInfos;
                int maxPage = (currentList.length == 0) ? 0 : (currentList.length - 1) / 5;

                if (gui.nav1.mouseOverButton(this)) {scedPage = 0;Sounds.emit(2);}
                if (gui.nav2.mouseOverButton(this) && scedPage > 0) {scedPage--;Sounds.emit(3);}
                if (gui.nav3.mouseOverButton(this) && scedPage < maxPage) {scedPage++;Sounds.emit(4);}
                if (gui.nav4.mouseOverButton(this)) {scedPage = maxPage;Sounds.emit(5);}

                if (gui.exit.mouseOverButton(this)) {
                    Sounds.emit(7);
                    gui.currentScreen = GUI.SCREEN.MAIN;
                    gui.tfInfoSearch.text = "";
                    scedPage = 0;
                }

                if (gui.delOC.on) {
                    int result = gui.delOC.uSure(this);
                    if (result == 1 && pendingDeleteOC != null) {
                        deleteOCfromBase(pendingDeleteOC);
                        Sounds.emit(9);
                        if (!gui.tfInfoSearch.text.isEmpty()) {
                            updateInfoSearchArr(gui.tfInfoSearch.text);
                        }
                        pendingDeleteOC = null;
                    } else if (result == 2) {
                        pendingDeleteOC = null;
                    }
                } else {
                    int start = scedPage * 5;
                    int end = Math.min(currentList.length, start + 5);

                    for (int i = start; i < end; i++) {
                        if (currentList[i].delete.mouseOverButton(this)) {
                            pendingDeleteOC = currentList[i].oc;
                            gui.delOC.activate();
                            Sounds.emit(8);
                            break;
                        }
                    }
                }
            }
            else if (gui.currentScreen == GUI.SCREEN.SETTINGS) {
                if (!gui.delDelAll.on && !gui.delAll.on) {
                    if (gui.sLang.mouseOverButton(this)) {
                        gui.sLang.toggle();
                        gui.lang = (gui.lang == LANG.ESP) ? LANG.ENG : LANG.ESP;
                        translateEverything();
                    }
                    if (gui.sCol.mouseOverButton(this)) {
                        gui.sCol.toggle();
                        Colors.switchMode();
                    }
                }

                if (gui.delDelAll.on) {
                    int result = gui.delDelAll.uSure(this);

                    if (result == 1) {
                        Sounds.emit(16);
                        reset();
                    } else if (result == 2){
                        gui.delDelAll.on = false;
                        gui.delDelAll.yes.y -= 40;
                    }
                } else if (gui.delAll.on) {
                    int result = gui.delAll.uSure(this);

                    if (result == 1) {
                        Sounds.emit(15);
                        gui.delDelAll.on = true;
                        gui.delDelAll.yes.y += 40;
                    } else if (result == 2){
                        gui.delAll.on = false;
                    }
                } else if (gui.nuke.mouseOverButton(this)) {
                    Sounds.emit(14);
                    gui.delAll.activate();
                }
                if (gui.exit.mouseOverButton(this)) gui.currentScreen = GUI.SCREEN.MAIN;
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
                boolean anyTextFieldSelected = false;

                for (int i = 0; i < gui.tfsced.length; i++) {
                    if (i == 2 || i == 4) continue;

                    if (gui.tfsced[i].selected) {
                        anyTextFieldSelected = true;
                        gui.tfsced[i].keyPressed(key, keyCode);
                        if (keyCode == ENTER) {
                            String txt = gui.tfsced[i].getText().replace(',', '.').trim();
                            try {
                                if (!txt.isEmpty()) {
                                    if (i >= 6) {
                                        int val = (int) Float.parseFloat(txt);
                                        val = (int) constrain(val, gui.slSced[i].minV, gui.slSced[i].maxV);
                                        gui.slSced[i].v = val;
                                        if (i == 6) {
                                            gui.slSced[5].v = 8.0f / (1.0f + (float)Math.exp(-0.0741f * val));
                                            gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));
                                        }
                                    }
                                    else {
                                        float val = Float.parseFloat(txt);
                                        val = constrain(val, gui.slSced[i].minV, gui.slSced[i].maxV);
                                        gui.slSced[i].v = val;
                                        if (i == 5) {
                                            float ratio = val;
                                            if (ratio >= 7.99f) ratio = 7.99f;
                                            if (ratio <= 0.1f) ratio = 0.1f;
                                            gui.slSced[6].v = (float)Math.log(ratio / (8.0f - ratio)) / 0.0741f;

                                            gui.slSced[6].v = constrain(Math.round(gui.slSced[6].v), 0, 80);
                                            gui.tfsced[6].setText(String.valueOf((int)gui.slSced[6].v));
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                println("Error: Entrada no numérica en campo " + i);
                            }
                            if (i != 5 && i != 6) {
                                gui.tfsced[i].selected = false;
                            }
                            updateCalculatedValues();
                            if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC pHolder) {
                                updateOCBases(pHolder);
                            }
                        }
                    }
                }
                if (!anyTextFieldSelected) {
                    int index = -1;
                    if (key >= '1' && key <= '9') index = key - '1';
                    if (key == '0') index = 9;
                    if (index >= 0 && index < allStands.length) {
                        if (scene.isInScene(allStands[index].uniqueID)) {
                            scene.addObject(allStands[index]);
                            b.insert("scene_has_stand", "Scene_UniqueID, Stand_UniqueID, Pos", String.valueOf(scene.uniqueID) + ", " + String.valueOf(-(key-48)) + ", " + String.valueOf(scene.currentObject));
                        }
                    }
                }
            } else {
                if (gui.tfSelectSearch.selected) {
                    gui.tfSelectSearch.keyPressed(key, keyCode);
                    if (!gui.tfSelectSearch.text.isEmpty()) {
                        updateSelectSearchArr(gui.tfSelectSearch.text);
                    }
                    scene.selPage = 0;
                }
            }
        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER){
            if (gui.tfInfoSearch.selected) {
                gui.tfInfoSearch.keyPressed(key, keyCode);
                if (!gui.tfInfoSearch.text.isEmpty()) updateInfoSearchArr(gui.tfInfoSearch.text);
                scedPage = 0;
            }
        }
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR){
            boolean isTfOn = false;

            for (int i = 0; i < gui.tfsced.length; i++) {
                if (gui.tfsced[i].selected) {
                    print(i + " ");
                    isTfOn = true;
                }
            }
            print(isTfOn);

            if (gui.tfSelectSearch.selected) {
                isTfOn = true;
            }
            if (!isTfOn) {
                int index = -1;
                if (key >= '1' && key <= '9') index = key - '1';
                if (key == '0') index = 9;
                if (index >= 0 && index < allStands.length) {
                    if (scene.isInScene(allStands[index].uniqueID)) {
                        scene.addObject(allStands[index]);
                        b.insert("scene_has_stand", "Scene_UniqueID, Stand_UniqueID, Pos", String.valueOf(scene.uniqueID) + ", " + String.valueOf(-(key - 48)) + ", " + String.valueOf(scene.currentObject));
                    }
                }
            }
        }
    }

    public void mouseDragged() {

        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            if (firstClick) {
                Slider selectedSl = null;
                for (int i = 0; i < gui.slSced.length; i++) {
                    if (i == 2 || i == 4) continue;
                    if (gui.slSced[i] != null && gui.slSced[i].mouseOnSlider(this)) {
                        selectedSl = gui.slSced[i];
                        gui.slSced[i].checkSlider(this, scene);
                        if (i >= 7 && i < 10) gui.tfsced[i].setText(String.valueOf((int) gui.slSced[i].getValue()));
                        else gui.tfsced[i].setText(String.format("%.2f", gui.slSced[i].getValue()));
                    }
                }
                if (selectedSl == gui.slSced[5]) {
                    gui.tfsced[5].setEnabled(true);
                    gui.tfsced[6].setEnabled(false);
                    float ratio = gui.slSced[5].getValue();
                    if (ratio >= 7.99f) ratio = 7.99f;
                    if (ratio <= 4.01f) ratio = 4.01f;
                    float age = (float)Math.log((ratio - 4.0f) / (8.0f - ratio)) / 0.0741f;
                    gui.slSced[6].setValue(age);
                    gui.tfsced[6].setText(String.format("%.2f", gui.slSced[6].getValue()));
                }
                else if (selectedSl == gui.slSced[6]) {
                    gui.tfsced[5].setEnabled(false);
                    gui.tfsced[6].setEnabled(true);
                    float age = gui.slSced[6].getValue();
                    float ratio = 4.0f + 4.0f / (1.0f + (float)Math.exp(-0.0741f * age));
                    gui.slSced[5].setValue(ratio);
                    gui.tfsced[5].setText(String.format("%.2f", gui.slSced[5].getValue()));
                }
            }
            updateCalculatedValues();
            if (gui.cPickOn) {
                gui.cPick.mouseDragged(this);
            }
        }
        else if (gui.currentScreen == GUI.SCREEN.SETTINGS) {
            gui.slVolume.checkSlider(this);
            Sounds.redoAmp(gui.slVolume.v);
        }
    }

    public void mouseReleased() {
        if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            updateCalculatedValues();
            if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC pHolder) {
                updateOCBases(pHolder);
            }
            if (!firstClick) firstClick = true;
            if (gui.cPickOn){
                gui.cPick.mouseReleased();
            }
            selectedSl = null;
        }
    }

    public void addNewOCtoBase(OC oc) {

        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs + 1);
        infos = java.util.Arrays.copyOf(infos, nAllOCs + 1);
        selects = java.util.Arrays.copyOf(selects, nAllOCs + 1);
        oc.ID = nAllOCs;
        allOCs[nAllOCs] = oc;
        infos[nAllOCs] = new InfoSlab(oc, this);
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

        if (index == -1) return;

        for (Scene s : scenes) {
            if (s == null) continue;
            boolean removed = false;

            for (int i = s.nObjects - 1; i >= 0; i--) {
                Stand st = s.stands[i];
                if (st.uniqueID == oc.uniqueID) {
                    b.deleteOCFromScene(String.valueOf(s.uniqueID), String.valueOf(st.uniqueID));
                    s.deleteObject(st);
                    removed = true;
                }
            }

            if (removed) {
                for (int k = 0; k < s.nObjects; k++) {
                    Stand t = s.stands[k];
                    if (t instanceof OC) {
                        b.updateOCPos(k, s.uniqueID, t.uniqueID);
                    } else {
                        b.updateStandPos(k, s.uniqueID, t.uniqueID);
                    }
                }

                if (s == this.scene && s.currentObject != -1) {
                    if (s.stands[s.currentObject] instanceof OC pHolder) {
                        updateSlidersFromOC(pHolder);
                        changeTFValues(pHolder);
                    }
                }
            }
        }

        b.delete("oc", "UniqueID", String.valueOf(oc.uniqueID));

        for (int i = index; i < nAllOCs - 1; i++) {
            allOCs[i] = allOCs[i + 1];
            infos[i] = infos[i + 1];
            selects[i] = selects[i + 1];
        }

        nAllOCs--;

        allOCs = java.util.Arrays.copyOf(allOCs, nAllOCs);
        infos = java.util.Arrays.copyOf(infos, nAllOCs);
        selects = java.util.Arrays.copyOf(selects, nAllOCs);

        defragment();

        if (!gui.tfInfoSearch.text.isEmpty()) {
            updateInfoSearchArr(gui.tfInfoSearch.text);
        }
        if (!gui.tfSelectSearch.text.isEmpty()) {
            updateSelectSearchArr(gui.tfSelectSearch.text);
        }
    }

    public void defragment() {
        for (int i = 0; i < nAllOCs; i++) {

            allOCs[i].ID = i;

            infos[i].ID = i % 5;
            infos[i].page = i / 5;
            infos[i].x = 720 + ((infos[i].ID - 1)) * 300;

            selects[i].ID = i % 10;
            selects[i].page = i / 10;
            selects[i].y = 198 + (selects[i].ID * 87);
        }
    }

    public void copyScene(){
        Sounds.emit(17);
        Scene newSc = new Scene(this.scene);
        scenes.add(newSc);
        b.newScene(newSc.uniqueID, newSc.ID, email);
        this.scene = newSc;
        for (int i = 0; i < scene.nObjects;i++){
            b.insert("oc_has_scene", "OC_UniqueID, Scene_UniqueID, Pos", scene.stands[i].uniqueID + ", " + scene.uniqueID + ", " + i);
        }
        for (int i = 0; i < scenes.size();i++){
            scenes.get(i).ID = i;
        }
        gui.scenes.get(scenes.size()-1).state = STATE.NORM;
        gui.scenes.get(scenes.size()).state = STATE.PLUS;
        gui.scenes.get(scenes.size()).updateSceneButton(scenes.size());
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

        scene = sc;
        for (int i = 0; i < scene.nObjects;i++) {
            Stand toDelete = scene.stands[scene.currentObject];
            if (toDelete instanceof OC) {
                b.deleteOCFromScene(String.valueOf(scene.uniqueID), String.valueOf(toDelete.uniqueID));
            } else {
                b.deleteStandFromScene(String.valueOf(scene.uniqueID), String.valueOf(toDelete.uniqueID));
            }
        }
        b.delete("scene", "UniqueID", String.valueOf(sc.uniqueID));

        for (int i = index; i < scenes.size(); i++) {
            scenes.get(i).ID = i;
            gui.scenes.get(i).updateSceneButton(i);
        }
        refactorSceneButtons();
    }

    private void refactorSceneButtons(){
        for (int i = 0; i < scenes.size() + 1; i++) {
            int k = i % 5;
            int j = (i / 5) % 3;
            gui.scenes.get(i).x = k * 375 + 180;
            gui.scenes.get(i).y = j * 318 + 300;
        }
    }

    public void clearDatabase() {
        b.deleteLinked("oc_has_scene", "Scene_UniqueID", "scene", "UniqueID", "User_email", email);
        b.deleteLinked("scene_has_stand", "Scene_UniqueID", "scene", "UniqueID", "User_email", email);
        b.deleteDirect("scene", "User_email", email);
        b.deleteDirect("oc", "User_email", email);
    }

    public void reset() {
        clearDatabase();

        allOCs = new OC[0];
        scenes = new ArrayList<>();
        infos = new InfoSlab[0];
        selects = new SelectSlab[0];
        nAllOCs = 0;

        for (int i = 1; i < gui.scenes.size(); i++) {
            if (gui.scenes.get(i).state != STATE.NULL) {
                gui.scenes.get(i).state = STATE.NULL;
                gui.scenes.get(i).text = "{}";
            }
        }

        if (!gui.scenes.isEmpty()) {
            gui.scenes.getFirst().state = STATE.PLUS;
            gui.scenes.getFirst().text = "{}";
        }
    }


    public void updateCalculatedValues() {
        if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC) {
            try {
                ((OC)scene.stands[scene.currentObject]).name = gui.tfsced[0].text;

                gui.slSced[1].v = Float.parseFloat(gui.tfsced[1].text.replace(",", "."));
                gui.slSced[3].v = Float.parseFloat(gui.tfsced[3].text.replace(",", "."));

                ((OC)scene.stands[scene.currentObject]).tHeight = gui.slSced[1].v;
                ((OC)scene.stands[scene.currentObject]).BMI = gui.slSced[3].v;

                if (!gui.tfsced[7].text.equals("R")) gui.slSced[7].v = Float.parseFloat(gui.tfsced[7].text.replace(",", "."));
                if (!gui.tfsced[8].text.equals("G")) gui.slSced[8].v = Float.parseFloat(gui.tfsced[8].text.replace(",", "."));
                if (!gui.tfsced[9].text.equals("B")) gui.slSced[9].v = Float.parseFloat(gui.tfsced[9].text.replace(",", "."));
            } catch (Exception e) {}
        }

        float height = gui.slSced[1].v;
        if (height <= 0) height = 0.01f;
        if (!gui.tfsced[1].selected) gui.tfsced[1].setText(String.format("%.2f", height));

        float bmi = gui.slSced[3].v;
        gui.slSced[2].minV = height * height;
        gui.slSced[2].maxV = 250 * height * height;
        gui.slSced[2].v = bmi * height * height;

        float weight = gui.slSced[2].v;
        if (!gui.tfsced[2].selected) {
            if (Math.abs(weight) < 1 || Math.abs(weight) >= 1000) gui.tfsced[2].setText(String.format("%.3e", weight));
            else gui.tfsced[2].setText(String.format("%.3f", weight));
        }

        gui.slSced[4].v = constrain((float)(height * Math.pow(bmi, 0.7979) / 81.906),
                (float)(height * Math.pow(1, 0.7979) / 81.906),
                (float)(height * Math.pow(250, 0.7979) / 81.906));

        if (!gui.tfsced[4].selected) gui.tfsced[4].setText(String.format("%.3f", gui.slSced[4].v));
        if (!gui.tfsced[5].selected) gui.tfsced[5].setText(String.format("%.3f", gui.slSced[5].v));
        if (!gui.tfsced[6].selected) gui.tfsced[6].setText(String.format("%.0f", gui.slSced[6].v));

        if (selectedSl == gui.slSced[5]) {
            float ratio = constrain(gui.slSced[5].v, 6.01f, 7.99f);
            gui.slSced[6].v = (float)Math.log((ratio - 4.0f) / (8.0f - ratio)) / 0.0741f;
        } else if (selectedSl == gui.slSced[6]) {
            float age = gui.slSced[6].v;
            gui.slSced[5].v = 4.0f + 4.0f / (1.0f + (float)Math.exp(-0.0741f * age));
        }

        gui.slSced[6].v = constrain(Math.round(gui.slSced[6].v), 0, 80);

        try {
            for (int i = 7; i <= 9; i++) {
                if (!gui.tfsced[i].selected) {
                    if (gui.tfsced[i].text.trim().isEmpty()) { gui.tfsced[i].setText("0"); gui.slSced[i].v = 0; }
                    else gui.tfsced[i].setText(String.valueOf((int)gui.slSced[i].v));
                }
            }
        } catch (Exception e) {}

        if (scene != null && scene.nObjects > 0 && scene.currentObject != -1 && scene.stands[scene.currentObject] instanceof OC) {
            ((OC)scene.stands[scene.currentObject]).name = gui.tfsced[0].text;
            ((OC)scene.stands[scene.currentObject]).tHeight = gui.slSced[1].v;
            ((OC)scene.stands[scene.currentObject]).weight = gui.slSced[2].v;
            ((OC)scene.stands[scene.currentObject]).BMI = gui.slSced[3].v;
            ((OC)scene.stands[scene.currentObject]).tWidth = gui.slSced[4].v;
            ((OC)scene.stands[scene.currentObject]).bhratio = gui.slSced[5].v;
            ((OC)scene.stands[scene.currentObject]).age = (int) gui.slSced[6].v;
            ((OC)scene.stands[scene.currentObject]).r = (int) gui.slSced[7].v;
            ((OC)scene.stands[scene.currentObject]).g = (int) gui.slSced[8].v;
            ((OC)scene.stands[scene.currentObject]).b = (int) gui.slSced[9].v;
        }
    }

    private void updateOCBases(OC oc){
        b.update("oc", "Name", oc.name, "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "tHeight", String.valueOf(oc.tHeight), "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "BMI", String.valueOf(oc.BMI), "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "age", String.valueOf(oc.age), "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "r", String.valueOf(oc.r), "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "g", String.valueOf(oc.g), "UniqueID", String.valueOf(oc.uniqueID));
        b.update("oc", "b", String.valueOf(oc.b), "UniqueID", String.valueOf(oc.uniqueID));
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
        b.newOC(pHolder.uniqueID, nAllOCs, email);
        setZwolfDefaults(pHolder);
        pHolder.name = "Zwolf";
        changeTFValues(pHolder);
        addNewOCtoBase(pHolder);
        scene.addObject(pHolder);
        b.insert("oc_has_scene", "OC_UniqueID, Scene_UniqueID, Pos",String.valueOf(pHolder.uniqueID) + ", " + String.valueOf(scene.uniqueID) + ", " + (scene.nObjects-1));

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
        gui.slSced[6].v = 45;
        gui.slSced[5].v = (float) Math.pow(0.1295f, -1);
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

    public void updateInfoSearchArr(String str) {
        if (str == null) str = "";
        String searchStr = str.toLowerCase().trim();

        ArrayList<InfoSlab> tempList = new ArrayList<>();
        int searchIndex = 0;

        if (infos != null) {
            for (int i = 0; i < infos.length; i++) {

                if (infos[i] != null && infos[i].oc != null && infos[i].oc.name != null) {

                    if (infos[i].oc.name.toLowerCase().contains(searchStr)) {
                        InfoSlab newSlab = new InfoSlab(infos[i].oc, this);
                        newSlab.oc = infos[i].oc;
                        newSlab.ID = searchIndex;
                        newSlab.page = searchIndex / 5;
                        newSlab.x = 720 + ((newSlab.ID - 1) - newSlab.page * 5) * 300;
                        tempList.add(newSlab);
                        searchIndex++;
                    }
                }
            }
        }
        searchInfos = tempList.toArray(new InfoSlab[0]);
    }

    public void updateSelectSearchArr(String str) {
        if (str == null) str = "";
        String searchStr = str.toLowerCase().trim();

        ArrayList<SelectSlab> tempList = new ArrayList<>();
        int searchIndex = 0;

        if (selects != null) {
            for (int i = 0; i < selects.length; i++) {

                if (selects[i] != null && selects[i].oc != null && selects[i].oc.name != null) {

                    if (selects[i].oc.name.toLowerCase().contains(searchStr)) {

                        SelectSlab newSlab = new SelectSlab(this, selects[i].oc);

                        newSlab.isEnabled = selects[i].isEnabled;

                        newSlab.ID = searchIndex % 10;
                        newSlab.page = searchIndex / 10;

                        newSlab.y = 198 + (newSlab.ID * 87);

                        tempList.add(newSlab);
                        searchIndex++;
                    }
                }
            }
        }
        searchSelects = tempList.toArray(new SelectSlab[0]);
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
        gui.nuke.text = Languages.translate("RESET", l);

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

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public void retrieve(String email) {
        for (int i = 0; i < gui.scenes.size(); i++) {
            gui.scenes.get(i).state = STATE.NULL;
        }
            int lang = Integer.parseInt(b.getInfo("user", "lang", "email", email));
            gui.lang = (lang == 1 ? LANG.ENG : LANG.ESP);
            translateEverything();

            ArrayList<Scene> tempSc = new ArrayList<>();
            String[][] allSceneData = b.getAllScenes();

            for (int i = 0; i < allSceneData.length; i++) {

                if (allSceneData[i][3].equals(email)) {

                    int id = Integer.parseInt(allSceneData[i][1]);
                    long uniqueId = Long.parseLong(allSceneData[i][0]);

                    Scene nextSc = new Scene(id, uniqueId);
                    tempSc.add(nextSc);

                    gui.scenes.get(i).state = STATE.NORM;
                    gui.scenes.get(i + 1).state = STATE.PLUS;
                }
            }
            scenes = tempSc;
        }
}