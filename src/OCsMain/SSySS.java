package OCsMain;

public class SSySS {
    String name;
    double surface, population, density;
    double height, BMI, tBMI, width, weight;


    SSySS (double surface, double population, String name){

        this.name = name;
        this.surface = surface;
        this.population = population;
        this.density = this.population / this.surface;

        this.height = 0.299 * Math.log10(this.surface)+0.756;
        this.BMI = -169.6 * Math.pow (this.density, 1/14.366f)+250;
        this.tBMI = Math.abs(this.BMI-25)+25;
        this.width = Math.pow(this.tBMI, 0.7979f) / 81.906;
        this.weight = Math.pow(this.height,2 )*this.tBMI;

    }

    void print(){
        System.out.printf("Datos de %s: \n Altura: %f m \n Índice de Masa Corporal: %f kg/m^3 \n Masa: %f kg \n", this.name, this.height, this.tBMI, this.weight);
    }
}
