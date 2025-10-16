import processing.core.PApplet;


public class Main extends PApplet {

    Fonts fonts;
    GUI gui;
    float scaleFactor;
    int baseWidth = 1920;
    int baseHeight = 1080;

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
            case OCVIEWER: break;
        }
    }

    public void keyPressed(){
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
    }
}