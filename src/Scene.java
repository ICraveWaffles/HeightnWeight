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
    float pixelSize;
    float scale;

    final int scH = 600;
    final int scW = 930;
    final int scX = 340;
    final int scY = 110;

    public Scene(int ID) {
        this.ID = ID;
        this.stands = new Stand[0];
        this.nObjects = 0;
        this.currentObject = -1;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = 0;
    }

    public Scene(Scene sc) {

        this.name = sc.name;
        this.ID = sc.ID;
        this.nObjects = sc.nObjects;
        this.currentObject = sc.currentObject;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = sc.selPage;


        if (sc.stands != null) {
            this.stands = sc.stands.clone();
        }
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

    public String getTallestObject() {
        if (nObjects == 0) return "NULL";
        Stand max = stands[0];

        for (int i = 1; i < nObjects; i++) {
            if (stands[i].tHeight > max.tHeight) {
                max = stands[i];
            }
        }
        return max.name;
    }

    public void designLayout() {

        if (nObjects == 0) return;

        float totalWorldWidth = 0f;
        float maxWorldHeight = 0f;
        for (int i = 0; i < nObjects; i++) {
            totalWorldWidth += stands[i].tWidth * 1.3f; // Mantenemos tu factor de 1.3
            maxWorldHeight = Math.max(maxWorldHeight, stands[i].tHeight);
        }
        if (nObjects > 1) {
            totalWorldWidth += (nObjects - 1) * 0.5f;
        }

        float scaleW = totalWorldWidth / (float) scW;
        float scaleH = maxWorldHeight / (float) scH;

        this.pixelSize = Math.max(scaleW, scaleH);
        float currentX = scX;

        for (int i = 0; i < nObjects; i++) {
            stands[i].width = (stands[i].tWidth * 1.3f) / this.pixelSize;
            stands[i].height = stands[i].tHeight / this.pixelSize;
            stands[i].x = currentX;
            stands[i].y = (scY + scH) - stands[i].height;
            currentX += stands[i].width + (0.5f / this.pixelSize);
        }

        double log = Math.log10(100 * this.pixelSize);
        this.scale = (float) (Math.pow(10, Math.floor(log)) / this.pixelSize);
    }
}
