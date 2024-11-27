package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;

public abstract class Accion extends Casilla {


    public Accion(String nombre, String tipo, int posicion, Jugador duenho, float valor, Grupo grupo) {
        super(nombre,tipo,posicion,duenho,grupo,valor);

    }

    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca);


}
