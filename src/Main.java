import processing.core.PApplet;


public class Main extends PApplet {

    Fonts fonts;
    GUI gui;
    float scaleFactor;
    int baseWidth = 1920;
    int baseHeight = 1080;
    TextField selectedtf;

    public static void main(String[]args){
        PApplet.main("Main");
    }

    public void settings() {

        fullScreen();

        float screenRatio = (float) displayWidth / displayHeight;
        float targetRatio = (float) baseWidth / baseHeight;


        int newWidth, newHeight;

        if (screenRatio > targetRatio) {
            newHeight = displayHeight;
            newWidth = (int) (newHeight * targetRatio);
        } else {
            newWidth = displayWidth;
            newHeight = (int) (newWidth / targetRatio);
        }

        smooth(100);
        scaleFactor = (float) newWidth / baseWidth;
    }

    public void setup(){
        fonts = new Fonts(this);
        gui = new GUI(this);
        println(width, height);
    }


    public void draw(){
        textFont(fonts.getThisFont(0));
        text("BigText test, say hello to the camera", 20, 30);
        textFont(fonts.getThisFont(1));
        text("ButtonText test, say hello to the  camera", 20, 55);
        textFont(fonts.getThisFont(2));
        text("TextText test, say hello to the camera", 20, 75);

        switch (gui.currentScreen){
            case PRELOGIN: gui.drawPRELOGIN(this); break;
            case LOGIN: gui.drawLOGIN(this); break;
            case SIGNUP: gui.drawSIGNUP(this); break;
            case MAIN: gui.drawMAIN(this); break;
            case QNA: gui.drawQNA(this); break;
            case SCENESELECTOR: gui.drawSCENESELECTOR(this); break;
            case SCENEEDITOR: gui.drawSCENEEDITOR(this);break;
            case OCVIEWER: gui.drawOCVIEWER(this);break;
        }
        noFill();
        rect(0, 0, 1280, 720);
    }

    /*public void keyPressed(){
        if (key == '0'){
            gui.currentScreen = GUI.SCREEN.PRELOGIN;
        } else if(key == '1'){
            gui.currentScreen = GUI.SCREEN.LOGIN;
        } else if(key == '2'){
            gui.currentScreen = GUI.SCREEN.SIGNUP;
        } else if(key == '3'){
            gui.currentScreen = GUI.SCREEN.MAIN;
        } else if(key == '4') {
            gui.currentScreen = GUI.SCREEN.QNA;
        } else if(key == '5'){
            gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
        } else if(key == '6'){
            gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
        } else if(key == '7'){
            gui.currentScreen = GUI.SCREEN.OCVIEWER;
        }
    } */

    public void mousePressed() {

        if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {
            if (gui.plog1.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.LOGIN;
            }
            if (gui.plog2.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.SIGNUP;
            }
            if (gui.exit.mouseOverButton(this)){
                exit();
            }

        } else if (gui.currentScreen == GUI.SCREEN.LOGIN) {
            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.PRELOGIN;
            }
            if (gui.login.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.MAIN;
            }

            gui.tflogin1.isPressed(this);
            gui.tflogin2.isPressed(this);

        } else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {

            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.PRELOGIN;
            }
            if (gui.signup.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.MAIN;
            }
            gui.tfsignup1.isPressed(this);
            gui.tfsignup2.isPressed(this);
            gui.tfsignup3.isPressed(this);
            gui.tfsignup4.isPressed(this);

        } else if (gui.currentScreen == GUI.SCREEN.MAIN) {

            if (gui.q1.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.QNA;
            }
            if (gui.m1.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
            }
            if (gui.m2.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.OCVIEWER;
            }
            if (gui.m3.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.PRELOGIN;
            }

        } else if (gui.currentScreen == GUI.SCREEN.QNA) {

            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.MAIN;
            }

        } else if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {

            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.MAIN;
                gui.page = 0;
            }
            if (gui.nav1.mouseOverButton(this)){
                gui.page = 0;
            }
            if (gui.nav2.mouseOverButton(this)&& gui.page!=0){
                gui.page--;
            }
            if (gui.nav3.mouseOverButton(this) && gui.page !=9){
                gui.page++;
            }
            if (gui.nav4.mouseOverButton(this)){
                gui.page=9;
            }
            for (int i = 0; i < gui.scenes.length; i++){
                if (gui.scenes[i].mouseOverButton(this)){
                    gui.currentScreen = GUI.SCREEN.SCENEEDITOR;
                }
            }

        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.SCENESELECTOR;
            }
            if (gui.rsced1.mouseOverButton(this)){
                gui.gridon = !gui.gridon;
            }

            for (int i = 0; i < 10; i++){
                if (!gui.lbsced[i].isLocked()) {
                    gui.tfsced[i].isPressed(this);
                }
                if (gui.lbsced[i].mouseOverButton(this)){

                    gui.lbsced[i].mousePressed(this);
                    if (!gui.lbsced[1].isLocked() && !gui.lbsced[2].isLocked() && !gui.lbsced[3].isLocked()){
                        if (i == 1){
                            gui.lbsced[3].changeLock();
                        } else if (i == 2){
                            gui.lbsced[3].changeLock();
                        } else if (i == 3){
                            gui.lbsced[2].changeLock();
                        }

                    }
                    if (!gui.lbsced[1].isLocked() && !gui.lbsced[3].isLocked() && !gui.lbsced[4].isLocked()){
                        if (i == 1){
                            gui.lbsced[4].changeLock();
                        } else if (i == 3){
                            gui.lbsced[4].changeLock();
                        } else if (i == 4){
                            gui.lbsced[3].changeLock();
                        }
                    }
                    if (!gui.lbsced[5].isLocked() && !gui.lbsced[6].isLocked()){
                        if (i == 5){
                            gui.lbsced[6].changeLock();
                        } else if (i == 6){
                            gui.lbsced[5].changeLock();
                        }
                    }

                }
            }


        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {
            if (gui.exit.mouseOverButton(this)){
                gui.currentScreen = GUI.SCREEN.MAIN;
            }
        }
    }

    public void keyPressed(){
        if (gui.currentScreen == GUI.SCREEN.PRELOGIN) {

        } else if (gui.currentScreen == GUI.SCREEN.LOGIN) {
            gui.tflogin1.keyPressed(key, keyCode, false);
            gui.tflogin2.keyPressed(key, keyCode, false);
        } else if (gui.currentScreen == GUI.SCREEN.SIGNUP) {
            gui.tfsignup1.keyPressed(key, keyCode, false);
            gui.tfsignup2.keyPressed(key, keyCode, false);
            gui.tfsignup3.keyPressed(key, keyCode, false);
            gui.tfsignup4.keyPressed(key, keyCode, false);
        } else if (gui.currentScreen == GUI.SCREEN.MAIN) {

        } else if (gui.currentScreen == GUI.SCREEN.QNA) {

        } else if (gui.currentScreen == GUI.SCREEN.SCENESELECTOR) {

        } else if (gui.currentScreen == GUI.SCREEN.SCENEEDITOR) {
            gui.tfsced[0].keyPressed(key, keyCode, false);
            for (int i = 1; i < 10; i++){
                gui.tfsced[i].keyPressed(key, keyCode, true );
            }
        } else if (gui.currentScreen == GUI.SCREEN.OCVIEWER) {

        }
    }

}