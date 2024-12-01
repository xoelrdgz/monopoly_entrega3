package paqueteCasilla;

import paqueteJuego.Grupo;
import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Tablero;
import paqueteConsola.*;

import static paqueteJuego.Valor.IMPUESTO_1;
import static paqueteJuego.Valor.IMPUESTO_2;

public class Impuesto extends Casilla{

    Consola consola = new ConsolaNormal();

    float impuesto;
    public Impuesto(String nombre, String tipo, int posicion, float impuesto, Jugador duenho, Grupo grupo){
        super(nombre, tipo, posicion, duenho,grupo);
        this.impuesto = impuesto;

    }

    public float getImpuesto() {
        return impuesto;
    }
    @Override
    public float getAlquiler(){float alquiler=0; alquiler=this.impuesto; return alquiler;}

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        String[] partes3 = this.getNombre().split(" ");

        if (partes3[0].equals("Impuesto1")) {
            consola.imprimir("Has caido en una casilla de impuesto, has de pagar" + IMPUESTO_1);
            if (actual.getFortuna() >= IMPUESTO_1) {
                actual.sumarFortuna(-IMPUESTO_1);
                banca.sumarFortuna(IMPUESTO_1);
                Tablero.setBote(Tablero.getBote() + IMPUESTO_1);
                actual.pagotasasimpuestos = actual.pagotasasimpuestos + IMPUESTO_1;
            } else {
                consola.imprimir("No puedes pagar el impuesto");
                Juego.sinDinero(actual, Tablero.banca);
                return false; // No puede pagar el impuesto BANCARROTA LUEGO
            }
            return true;

        }
        if (partes3[0].equals("Impuesto2")) {
            consola.imprimir("Has caido en una casilla de impuesto, has de pagar" + IMPUESTO_2);
            if (actual.getFortuna() >= IMPUESTO_2) {
                actual.sumarFortuna(-IMPUESTO_2);
                banca.sumarFortuna(IMPUESTO_2);
                Tablero.setBote(Tablero.getBote() + IMPUESTO_2);
                actual.pagotasasimpuestos = actual.pagotasasimpuestos + IMPUESTO_2;
            } else {
                consola.imprimir("No puedes pagar el impuesto");
                Juego.sinDinero(actual, Tablero.banca);
                return false; // No puede pagar el impuesto BANCARROTA LUEGO
            }
    }return true;
    }

}


