package Hilos;

import Actividades.Faro;

public class AsistenteFaro implements Runnable {
    private Faro faro;

    public AsistenteFaro(Faro faro) {
        this.faro = faro;
    }

    public void run() {
        while (true) {
            this.faro.asignarTobogan();
        }

    }
}
