package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import Hilos.ChoferTren;

public class Tren {

    private int cantAdentro;
    private int capacidadMaxima;
    private boolean enPuerta;
    private boolean bajarse;
    private int timeout;

    public Tren() {
        this.cantAdentro = 0;
        this.capacidadMaxima = 15;
        this.bajarse = false;
        this.enPuerta = true;
        this.timeout = 40000;
    }

    public synchronized void subirAltren() {
        while (cantAdentro >= capacidadMaxima || !enPuerta) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        cantAdentro++;
        this.notifyAll();
    }

    public synchronized void bajarDelTren() {
        while (!bajarse) {
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, e);
            }
            if (timeout > 0 && cantAdentro < capacidadMaxima) {
                System.out
                        .println(Thread.currentThread().getName() + " Se bajo del tren porque no se completo el cupo");
                return;
            }
        }
        cantAdentro--;
        this.notifyAll();
    }

    public synchronized void notificarLlegadaAActividadGomones() {
        bajarse = true;
        this.notifyAll();
    }

    public synchronized void irAAnden() {
        bajarse = false;
        enPuerta = true;
        this.notifyAll();
    }

    public synchronized void iniciarViaje() {
        while (cantAdentro < capacidadMaxima) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        enPuerta = false;
        this.notifyAll();
    }
}
