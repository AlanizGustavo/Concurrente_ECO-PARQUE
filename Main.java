import Actividades.AdministradorTour;
import Actividades.Delfines;
import Actividades.Faro;
import Actividades.Gomones;
import Actividades.Parque;
import Actividades.Restaurante;
import Actividades.Shop;
import Actividades.Snorkel;
import Hilos.AsistenteFaro;
import Hilos.AsistenteSnorkel;
import Hilos.ChoferTren;
import Hilos.Gomon;
import Hilos.RelojEstacionamiento;
import Hilos.RelojParque;
import Hilos.Visitante;
import util.TecladoIn;
import util.Tren;

public class Main {
    public static void main(String[] args) {

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("-------------------BIENVENIDOS A PARQUE ECO-PCS----------------------------");
        System.out.println("---------------------------------------------------------------------------");

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("-------ANTES DE EMPEZAR LA SIMULACION, ES NECESARIO ESTABLECER-------------");
        System.out.println("------------ALGUNOS VALORES DE LAS ACTIVIDADES DEL PARQUE------------------");
        System.out.println("---------------------------------------------------------------------------");

        Shop shop = new Shop();
        AdministradorTour adminTour = new AdministradorTour();
        Tren tren = new Tren();

        // *********************** RESTAURANTES **************************************
        int CANT_RESTAURANTES = 3; // Cantidad especificada en el enunciado
        Restaurante[] restaurantes = new Restaurante[CANT_RESTAURANTES];

        System.out.println("Ingrese la capacidad de los restaurantes (todos tendran la misma)");
        int capacidadRestaurantes = TecladoIn.readLineInt();

        for (int i = 0; i < CANT_RESTAURANTES; i++) {
            restaurantes[i] = new Restaurante(capacidadRestaurantes);
        }

        // *********************** GOMONES **************************************
        System.out.println("Ingrese la cantidad de gomones individuales");
        int cantGomonesIndividuales = TecladoIn.readLineInt();
        System.out.println("Ingrese la cantidad de gomones dobles");
        int cantGomonesDobles = TecladoIn.readLineInt();
        System.out.println("Ingrese la cantidad de gomones que se necesitan para salir");
        int cantGomonesJuntos = TecladoIn.readLineInt();

        Gomones gomones = new Gomones(cantGomonesIndividuales, cantGomonesDobles, cantGomonesJuntos);

        // *********************** PARQUE **************************************
        System.out.println("Ingrese la cantidad de molinetes que tiene el parque");
        int cantMolinetes = TecladoIn.readLineInt();

        Parque parque = new Parque(cantMolinetes);

        // *********************** SNORKEL **************************************
        System.out.println("Ingrese la cantidad de equipamiento que posee la atraccion snorkel ilimintado");
        int cantEquipamientos = TecladoIn.readLineInt();

        Snorkel snorkel = new Snorkel(cantEquipamientos);

        // *********************** FARO-MIRADOR **************************************
        System.out.println("Ingrese la cantidad de escalones que tiene el faro-mirador");
        int capacidadEscalera = TecladoIn.readLineInt();

        Faro faro = new Faro(capacidadEscalera);

        // ********************** NADO CON DELFINES **********************************

        Delfines delfines = new Delfines();

        // ************************* VISITANTES **************************************
        System.out.println("Ingrese la cantidad de visitantes al parque");
        int cantVisitantes = TecladoIn.readLineInt();

        // ************************ EJECUCION DE HILOS ******************************

        Thread hilosGomones[] = new Thread[(cantGomonesIndividuales + cantGomonesDobles)];
        for (int i = 0; i < cantVisitantes; i++) {
            new Thread(new Visitante(parque, adminTour, restaurantes, shop, snorkel, faro, delfines, tren, gomones),
                    "VISITANTE " + i)
                    .start();
        }

        new Thread(new ChoferTren(tren), "CHOFER TREN ").start();

        new Thread(new RelojEstacionamiento(adminTour), "RELOJ ESTACIONAMIENTO ").start();

        new Thread(new RelojParque(parque, delfines), "RELOJ PARQUE").start();

        new Thread(new AsistenteSnorkel(snorkel), "Asistente Snorkel 1 ").start();
        new Thread(new AsistenteSnorkel(snorkel), "Asistente Snorkel 2 ").start();

        new Thread(new AsistenteFaro(faro), "Asistente Faro ").start();

        for (int i = 0; i < cantGomonesIndividuales; i++) {
            hilosGomones[i] = new Thread(new Gomon(1, gomones), "GOMON INDIVIDUAL " + i + ":");
        }

        for (int i = cantGomonesIndividuales; i < cantGomonesDobles + cantGomonesIndividuales; i++) {
            hilosGomones[i] = new Thread(new Gomon(2, gomones), "GOMON DOBLE " + i + ":");
        }

        for (int i = 0; i < hilosGomones.length; i++) {
            hilosGomones[i].start();
        }
    }
}