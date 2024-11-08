package Hilos;

import java.util.Random;

import Actividades.AdministradorTour;
import Actividades.Delfines;
import Actividades.Faro;
import Actividades.Gomones;
import Actividades.Parque;
import Actividades.Restaurante;
import Actividades.Shop;
import Actividades.Snorkel;
import util.Tobogan;
import util.Tren;

public class Visitante implements Runnable {
    private boolean isInTour;
    private boolean almuerzoPendiente = true;
    private boolean meriendaPendiente = true;
    private int turnoDelfines;

    private Parque parque;
    private AdministradorTour adminTour;
    private Restaurante[] restaurantes;
    private Shop shop;
    private Snorkel snorkel;
    private Faro faro;
    private Delfines delfines;
    private Tren tren;
    private Gomones gomones;

    private Random Random = new Random();

    public Visitante(
            Parque parque,
            AdministradorTour adminTour,
            Restaurante[] restaurantes,
            Shop shop,
            Snorkel snorkel,
            Faro faro,
            Delfines delfines,
            Tren tren,
            Gomones gomones) {

        this.isInTour = getNumeroRandom(2) % 2 == 0 ? true : false;
        this.parque = parque;
        this.adminTour = adminTour;
        if (isInTour) {
            this.adminTour.solicitarTour();
        }
        this.restaurantes = restaurantes;
        this.shop = shop;
        this.snorkel = snorkel;
        this.faro = faro;
        this.delfines = delfines;
        this.tren = tren;
        this.gomones = gomones;
    }

    private void comenzarActividades() {
        int actividadesARealizar = this.getNumeroRandom(3);
        while (parque.puedeJugar()) {
            if (this.turnoDelfines > 0 && this.turnoDelfines == parque.getHoraActual()) {
                System.out.println(Thread.currentThread().getName() + " Decidio nadar con delfines");
                this.delfines();
            } else {
                switch (actividadesARealizar) {
                    case 1:
                        if (this.almuerzoPendiente) {
                            System.out.println(Thread.currentThread().getName() + " Decidio almorzar");
                            this.almorzar();
                            this.almuerzoPendiente = false;
                        } else if (this.meriendaPendiente) {
                            System.out.println(Thread.currentThread().getName() + " Decidio merendar");
                            this.merendar();
                            this.meriendaPendiente = false;
                        }
                        break;
                    case 2:
                        System.out.println(
                                Thread.currentThread().getName() + " Decidio que ira a la actividad Snorkel Ilimitado");
                        this.snorkel();
                        break;
                    case 3:
                        System.out
                                .println(Thread.currentThread().getName() + " Decidio ir a la actividad Faro-Mirador");
                        this.faroMirador();
                        break;
                    case 4:
                        System.out
                                .println(Thread.currentThread().getName() + " Decidio que ira a la carrera de Gomones");
                        this.gomones();
                        break;
                }
            }
            actividadesARealizar = this.getNumeroRandom(4);
        }
    }

    private void empezarRecorrido() {
        boolean primeroShop = getNumeroRandom(2) % 2 == 0 ? true : false;
        if (primeroShop) {
            System.out.println(Thread.currentThread().getName() + " Buscando algun souvenir para comprar");
            this.simularTiempo(1000);
            this.shop.pagar();
            this.shop.finalizarPago();
            System.out.println(Thread.currentThread().getName() + " Salio del Shop");
        }
        this.comenzarActividades();
    }

    private void almorzar() {
        if (this.almuerzoPendiente) {
            int restauranteElegido = getNumeroRandom(this.restaurantes.length) - 1;
            restaurantes[restauranteElegido].ingresar();
            System.out.println(Thread.currentThread().getName() + " Esta almorzando");
            this.simularTiempo(1000);
            restaurantes[restauranteElegido].salirRestaurante();
        }
    }

    private void merendar() {
        if (this.meriendaPendiente) {
            int restauranteElegido = getNumeroRandom(this.restaurantes.length) - 1;
            restaurantes[restauranteElegido].ingresar();
            System.out.println(Thread.currentThread().getName() + " Esta merendando");
            this.simularTiempo(1000);
            restaurantes[restauranteElegido].salirRestaurante();
        }
    }

    private void delfines() {
        this.delfines.nadarConDelfines();
    }

    private void faroMirador() {
        this.faro.subirEscalera();
        Tobogan tobogan = this.faro.esperarTobogan();
        this.faro.usarTobogan(tobogan);
        this.simularTiempo(1000);
        this.faro.salirDeAtraccion(tobogan);
    }

    private void gomones() {
        if (this.getNumeroRandom(2) == 1) {
            System.out.println(
                    Thread.currentThread().getName() + ": Decidio ir hasta el inicio de la carrera de gomones en Tren");
            tren.subirAltren();
            tren.bajarDelTren();
        } else {
            System.out.println(
                    Thread.currentThread().getName() + ": Decidio ir hasta la actividad de gomones en bicicleta");
            this.simularTiempo(1000);
        }
        System.out.println(Thread.currentThread().getName() + ": Llego al inicio de la actividad de gomones");

        int nroBolso = Random.nextInt(2);
        if (nroBolso == 0) {
            System.out.println(Thread.currentThread().getName()
                    + ": Dejo su bolso con pertenencias");
        }

        Random r = new Random();
        if (r.nextInt(2) % 2 == 0)
            this.gomones.liberarGomon(1);
        else
            this.gomones.liberarGomon(2);

        gomones.bajarse();

        if (nroBolso == 0) {
            System.out.println(Thread.currentThread().getName() + ": Paso a buscar su bolso con sus pertenencias");
        }
        System.out.println(Thread.currentThread().getName() + ": Se fue de la actividad carrera de gomones");
    }

    private void snorkel() {
        this.snorkel.ingresarZonaSnorkel();
        System.out.println(Thread.currentThread().getName() + " Ingreso a la zona de entrega de equipamiento");
        this.snorkel.obtenerEquipamiento();
        System.out.println(Thread.currentThread().getName() + " Esta disfrutando del snorkel ilimitado");
        this.simularTiempo(3000);
        this.snorkel.salirDeLaPileta();
        System.out.println(Thread.currentThread().getName() + " Finalizo su actividad de snorkel ilimitado");
    }

    private int getNumeroRandom(int bound) {
        return Random.nextInt(bound) + 1;
    }

    private void irAlParque() {
        if (isInTour) {
            adminTour.esperarPorColectivo();
            adminTour.subirAColectivo();
            adminTour.simularViaje();
        } else {
            System.out.println(Thread.currentThread().getName() + " ESTA YENDO AL PARQUE DE MANERA PARTICULAR.");
            this.simularTiempo(2000);
        }
    }

    public void simularTiempo(int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (Exception e) {
        }
    }

    public void run() {
        irAlParque();
        parque.obtenerPulsera();
        boolean puedePasar = parque.entrarAlParque();
        if (puedePasar) {
            this.turnoDelfines = this.delfines.reservarLugar();
            empezarRecorrido();
        }
    }
}
