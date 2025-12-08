public class Scene {

    Stand[] stands;
    int nObjects;
    int currentObject;
    enum scInstance {DISPLAY, OCSELECT}
    scInstance sel;
    int selPage = 0;

    public Scene(int n) {
        this.stands = new Stand[n];
        this.nObjects = 0;
        this.currentObject = -1;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = 0;
    }

    public void addObject(Stand s) {
        if (nObjects == stands.length) {
            stands = java.util.Arrays.copyOf(stands, stands.length + 1);
        }
        stands[nObjects] = s;
        currentObject = nObjects;
        nObjects++;
    }

    public void deleteObject(int index) {
        if (index < 0 || index >= nObjects) return;

        for (int i = index; i < nObjects - 1; i++) {
            stands[i] = stands[i + 1];
        }

        stands[nObjects - 1] = null;
        nObjects--;

        if (currentObject >= nObjects) currentObject = nObjects - 1;
        if (nObjects == 0) currentObject = -1;
    }

    public float[] getTallestObject() {
        if (nObjects == 0) return new float[]{-1, 0};

        int index = 0;
        float max = stands[0].tHeight;

        for (int i = 1; i < nObjects; i++) {
            if (stands[i].tHeight > max) {
                max = stands[i].tHeight;
                index = i;
            }
        }
        return new float[]{index, max};
    }

    public void designLayout() {
        if (nObjects == 0) return;

        float totalWidth = 0f;
        float tallest = 0f;

        for (int i = 0; i < nObjects; i++) {
            totalWidth += stands[i].tWidth;
            tallest = Math.max(tallest, stands[i].tHeight);
        }

        totalWidth += (nObjects - 1) * 0.5f;
        float scaleW = totalWidth / 900f;
        float scaleH = tallest / 600f;
        float pixelSize = Math.max(scaleW, scaleH);

        float currentX = 364f;
        float baseY = 114f;

        for (int i = 0; i < nObjects; i++) {
            stands[i].width = stands[i].tWidth / pixelSize;
            stands[i].height = stands[i].tHeight / pixelSize;
            stands[i].x = currentX;
            stands[i].y = baseY + (600f - stands[i].height);
            currentX += stands[i].width + 0.5f / pixelSize;
        }
    }
}
