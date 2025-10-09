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
        float screenRatio = (float) displayWidth / displayHeight;
        float targetRatio = (float) baseWidth / baseHeight;
        fullScreen(P2D,1920);

        int newWidth, newHeight;

        if (screenRatio > targetRatio) {
            newHeight = displayHeight;
            newWidth = (int) (newHeight * targetRatio);
        } else {
            newWidth = displayWidth;
            newHeight = (int) (newWidth / targetRatio);
        }

        size(newWidth, newHeight);
        smooth(6);
        scaleFactor = (float) newWidth / baseWidth;
    }

    public void setup(){
        fonts = new Fonts(this);
        gui = new GUI(this);
    }


    public void draw(){
        background (127);
        scale(scaleFactor);
        textFont(fonts.getThisFont(0));
        text("BigText test, say hello to the camera", 20, 30);
        textFont(fonts.getThisFont(1));
        text("ButtonText test, say hello to the  camera", 20, 55);
        textFont(fonts.getThisFont(2));
        text("TextText test, say hello to the camera", 20, 75);

        switch (gui.currentScreen){
            case PRELOGIN: gui.drawPRELOGIN(this); break;
            case LOGIN: break;
            case SIGNUP: break;
            case MAIN: break;
            case QNA: break;
            case SCENESELECTOR: break;
            case SCENEEDITOR: break;
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