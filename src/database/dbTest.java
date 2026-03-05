package database;

import processing.core.PApplet;

public class dbTest {
    public static Database b;

    public static void main(String[]args){
        b = new Database("admin", "12345", "ocbase");
        b.connect();
    }
}
