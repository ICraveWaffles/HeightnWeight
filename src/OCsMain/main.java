package OCsMain;

public class main {
    public static void main(String[]args){

        AWoS Betelgeuse = new AWoS(1392000*764, 1.988*Math.pow(10,30)*16.5f, 3500, "Betelgeuse");
        AWoS Rigel = new AWoS(1392000*74, 1.988*Math.pow(10,30)*21, 11000, "Rigel");

        Betelgeuse.print();
        Betelgeuse.printTemperature(0);

        Rigel.print();

        SSySS Spain = new SSySS(506030,47889958, "España");
        SSySS Madrid = new SSySS (8028, 7056000, "Madrid");
        SSySS Majorca = new SSySS (3620, 966908, "Mallorca");
        SSySS Greenland = new SSySS (2165000, 56700, "Greenland");

        Spain.print();
        Majorca.print();
        Madrid.print();
        Greenland.print();
    }
}
