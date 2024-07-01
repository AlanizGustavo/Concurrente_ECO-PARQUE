package Actividades;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurante {
    int CAPACIDAD_DE_RESTAURENTE;
    int clientesAdentro;

    public Restaurante(int capacidad) {
        this.CAPACIDAD_DE_RESTAURENTE = capacidad;
        this.clientesAdentro = 0;
    }

    public synchronized void ingresar() {
        while (clientesAdentro >= CAPACIDAD_DE_RESTAURENTE) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        // Accede al restaurante
        clientesAdentro++;
    }

    public synchronized void salirRestaurante() {
        clientesAdentro--;
        this.notifyAll();
    }
}
