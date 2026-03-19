package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Connection connection;
    Statement query;

    String user, password, databaseName;

    boolean connected = false;

    public Database(String user, String password, String databaseName){
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + databaseName,
                    user,
                    password
            );
            query = connection.createStatement();
            System.out.print("Successfully connected to database");
            connected = true;
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public String getInfo(String objectName, String attributeName, String primaryKey, String primaryKeyValue){
        try{
            String q = "SELECT " + attributeName +
                    " FROM " + objectName +
                    " WHERE " + primaryKey + " = '" + primaryKeyValue + "'";

            System.out.println(q);

            ResultSet rs = query.executeQuery(q);
            rs.next();

            return rs.getString(attributeName);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public int getRowCount(String objectName){
        String q = "SELECT COUNT(*) AS num FROM " + objectName;
        try{
            ResultSet rs = query.executeQuery(q);
            rs.next();
            return rs.getInt("num");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public int getColCount(String objectName){
        try {
            String q = "SELECT count(*) as n FROM information_schema.columns WHERE table_name ='"+ objectName +"' AND table_schema='"+databaseName+"'";
            System.out.println(q);
            ResultSet rs = query.executeQuery( q);
            rs.next();
            int numCols = rs.getInt("n");
            return numCols;
        }
        catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public String[] getColumnValues(String objectName, String attributeName){
        int rowCount = getRowCount(objectName);
        String[] values = new String[rowCount];
        String q = "SELECT " + attributeName +
                " FROM " + objectName;

        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            int index = 0;
            while(rs.next()){
                values[index] = rs.getString(attributeName);
                index++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return values;
    }

    public String[][] getAllScenes() {
        int nc = 4;

        List<String[]> rows = new ArrayList<>();

        try {
            String q = "SELECT * FROM " + "scene";
            ResultSet rs = query.executeQuery(q);

            while (rs.next()) {
                String[] row = new String[nc];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                rows.add(row);
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar escenas: " + e.getMessage());
        }
        return rows.toArray(new String[0][nc]);
    }
    public void insert(String objectName, String attributeNames, String values) {

        String q = "INSERT INTO " + objectName +
                " (" + attributeNames + ") VALUES (" + values + ")";

        try {
            query.executeUpdate(q);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void signup (String email, String username, String password){
        String values = "'" + email + "', " +
                "'" + username + "', " +
                "'" + password + "', " +
                "0, " +
                "2";
        insert("user", "email, username, password, nScreenshots, lang", values);

        System.out.println("Registro intentado para: " + username);
    }

    public void newScene(long uniqueID, int ID, String email) {
        String values = uniqueID + ", " +
                ID + ", " +
                "0, " +
                "'" + email + "'";

        insert("scene", "UniqueID, ID, currentObject, User_email", values);

        System.out.println("Escena creada para: " + email);
    }

    public void newOC(long uniqueID, int ID, String email) {
        String name = "Zwolf";
        float tHeight = 1.83f;
        float bmi = 25.0f;
        float age = 45.0f;
        int r = 127;
        int g = 127;
        int bValue = 127;

        String values = ID + ", " +
                uniqueID + ", " +
                "'" + name + "', " +
                tHeight + ", " +
                bmi + ", " +
                age + ", " +
                r + ", " +
                g + ", " +
                bValue + ", " +
                "'" + email + "'";

        insert("oc", "ID, UniqueID, Name, tHeight, BMI, age, r, g, b, User_email", values);

        System.out.println("OC " + name + " creado con éxito para: " + email);
    }

    public void update(String objectName, String attributeName, String newValue, String primaryKey, String primaryKeyValue){

        String q = "UPDATE " + objectName +
                " SET " + attributeName + " = '" + newValue + "'" +
                " WHERE " + primaryKey + " = '" + primaryKeyValue + "'";

        try{
            query.executeUpdate(q);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void updateStandPos(int nuevaPos, long sceneID, long standID) {

        String q = "UPDATE " + "scene_has_stand" +
                " SET Pos = " + nuevaPos +
                " WHERE Scene_UniqueID = " + sceneID +
                " AND Stand_UniqueID = " + standID;
        try {
            query.executeUpdate(q);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOCPos(int nuevaPos, long sceneID, long standID) {

        String q = "UPDATE " + "oc_has_scene" +
                " SET Pos = " + nuevaPos +
                " WHERE Scene_UniqueID = " + sceneID +
                " AND OC_UniqueID = " + standID;
        try {
            query.executeUpdate(q);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String objectName, String primaryKey, String primaryKeyValue){
        String q = "DELETE FROM " + objectName +
                " WHERE " + primaryKey + " = '" + primaryKeyValue + "'";
        try{
            query.executeUpdate(q);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void deleteDirect(String table, String userColumn, String email) {
        String q = "DELETE FROM " + table + " WHERE " + userColumn + " = '" + email + "'";
        try {
            query.executeUpdate(q);
        } catch (Exception e) {
            System.out.println("Error en " + table + ": " + e.getMessage());
        }
    }

    public void deleteLinked(String junctionTable, String junctionCol, String parentTable, String parentIdCol, String userCol, String email) {
        String q = "DELETE FROM " + junctionTable + " WHERE " + junctionCol + " IN (SELECT " + parentIdCol + " FROM " + parentTable + " WHERE " + userCol + " = '" + email + "')";
        try {
            query.executeUpdate(q);
        } catch (Exception e) {
            System.out.println("Error en relación " + junctionTable + ": " + e.getMessage());
        }
    }

    public void deleteStandFromScene(String sceneID, String standID) {
        String q = "DELETE FROM scene_has_stand WHERE Scene_UniqueID = " + sceneID +
                " AND Stand_UniqueID = " + standID;
        try {
            query.executeUpdate(q);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOCFromScene(String sceneID, String ocID) {
        String q = "DELETE FROM oc_has_scene WHERE Scene_UniqueID = " + sceneID +
                " AND OC_UniqueID = " + ocID;
        try {
            query.executeUpdate(q);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            if(connection != null){
                connection.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean exists(String tabla, String columna, String valor) {
        String q = "SELECT COUNT(*) AS total FROM " + tabla + " WHERE " + columna + " = '" + valor + "'";
        try {
            java.sql.ResultSet rs = query.executeQuery(q);
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar duplicados: " + e);
        }
        return false;
    }
}