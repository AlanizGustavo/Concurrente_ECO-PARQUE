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
import util.Tren;

public class Main {
    public static void main(String[] args) {
        int CANT_RESTAURANTES = 3; // Cantidad especificada en el enunciado
        Restaurante[] restaurantes = new Restaurante[CANT_RESTAURANTES];
        int cantGomonesIndividuales = 5;
        int cantGomonesDobles = 4;
        int maxCantGomonesJuntos = 3;
        Thread hilosGomones[] = new Thread[(cantGomonesIndividuales + cantGomonesDobles)];
        Shop shop = new Shop();
        Parque parque = new Parque();
        AdministradorTour adminTour = new AdministradorTour();
        Snorkel snorkel = new Snorkel(10);
        Faro faro = new Faro(10);
        Delfines delfines = new Delfines();
        Tren tren = new Tren();
        Gomones gomones = new Gomones(cantGomonesIndividuales, cantGomonesDobles, maxCantGomonesJuntos);

        for (int i = 0; i < CANT_RESTAURANTES; i++) {
            restaurantes[i] = new Restaurante(10);
        }

        for (int i = 0; i < 20; i++) {
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

        int k = 0;
        for (int i = 0; i < cantGomonesIndividuales; i++) {
            hilosGomones[k] = new Thread(new Gomon(1, gomones), "GOMON INDIVIDUAL " + k + ":");
            k++;
        }

        for (int i = 0; i < cantGomonesDobles; i++) {
            hilosGomones[k] = new Thread(new Gomon(2, gomones), "GOMON DOBLE " + k + ":");
            k++;
        }

        for (int i = 0; i < hilosGomones.length; i++) {
            hilosGomones[i].start();
        }
    }
}