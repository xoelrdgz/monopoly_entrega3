package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;

public abstract class Salida extends Especial{
    public Salida(String nombre, String tipo, int posicion, Jugador duenho, float valor,Grupo grupo){
        super(nombre,tipo,posicion,duenho,valor,grupo);
    }
    
}
