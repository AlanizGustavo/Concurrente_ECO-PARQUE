package Hilos;

import java.util.logging.Level;
import java.util.logging.Logger;

import Actividades.Gomones;

public class Gomon implements Runnable {
    private int tipo;
    private Gomones gomones;

    public Gomon(int tipo, Gomones gomones) {
        this.tipo = tipo;
        this.gomones = gomones;
    }

    public void run() {
        while (true) {
            this.gomones.correrCarrera(tipo);
            this.simular();
            this.gomones.finalizarCarrera();
        }
    }

    public void simular() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            Logger.getLogger(Gomon.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
