package paqueteCarta;
import paqueteCasilla.Casilla;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import java.util.ArrayList;


public abstract class Carta   {
    public abstract void accion(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas);

    public abstract void accion();
}
