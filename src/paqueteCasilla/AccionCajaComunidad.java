package paqueteCasilla;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

public class AccionCajaComunidad extends Accion{

    public AccionCajaComunidad(String nombre, String tipo, int posicion, Jugador duenho, float valor,Grupo grupo){
        super(nombre,tipo,posicion,duenho,valor,grupo);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        System.out.println("¡Has caido en una casilla de tipo Comunidad!");
        Juego.accionComunidad(actual, Tablero.getTodasCasillas());
        return true;
    }
    
}
