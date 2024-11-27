package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;

public abstract class Carcel extends Especial{
    public Carcel(String nombre, String tipo, int posicion, Jugador duenho, Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }
    
}
