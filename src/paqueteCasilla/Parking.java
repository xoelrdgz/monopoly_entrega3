package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

public class Parking extends Especial{
    public Parking(String nombre, String tipo, int posicion, Jugador duenho, float valor,Grupo grupo){
        super(nombre,tipo,posicion,duenho,valor,grupo);
    }

    public boolean evaluarCasillaParking(Jugador actual){
        // El jugador cobra el bote de los impuestos
        System.out.println("Se te suma el bote " + Tablero.getBote());
        actual.sumarFortuna(Tablero.getBote());
        actual.premiosinversionesbote = actual.premiosinversionesbote + Tablero.getBote();
        Tablero.setBote(0);
        return true;
    }
}
