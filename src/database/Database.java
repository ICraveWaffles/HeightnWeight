package database;

import java.sql.*;

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


    public void insert(String objectName, String attributeNames, String values){

        String q = "INSERT INTO " + objectName +
                " (" + attributeNames + ") VALUES (" + values + ")";

        try{
            query.executeUpdate(q);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void signup (String email, String username, String password){
        String values = "'" + email + "', " +
                "'" + username + "', " +
                "'" + password + "', " +
                "0, " +
                "1";
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