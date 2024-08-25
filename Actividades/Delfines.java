package Actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import util.TurnosDesfines;

public class Delfines {
    private final int CANT_PILETAS = 4;
    private TurnosDesfines[] turnosDisponibles = new TurnosDesfines[4];
    private List<Semaphore> semaphores = new ArrayList<>();
    private CyclicBarrier barrera = new CyclicBarrier(30);

    public Delfines() {
        for (int i = 0; i < turnosDisponibles.length; i++) {
            turnosDisponibles[i] = new TurnosDesfines(10 + (2 * i));
        }
        for (int i = 0; i < this.CANT_PILETAS; i++) {
            this.semaphores.add(new Semaphore(10));
        }
    }

    public int reservarLugar() {
        boolean reservaHecha = false;
        boolean pudoReservar;
        int turnoReservado = -1;
        int i = 0;
        while (!reservaHecha && i < turnosDisponibles.length) {
            pudoReservar = turnosDisponibles[i].reservarLugar();
            if (pudoReservar) {
                reservaHecha = true;
                turnoReservado = turnosDisponibles[i].getTurno();
            }
        }
        return turnoReservado;
    }

    public void nadarConDelfines() {
        try {
            barrera.await();
            System.out.println(Thread.currentThread().getName() + " está nadando con los delfines.");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()
                    + " No se alcanzó la cantidad mínima de personas para iniciar la atracción. Continua con otras atracciones");
        } catch (BrokenBarrierException e) {
            System.out.println(Thread.currentThread().getName()
                    + " No se alcanzó la cantidad mínima de personas para iniciar la atracción. Continua con otras atracciones");
        }
    }

    public void resetearBarrera(int horaActual) {
        if (horaActual > 10 && !this.barrera.isBroken()) {
            if (this.barrera.getNumberWaiting() > 0) {
                this.barrera.reset();
            }
        }
    }
}
