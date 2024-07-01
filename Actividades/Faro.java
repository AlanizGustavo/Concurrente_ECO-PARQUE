package Actividades;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Tobogan;

public class Faro {
    private final Semaphore mutexCliente;
    private final Semaphore mutexAsistente;
    private final Semaphore mutexToboganAsignado;
    private final Semaphore escalera;
    private final Semaphore tobogan1;
    private final Semaphore tobogan2;

    private Tobogan toboganAsignado = Tobogan.TOBOGAN1;

    public Faro(int capacidadEscalera) {
        this.escalera = new Semaphore(capacidadEscalera);
        this.mutexCliente = new Semaphore(0, true);
        this.mutexAsistente = new Semaphore(1);
        this.mutexToboganAsignado = new Semaphore(1);
        this.tobogan1 = new Semaphore(1, true);
        this.tobogan2 = new Semaphore(1, true);
    }

    public void subirEscalera() {
        try {
            escalera.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(Faro.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println(Thread.currentThread().getName() + " esta en la fila.");
    }

    public Tobogan esperarTobogan() {
        Tobogan tobogan = null;
        try {
            this.mutexCliente.acquire();
            this.mutexToboganAsignado.acquire();
            tobogan = this.toboganAsignado;
            this.mutexToboganAsignado.release();
            this.mutexAsistente.release();
        } catch (InterruptedException e) {
            Logger.getLogger(Faro.class.getName()).log(Level.SEVERE, null, e);
        }
        return tobogan;
    }

    public void asignarTobogan() {
        try {
            this.mutexAsistente.acquire();
            if (this.tobogan1.tryAcquire()) {
                this.mutexToboganAsignado.acquire();
                this.toboganAsignado = Tobogan.TOBOGAN1;
                this.mutexToboganAsignado.release();
                this.tobogan1.release();
            } else if (this.tobogan2.tryAcquire()) {
                this.mutexToboganAsignado.acquire();
                this.toboganAsignado = Tobogan.TOBOGAN2;
                this.mutexToboganAsignado.release();
                this.tobogan2.release();
            } else {
                int tobogan = (int) (Math.random() * 2) + 1;
                if (tobogan == 1) {
                    this.mutexToboganAsignado.acquire();
                    this.toboganAsignado = Tobogan.TOBOGAN1;
                    this.mutexToboganAsignado.release();
                } else {
                    this.mutexToboganAsignado.acquire();
                    this.toboganAsignado = Tobogan.TOBOGAN2;
                    this.mutexToboganAsignado.release();
                }
            }
            this.mutexCliente.release();
        } catch (InterruptedException e) {
            Logger.getLogger(Faro.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void usarTobogan(Tobogan tobogan) {
        try {
            if (tobogan == Tobogan.TOBOGAN1) {
                this.tobogan1.acquire();
                System.out.println(Thread.currentThread().getName() + " esta en el tobogan: " + Tobogan.TOBOGAN1);
            } else {
                this.tobogan2.acquire();
                System.out.println(Thread.currentThread().getName() + " esta en el tobogan: " + Tobogan.TOBOGAN2);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Faro.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void salirDeAtraccion(Tobogan tobogan) {
        System.out.println(Thread.currentThread().getName() + " Llego a la pileta.");
        if (tobogan == Tobogan.TOBOGAN1) {
            this.tobogan1.release();
        } else {
            this.tobogan2.release();
        }
        this.escalera.release();
    }
}
