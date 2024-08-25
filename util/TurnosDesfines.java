package util;

public class TurnosDesfines {
    private int horario;
    private int cantVisitantes = 0;
    private final int CANT_MAX_VISITANTE_POR_TURNO = 40;

    public TurnosDesfines(int horario) {
        this.horario = horario;
    }

    public synchronized boolean reservarLugar() {
        boolean pudoReservar;
        if (this.cantVisitantes < this.CANT_MAX_VISITANTE_POR_TURNO) {
            this.cantVisitantes++;
            pudoReservar = true;
        } else {
            pudoReservar = false;
        }
        return pudoReservar;
    }

    public int getTurno() {
        return this.horario;
    }
}
