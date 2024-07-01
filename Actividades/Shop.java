package Actividades;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Shop {
    private Semaphore cajasDisponibles = new Semaphore(2, true);

    public void pagar() {
        try {
            cajasDisponibles.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void finalizarPago() {
        cajasDisponibles.release();
    }
}
