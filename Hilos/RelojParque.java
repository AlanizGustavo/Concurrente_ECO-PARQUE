package Hilos;

import Actividades.Delfines;
import Actividades.Parque;

public class RelojParque implements Runnable {
    private Parque parque;
    private Delfines delfines;

    public RelojParque(Parque parque, Delfines delfines) {
        this.parque = parque;
        this.delfines = delfines;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(8000);
                this.parque.pasarHora();
                if (parque.getHoraActual() > 12) {
                    new Thread(new RelojDelfines(delfines, parque), "RELOJ DELFINES").start();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
