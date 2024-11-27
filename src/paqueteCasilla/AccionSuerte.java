package paqueteCasilla;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

public class AccionSuerte extends Accion{

    public AccionSuerte(String nombre, String tipo, int posicion, Jugador duenho, float valor,Grupo grupo){
        super(nombre,tipo,posicion,duenho,valor,grupo);
    }
    @Override
    public boolean evaluarCasilla(Jugador actual,Jugador banca){
        System.out.println("¡Has caído en una casilla de tipo suerte!");
        Juego.accionSuerte(actual, Tablero.getTodasCasillas());
        return true;
    }
    
}
