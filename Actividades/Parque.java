package Actividades;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parque {
    private Semaphore molinetes;
    private Semaphore mutexHora;
    private int cantMolinetes = 20;

    private final int HORA_ENTRADA = 9;
    private final int FIN_HORA_ENTRADA = 17;
    private final int HORA_SALIDA = 18;
    private int horaActual = HORA_ENTRADA;

    public Parque() {
        this.mutexHora = new Semaphore(1);
        this.molinetes = new Semaphore(cantMolinetes, true);
    }

    public boolean entrarAlParque() {
        boolean puedeEntrar = false;
        try {
            this.mutexHora.acquire();
            if (this.horaActual >= this.HORA_ENTRADA && this.horaActual < this.FIN_HORA_ENTRADA) {
                this.mutexHora.release();
                this.molinetes.acquire();
                System.out.println(Thread.currentThread().getName() + " ENTRO AL PARQUE");
                molinetes.release();
                puedeEntrar = true;
            } else {
                mutexHora.release();
                System.out.println(Thread.currentThread().getName() + " NO PUEDE ENTRAR, LLEGO TARDE");
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Parque.class.getName()).log(Level.SEVERE, null, e);
        }
        return puedeEntrar;
    }

    public boolean obtenerPulsera() {
        System.out.println(Thread.currentThread().getName() + " OBTUVO SU PULSERA");
        return true;
    }

    public void pasarHora() {
        try {
            this.mutexHora.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(Parque.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(Thread.currentThread().getName() + ": " + this.horaActual);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        this.horaActual++;
        this.mutexHora.release();
    }

    public boolean puedeJugar() {
        try {
            mutexHora.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(Parque.class.getName()).log(Level.SEVERE, null, e);
        }
        boolean puedeJugar = this.horaActual < this.HORA_SALIDA;
        mutexHora.release();
        return puedeJugar;
    }

    public int getHoraActual() {
        return this.horaActual;
    }
}
