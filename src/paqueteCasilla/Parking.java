package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

public class Parking extends Especial{
    public Parking(String nombre, String tipo, int posicion, Jugador duenho,Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        // El jugador cobra el bote de los impuestos
        System.out.println("Se te suma el bote " + Tablero.getBote());
        actual.sumarFortuna(Tablero.getBote());
        actual.premiosinversionesbote = actual.premiosinversionesbote + Tablero.getBote();
        Tablero.setBote(0);
        return true;
    }

    public float getAlquiler(){return 0;}
}
