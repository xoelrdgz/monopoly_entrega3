package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;

public abstract class Especial extends Casilla {



    public Especial(String nombre, String tipo, int posicion, Jugador duenho,Grupo grupo) {
        super(nombre,tipo,posicion,duenho,grupo);

    }

public abstract boolean evaluarCasilla(Jugador actual, Jugador banca);
    
}
