package paqueteExcepcion.carta;

import paqueteExcepcion.finanzas.FinanzasException;

public class CasillaNoEncontradaException extends CartaException {
    public CasillaNoEncontradaException() {
        super("La casilla asociada a la carta no pudo ser encontrada."); // Mensaje predeterminado
    }

    public CasillaNoEncontradaException(String message) {
        super(message); // Mensaje personalizado
    }
}
