import java.util.Arrays;

public class Scene {

    String name;
    Stand[] stands;
    public int nObjects;
    int currentObject;
    enum scInstance {DISPLAY, OCSELECT}
    scInstance sel;
    int selPage = 0;
    int ID;
    public boolean cPickerOn;

    public Scene(int ID) {
        this.ID = ID;
        this.stands = new Stand[0];
        this.nObjects = 0;
        this.currentObject = -1;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = 0;
        this.cPickerOn = false;
    }

    public Scene(Scene sc) {
        this.name = sc.name;
        this.ID = sc.ID;
        this.stands = sc.stands;
        this.nObjects = sc.nObjects;
        this.currentObject = sc.currentObject;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = sc.selPage;
        this.cPickerOn = false;
    }

    public void addObject(Stand s) {
        if (nObjects == stands.length) {
            stands = java.util.Arrays.copyOf(stands, stands.length + 1);
        }
        stands[nObjects] = s;
        currentObject = nObjects;
        nObjects++;
    }

    public void deleteObject(Stand oc) {
        int index = -1;
        for (int i = 0; i < nObjects; i++) {
            if (stands[i] == oc) {
                index = i;
                break;
            }
        }
        if (index == -1) return;
        for (int i = index; i < nObjects - 1; i++) {
            stands[i] = stands[i + 1];
        }
        stands[nObjects - 1] = null;
        nObjects--;
        stands = Arrays.copyOf(stands, nObjects);
        if (currentObject >= nObjects) currentObject = nObjects - 1;
        if (nObjects == 0) currentObject = -1;

    }

    public boolean isInScene(long id){
        boolean isIn = false;
        for (int i = 0; i <this.nObjects;i++){
            if (this.stands[i].uniqueID == id) {
                isIn = true;
                break;
            }
        }
        return !isIn;
    }

    public OC getTallestObject() {
        if (nObjects == 0) return null;
        Stand max = stands[0];

        for (int i = 1; i < nObjects; i++) {
            if (stands[i].tHeight > max.height) {
                max = stands[i];
            }
        }
        return (OC) max;
    }

    public void designLayout() {
        if (nObjects == 0) return;

        float totalWidth = 0f;
        float tallest = 0f;

        for (int i = 0; i < nObjects; i++) {
            totalWidth += stands[i].tWidth*1.3f;
            tallest = Math.max(tallest, stands[i].tHeight);
        }

        totalWidth += (nObjects - 1) * 0.5f;
        float scaleW = totalWidth / 900f;
        float scaleH = tallest / 600f;
        float pixelSize = Math.max(scaleW, scaleH);

        float currentX = 364f;
        float baseY = 114f;

        for (int i = 0; i < nObjects; i++) {
            stands[i].width = stands[i].tWidth*1.3f / pixelSize;
            stands[i].height = stands[i].tHeight / pixelSize;
            stands[i].x = currentX;
            stands[i].y = baseY + (600f - stands[i].height);
            currentX += stands[i].width + 0.5f / pixelSize;
        }
    }
}
