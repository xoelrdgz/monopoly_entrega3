package paqueteCasilla;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;
import paqueteConsola.*;

public class AccionSuerte extends Accion{

    Consola consola = new ConsolaNormal();

    public AccionSuerte(String nombre, String tipo, int posicion, Jugador duenho, Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }
    @Override
    public boolean evaluarCasilla(Jugador actual,Jugador banca){
        consola.imprimir("¡Has caído en una casilla de tipo suerte!");
        Juego.accionSuerte(actual, Tablero.getTodasCasillas());
        return true;
    }

    public float getAlquiler(){return 0;}
    
}
