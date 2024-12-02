package paqueteExcepcion.movimiento;


import paqueteExcepcion.carta.CartaException;

public class ModoMovimientoInvalidoException extends MovimientoException {
    public ModoMovimientoInvalidoException() {
        super("El modo de movimiento seleccionado no es válido para este avatar."); // Mensaje predeterminado
    }

    public ModoMovimientoInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
