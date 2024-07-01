package Hilos;

import Actividades.AdministradorTour;

public class RelojEstacionamiento implements Runnable {
    private AdministradorTour adminTour;

    public RelojEstacionamiento(AdministradorTour adminTour) {
        this.adminTour = adminTour;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(5000);
                this.adminTour.liberarColectivo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (this.adminTour.cuantosVisitantesRestan() > 0);
    }
}
