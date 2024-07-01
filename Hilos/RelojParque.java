package Hilos;

import Actividades.Parque;

public class RelojParque implements Runnable {
    private Parque parque;

    public RelojParque(Parque parque) {
        this.parque = parque;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                this.parque.pasarHora();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
