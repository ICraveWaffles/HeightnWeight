import java.util.Arrays;
import java.util.Random;

public class Scene {

    public String name;
    public Stand[] stands;
    public int nObjects;
    public int currentObject;

    public enum scInstance {DISPLAY, OCSELECT}
    public scInstance sel;

    public int selPage = 0;
    public int ID;
    public final long uniqueID;

    public float pixelSize;
    public float scale;

    public final int scH = 900;
    public final int scW = 1395;
    public final int scX = 510;
    public final int scY = 165;

    /**
     * Constructor de la clase Scene para escenas completamente nuevas.
     * @param ID posición de la escena.
     */
    public Scene(int ID) {
        Random random = new Random();
        this.ID = ID;
        this.uniqueID = 10000000 + random.nextInt(90000000);
        this.stands = new Stand[0];
        this.nObjects = 0;
        this.currentObject = -1;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = 0;
    }
    /**
     * Constructor de la clase String para el método retrieve(String email) de la clase main.
     * @param ID
     * @param uniqueID
     */
    public Scene(int ID, long uniqueID) {
        this.ID = ID;
        this.uniqueID = uniqueID;
        this.stands = new Stand[0];
        this.nObjects = 0;
        this.currentObject = -1;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = 0;
    }

    /**
     * Constructor que copia una escena concreta.
     * @param sc una escena concreta.
     */
    public Scene(Scene sc) {
        Random random = new Random();
        this.name = sc.name;
        this.ID = sc.ID;
        this.uniqueID = 10000000 + random.nextInt(90000000);
        this.nObjects = sc.nObjects;
        this.currentObject = sc.currentObject;
        this.sel = Scene.scInstance.DISPLAY;
        this.selPage = sc.selPage;

        if (sc.stands != null) {
            this.stands = sc.stands.clone();
        }
    }

    public void addObject(Stand s) {
        Sounds.emit(11);
        if (nObjects == stands.length) {
            stands = Arrays.copyOf(stands, stands.length + 1);
        }
        stands[nObjects] = s;
        currentObject = nObjects;
        nObjects++;
    }

    public void deleteObject(Stand oc) {
        Sounds.emit(12);
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
        for(int i=0;i<this.nObjects;i++){
            if(this.stands[i].uniqueID==id){
                isIn=true;
                break;
            }
        }
        return !isIn;
    }

    public String getTallestObject(){
        if(nObjects==0) return "NULL";
        Stand max = stands[0];
        for(int i=1;i<nObjects;i++){
            if(stands[i].tHeight>max.tHeight){
                max=stands[i];
            }
        }
        return max.name;
    }

    public void designLayout(){
        if(nObjects==0) return;
        float totalWorldWidth=0f;
        float maxWorldHeight=0f;
        for(int i=0;i<nObjects;i++){
            totalWorldWidth+=stands[i].tWidth*1.3f;
            maxWorldHeight=Math.max(maxWorldHeight,stands[i].tHeight);
        }
        if(nObjects>1){
            totalWorldWidth+=(nObjects-1)*0.5f;
        }
        float scaleW = totalWorldWidth/(float)scW;
        float scaleH = maxWorldHeight/(float)scH;
        this.pixelSize = Math.max(scaleW,scaleH);
        float currentX = scX;
        for(int i=0;i<nObjects;i++){
            stands[i].width  = (stands[i].tWidth*1.3f)/this.pixelSize;
            stands[i].height = stands[i].tHeight/this.pixelSize;
            stands[i].x = currentX;
            stands[i].y = (scY+scH)-stands[i].height;
            currentX += stands[i].width + (0.5f/this.pixelSize);
        }
        double log = Math.log10(100*this.pixelSize);
        this.scale = (float)(Math.pow(10,Math.floor(log))/this.pixelSize);
    }
}