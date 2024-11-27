package paqueteCasilla;

import paqueteJuego.Grupo;
import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Valor;

public class Transporte extends Propiedad{

    public Transporte(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, valor, duenho, grupo);}


    @Override
    public boolean evaluarCasilla(Jugador actual,Jugador banca){
        if (this.duenho != null && this.duenho != actual) {
            float alquilerTransporte = calcularAlquiler();
            if (actual.getFortuna() >= alquilerTransporte) {
                actual.sumarFortuna(-alquilerTransporte);
                actual.pagoalquileres = actual.pagoalquileres + alquilerTransporte;
                this.duenho.sumarFortuna(alquilerTransporte);
                this.duenho.cobroalquileres = this.duenho.cobroalquileres + alquilerTransporte;
                this.casillahagenerado = this.casillahagenerado + alquilerTransporte;
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

    @Override
    public float getAlquiler(){float alquiler=0; alquiler=calcularAlquiler(); return alquiler;}

    @Override
    public float calcularAlquiler() {
        int numTransportes = 0;
        Jugador jugador = this.getDuenho();
        float alquiler;
        if (jugador.getPropiedades() == null) {
            alquiler = Valor.SUMA_VUELTA;
        } else {
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad.getTipo().equalsIgnoreCase("transporte")) {
                    numTransportes++;
                }
            }

            float porcentaje = switch (numTransportes) {
                case 1 -> 0.25f;
                case 2 -> 0.50f;
                case 3 -> 0.75f;
                case 4 -> 1.00f;
                default -> 0;
            };

            float operacionTransporte = Valor.SUMA_VUELTA;

            alquiler = operacionTransporte * porcentaje;
        }
        return alquiler;
    }
}
