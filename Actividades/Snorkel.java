package Actividades;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snorkel {
    private int cantAsistentes;
    private int cantClienteEsperando;
    private int cantEquipamientos;
    private Lock lock = new ReentrantLock(true);
    private Condition noHayCliente = lock.newCondition();
    private Condition noHayAsistente = lock.newCondition();
    private Condition noHayEquipamiento = lock.newCondition();

    public Snorkel(int cantEquipamientos) {
        this.cantEquipamientos = cantEquipamientos;
        this.cantClienteEsperando = 0;
        this.cantAsistentes = 0;
    }

    public void ingresarZonaSnorkel() {
        lock.lock();
        cantClienteEsperando++;
        noHayCliente.signalAll();
        while (cantAsistentes <= 0) {
            try {
                noHayAsistente.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Snorkel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cantAsistentes--;
        lock.unlock();
    }

    public void esperarCliente() {
        lock.lock();
        cantAsistentes++;
        while (cantClienteEsperando <= 0)
            try {
                noHayCliente.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Snorkel.class.getName()).log(Level.SEVERE, null, ex);
            }
        cantClienteEsperando--;
        noHayAsistente.signal();
        lock.unlock();
    }

    public void obtenerEquipamiento() {
        lock.lock();
        while (cantEquipamientos <= 0) {
            try {
                noHayEquipamiento.await();
            } catch (Exception e) {
                Logger.getLogger(Snorkel.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        cantEquipamientos--;
        cantAsistentes++;
        noHayAsistente.signal();
        lock.unlock();
    }

    public void salirDeLaPileta() {
        lock.lock();
        cantEquipamientos++;
        noHayEquipamiento.signal();
        lock.unlock();
    }
}
