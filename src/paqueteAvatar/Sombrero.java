package paqueteAvatar;

import paqueteExcepcion.movimiento.MovimientoInvalidoException;
import paqueteJuego.Jugador;
import paqueteCasilla.Casilla;
import java.util.ArrayList;

public class Sombrero extends Avatar {

    public Sombrero(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(tipo,jugador, lugar, avCreados);

    }



    @Override
    public void moverenavanzado(Jugador jugador, int valorTirada,Avatar avatar,int posicionActual,int nuevaPosicion){
        try{

            moverenbásico(jugador,valorTirada,avatar,posicionActual,nuevaPosicion);
            throw new MovimientoInvalidoException("No hay mov avanzado para este avatar, se procede con mov básico");
    } catch (MovimientoInvalidoException e) {
            consola.imprimir(e.getMessage());
        }
    }
}
