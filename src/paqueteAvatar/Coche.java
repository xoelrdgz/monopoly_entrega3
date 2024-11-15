package paqueteAvatar;

import paqueteJuego.Jugador;
import paqueteCasilla.Casilla;
import java.util.ArrayList;

public class Coche extends Avatar {
    public Coche(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(tipo, jugador, lugar, avCreados);
    }

    // Métodos específicos para Coche
}
