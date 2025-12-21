package OCsMain;
import java.lang.Math;

public class AWoS {
    String name;
    double diameter,diameter2,diameter3;
    double surface, mass, density, volume, width,  temp0, tBMI;
    double height, BMI, weight, contacttemp, safedist;


    AWoS(double diameter, double mass, double temp0, String name){
        this.name = name;
        this.diameter = diameter;
        this.diameter2 = diameter;
        this.diameter3 = diameter;

        this.surface = Math.PI * Math.pow(this.diameter,2);
        this.volume = (Math.PI/6f)*Math.pow(diameter*1000, 3);
        this.mass = mass;
        this.density = this.mass / this.volume;

        this.height = 0.598 * Math.log10(diameter)+0.906;
        this.BMI = -4.393 * Math.log(this.density)+55.348;
        this.tBMI = Math.abs(this.BMI-25)+25;
        this.width = Math.pow(this.tBMI, 0.7979f) / 81.906;
        this.weight = Math.pow(this.height,2 )*this.tBMI;

        this.temp0 = temp0;
        this.contacttemp = (1/296.56f)*(temp0/Math.sqrt(2))*Math.pow(height/(width/2), 0.5)+273.15f;
        this.safedist = this.height * (Math.pow(this.temp0,2)/439700000) - this.width/2;

    }


    void print(){
        System.out.printf("Datos de %s: \n Altura: %f m \n Índice de Masa Corporal: %f kg/m^3 \n Masa: %f kg \n Temperatura de Contacto: %f ºC \n Temperatura de seguridad: %f m \n", this.name,  this.height, this.tBMI, this.weight, this.contacttemp-273.15, this.safedist);
    }

    void printTemperature(double distance){
        double tempinfunctionofdistance = (1/296.56f)*(temp0/Math.sqrt(2))*Math.pow(height/(width/2 + distance), 0.5);
        System.out.printf("Temperatura a %f metros: %f ºC\n", distance, tempinfunctionofdistance);
    }
}

