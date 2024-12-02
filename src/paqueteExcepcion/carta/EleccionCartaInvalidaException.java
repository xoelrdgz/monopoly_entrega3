package paqueteExcepcion.carta;

import paqueteExcepcion.finanzas.FinanzasException;

public class EleccionCartaInvalidaException extends CartaException {
    public EleccionCartaInvalidaException() {
        super("La elección de la carta no es válida. Seleccione una opción válida."); // Mensaje predeterminado
    }

    public EleccionCartaInvalidaException(String message) {
        super(message); // Mensaje personalizado
    }
}
