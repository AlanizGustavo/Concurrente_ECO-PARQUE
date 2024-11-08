package Actividades;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import Hilos.FinDelJuegoRestart;

public class Gomones {

    private int cantGomonesIndividuales, cantGomonesDobles;
    private int cantGomonesJuntos;
    private Semaphore gomonesIndividuales, gomonesDobles;
    private Semaphore mutex; // Para exclusion mutua
    private Semaphore ganador; // Para determinar un ganador
    private Semaphore mandarSimple; // Rendevous entre clientes y gomones
    private Semaphore mandarDoble; // Rendevous entre clientes y gomones
    private Semaphore clientesBajarse;// Rendevous para avisar a los clientes que pueden bajarse
    private Semaphore esperarInicioDeCarrera; // necesario para que no empiece una carrera antes de que finalice la otra

    private CyclicBarrier barrera, meta; // Representan las lineas de meta y de llegada
    private int cantGomonesIndActual, cantGomonesDoblesActual; // La cantidad de cada tipo de gomon que se tiro en un
                                                               // juego

    private Thread restartCarrera;

    public Gomones(int cantGomonesIndivuales, int cantGomonesDobles, int cantGomonesJuntos) {
        this.cantGomonesIndividuales = cantGomonesIndivuales;
        this.cantGomonesDobles = cantGomonesDobles;
        this.cantGomonesJuntos = cantGomonesJuntos;

        gomonesIndividuales = new Semaphore(this.cantGomonesIndividuales);
        gomonesDobles = new Semaphore(this.cantGomonesDobles * 2);
        mutex = new Semaphore(1);
        clientesBajarse = new Semaphore(0);
        ganador = new Semaphore(1);
        mandarSimple = new Semaphore(0);
        mandarDoble = new Semaphore(0);
        esperarInicioDeCarrera = new Semaphore(cantGomonesJuntos);

        restartCarrera = new Thread(new FinDelJuegoRestart(this));

        barrera = new CyclicBarrier(cantGomonesJuntos);
        meta = new CyclicBarrier(cantGomonesJuntos, restartCarrera);
    }

    public void liberarGomon(int tipo) {
        try {
            if (tipo == 1) {
                gomonesIndividuales.acquire();
                System.out.println(Thread.currentThread().getName() + " agarro un gomon individual");
                mandarSimple.release();
            } else {
                gomonesDobles.acquire();
                System.out.println(Thread.currentThread().getName() + " agarro un gomon doble");
                mandarDoble.release();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void bajarse() {
        try {
            clientesBajarse.acquire();
        } catch (InterruptedException e) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void correrCarrera(int tipo) {
        try {
            if (tipo == 1) {
                mandarSimple.acquire();
            } else {
                mandarDoble.acquire(2);
            }

            esperarInicioDeCarrera.acquire();
            barrera.await();

            mutex.acquire();

            if (tipo == 1) {
                cantGomonesIndActual++;

            } else {
                cantGomonesDoblesActual++;
            }

            mutex.release();

        } catch (InterruptedException e) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, e);
        } catch (BrokenBarrierException e) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void finalizarCarrera() {
        try {
            if (ganador.tryAcquire()) {
                System.out.println(Thread.currentThread().getName() + " GANO LA CARRERA!!!");
            }
            meta.await();
        } catch (Exception e) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void resetearJuego() {
        try {
            mutex.acquire();
            gomonesIndividuales.release(cantGomonesIndActual);
            gomonesDobles.release(cantGomonesDoblesActual * 2);
            clientesBajarse.release((cantGomonesIndActual + (cantGomonesDoblesActual * 2)));
            cantGomonesIndActual = 0;
            cantGomonesDoblesActual = 0;
            ganador.release();
            esperarInicioDeCarrera.release(cantGomonesJuntos);
            mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Gomones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
