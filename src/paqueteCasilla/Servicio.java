package paqueteCasilla;

import paqueteJuego.*;

public class Servicio extends Propiedad {

    public Servicio(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, valor, duenho, grupo);}

    public float getAlquilerServicio(){float alquiler=0; alquiler=calcularAlquilerServicio(); return alquiler;}

    public boolean evaluarCasillaServicio(Jugador actual){
        if (this.duenho != null && this.duenho != actual) {
            float alquiler = calcularAlquilerServicio();
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
            System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor);
            System.out.println("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
        }
        return true;
    }


    // Método para calcular el alquiler de una casilla de tipo servicio.
    private float calcularAlquilerServicio() {
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
