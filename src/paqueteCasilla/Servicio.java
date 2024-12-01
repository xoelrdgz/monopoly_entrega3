package paqueteCasilla;

import paqueteJuego.*;

public class Servicio extends Propiedad {

    public Servicio(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, valor, duenho, grupo);}

    @Override
    public float getAlquiler(){float alquiler=0; alquiler=calcularAlquiler(); return alquiler;}

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        if (this.duenho != null && this.duenho != actual) {
            float alquiler = calcularAlquiler();
            if (actual.getFortuna() >= alquiler) {
                actual.sumarFortuna(-alquiler);
                actual.pagoalquileres = actual.pagoalquileres + alquiler;
                this.duenho.sumarFortuna(alquiler);
                this.duenho.cobroalquileres = this.duenho.cobroalquileres + alquiler;
                this.casillahagenerado = this.casillahagenerado + alquiler;
            } else {
                Juego.sinDinero(actual, this.duenho);
                return false; // No puede pagar el alquiler
            }
        } else if (this.duenho == null) {
            consola.imprimir("La casilla " + this.nombre + " está en venta por " + this.valor);
            consola.imprimir("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
        }
        return true;
    }


    @Override
    public float calcularAlquiler() {
        Jugador propietario = this.getDuenho();
        if (propietario == null) {
            return 0;
        }

        int valorDados = Dado.getSuma();

        int numServicios = 0;
        Jugador jugador = this.getDuenho();
        if (jugador.getPropiedades() == null) {
            return 0;
        } else {
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad.getTipo().equalsIgnoreCase("servicio")) {
                    numServicios++;
                }
            }

            float factorServicio = Valor.SUMA_VUELTA / 200;
            if (numServicios == 1) {
                return 4 * valorDados * factorServicio;
            } else if (numServicios == 2) {
                return 10 * valorDados * factorServicio;
            } else {
                return 0;
            }
        }
    }



}
