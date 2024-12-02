package paqueteExcepcion.carta;

import paqueteExcepcion.finanzas.FinanzasException;

public class JugadoresNoDefinidosException extends CartaException {
    public JugadoresNoDefinidosException() {
        super("No hay jugadores definidos en el juego para procesar esta carta."); // Mensaje predeterminado
    }

    public JugadoresNoDefinidosException(String message) {
        super(message); // Mensaje personalizado
    }
}
