import java.nio.file.*;

public class Languages {
    public static String[][] data;

    public static void load() throws Exception {
        Path path = Paths.get("data/tokens.txt");
        Object[] lines = Files.lines(path).toArray();
        data = new String[lines.length][];

        for (int i = 0; i < lines.length; i++) {
            data[i] = lines[i].toString().split(";");
        }
    }

    /**
     * Método que traduce el token de un elemento de la GUI a una lengua específica.
     * @param token token de un elemento de la GUI.
     * @param lang valor que equivale a la columna de data[][].
     * @return traducción encontrada.
     */
    public static String translate(String token, int lang) {
        if (data == null) return token;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null && data[i].length > 0) {

                String tokenEnArchivo = data[i][0].trim();
                if (tokenEnArchivo.equalsIgnoreCase(token.trim())) {

                    if (lang < data[i].length) {
                        return data[i][lang].trim();
                    }
                }
            }
        }
        return token;
    }
}