package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;

public class Carcel extends Especial{
    public Carcel(String nombre, String tipo, int posicion, Jugador duenho, Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }
    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){return true;}
    public float getAlquiler(){return 0;}
}
