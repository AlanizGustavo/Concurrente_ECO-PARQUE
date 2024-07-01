package Hilos;

import Actividades.Snorkel;

public class AsistenteSnorkel implements Runnable {
    private Snorkel snorkel;

    public AsistenteSnorkel(Snorkel snorkel) {
        this.snorkel = snorkel;
    }

    public void run() {
        while (true) {
            snorkel.esperarCliente();
        }
    }
}
