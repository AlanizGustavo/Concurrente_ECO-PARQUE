import Actividades.AdministradorTour;
import Actividades.Faro;
import Actividades.Parque;
import Actividades.Restaurante;
import Actividades.Shop;
import Actividades.Snorkel;
import Hilos.AsistenteFaro;
import Hilos.AsistenteSnorkel;
import Hilos.RelojEstacionamiento;
import Hilos.RelojParque;
import Hilos.Visitante;

public class Main {
    public static void main(String[] args) {
        int CANT_RESTAURANTES = 3; // Cantidad especificada en el enunciado
        Restaurante[] restaurantes = new Restaurante[CANT_RESTAURANTES];
        Shop shop = new Shop();
        Parque parque = new Parque();
        AdministradorTour adminTour = new AdministradorTour();
        Snorkel snorkel = new Snorkel(10);
        Faro faro = new Faro(10);

        for (int i = 0; i < CANT_RESTAURANTES; i++) {
            restaurantes[i] = new Restaurante(10);
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Visitante(parque, adminTour, restaurantes, shop, snorkel, faro), "VISITANTE " + i).start();
        }

        new Thread(new RelojEstacionamiento(adminTour), "RELOJ ESTACIONAMIENTO ").start();

        new Thread(new RelojParque(parque), "RELOJ PARQUE").start();

        new Thread(new AsistenteSnorkel(snorkel), "Asistente Snorkel 1 ").start();
        new Thread(new AsistenteSnorkel(snorkel), "Asistente Snorkel 2 ").start();

        new Thread(new AsistenteFaro(faro), "Asistente Faro ").start();

    }

}