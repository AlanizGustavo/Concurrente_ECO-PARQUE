package Hilos;

import java.util.logging.Level;
import java.util.logging.Logger;

import util.Tren;

public class ChoferTren implements Runnable {
    Tren tren;

    public ChoferTren(Tren tren) {
        this.tren = tren;
    }

    public void run() {
        while (true) {
            this.tren.irAAnden();
            this.tren.iniciarViaje();
            this.simulaViaje();
            this.tren.notificarLlegadaAActividadGomones();
            this.simulaViaje();

        }
    }

    private void simulaViaje() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            Logger.getLogger(ChoferTren.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
