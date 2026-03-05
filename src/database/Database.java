package database;

import java.sql.*;

public class Database {

    Connection connection;
    Statement query;

    String user, pw, dbName;

    boolean connected = false;

    public Database(String user, String pw, String dbName){
        this.user = user;
        this.pw = pw;
        this.dbName = dbName;
    }

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName, user, pw);
            query = connection.createStatement();
            System.out.print("Successfully connected to database");
            connected = true;
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public String getInfo(String table, String column, String pk, String id){
        try{
            String q =  "SELECT " + column +
                    " FROM " + table +
                    " WHERE "+ pk  + " = '" + id + "' ";
            System.out.println(q);
            ResultSet rs= query.executeQuery(q);
            rs.next();
            return rs.getString(column);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "neverUser";
    }

    public int getRowCount(String tableName){
        String q = "SELECT COUNT(*) AS num FROM " + tableName;
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

    public String[] getColumnValues(String tableName, String columnName){
        int n = getRowCount(tableName);
        String[] info = new String[n];
        String q = "SELECT " + columnName +
                " FROM " + tableName +
                " ORDER BY " + columnName + " ASC";
        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while(rs.next()){
                info[f] = rs.getString(1);
                f++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return info;
    }

}
