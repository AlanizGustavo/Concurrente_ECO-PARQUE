package Hilos;

import Actividades.Delfines;
import Actividades.Parque;

public class RelojDelfines implements Runnable {
    private Delfines delfines;
    private Parque parque;

    public RelojDelfines(Delfines delfines, Parque parque) {
        this.delfines = delfines;
        this.parque = parque;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                delfines.resetearBarrera(parque.getHoraActual());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}