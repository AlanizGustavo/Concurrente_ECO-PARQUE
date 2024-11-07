package Hilos;

import Actividades.Gomones;

public class FinDelJuegoRestart implements Runnable {

    private Gomones gomones;

    public FinDelJuegoRestart(Gomones gomones) {
        this.gomones = gomones;
    }

    public void run() {
        gomones.resetearJuego();
    }
}
