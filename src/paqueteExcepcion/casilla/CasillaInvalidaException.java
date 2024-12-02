package paqueteExcepcion.casilla;

import paqueteExcepcion.carta.CartaException;

public class CasillaInvalidaException extends CasillaException {
    public CasillaInvalidaException() {
        super("La casilla no es válida para realizar esta acción."); // Mensaje predeterminado
    }

    public CasillaInvalidaException(String message) {
        super(message); // Mensaje personalizado
    }
}
