package Actividades;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorTour {
    private Semaphore semTour;
    private Semaphore semColectivo;
    private Semaphore mutex;

    private int cantPasajerosEnColectivo = 0;
    private int cantPasajerosRestantes = 0;
    private final int CANT_MAX_PASAJEROS = 25;

    public AdministradorTour() {
        this.semTour = new Semaphore(CANT_MAX_PASAJEROS);
        this.semColectivo = new Semaphore(0, true);
        this.mutex = new Semaphore(1);
    }

    public int cuantosVisitantesRestan() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(AdministradorTour.class.getName()).log(Level.SEVERE, null, e);
        }
        int restantes = this.cantPasajerosRestantes;
        mutex.release();
        return restantes;
    }

    public void esperarPorColectivo() {
        try {
            this.semTour.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(AdministradorTour.class.getName()).log(Level.SEVERE, null, e);
        }
        ;
    }

    public void liberarColectivo() {
        try {
            this.mutex.acquire();
            System.out.println(Thread.currentThread().getName() + ": " + this.cantPasajerosEnColectivo
                    + " PASAJEROS ESTAN VIAJANDO AL PARQUE EN COLECTIVO.");
            this.semColectivo.release(this.cantPasajerosEnColectivo);
            this.semTour.release(cantPasajerosEnColectivo);
            this.cantPasajerosRestantes -= this.cantPasajerosEnColectivo;
            this.cantPasajerosEnColectivo = 0;
        } catch (InterruptedException e) {
            Logger.getLogger(AdministradorTour.class.getName()).log(Level.SEVERE, null, e);
        }
        this.mutex.release();
    }

    public void solicitarTour() {
        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(AdministradorTour.class.getName()).log(Level.SEVERE, null, e);
        }
        this.cantPasajerosRestantes++;
        this.mutex.release();
    }

    public void subirAColectivo() {
        try {
            this.mutex.acquire();
            this.cantPasajerosEnColectivo++;
            this.mutex.release();
            System.out.println(Thread.currentThread().getName() + " ESTA EN EL COLECTIVO.");
            this.semColectivo.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(AdministradorTour.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void simularViaje() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
    }
}
