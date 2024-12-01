package paqueteCasilla;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;
import paqueteConsola.*;

public class AccionCajaComunidad extends Accion{

    Consola consola = new ConsolaNormal();

    public AccionCajaComunidad(String nombre, String tipo, int posicion, Jugador duenho,Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        consola.imprimir("¡Has caido en una casilla de tipo Comunidad!");
        Juego.accionComunidad(actual, Tablero.getTodasCasillas());
        return true;
    }

    public float getAlquiler(){return 0;}
    
}
