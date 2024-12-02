package paqueteExcepcion.movimiento;


import paqueteExcepcion.carta.CartaException;

public class MovimientoInvalidoException extends MovimientoException {
    public MovimientoInvalidoException() {
        super("El movimiento del avatar no es válido."); // Mensaje predeterminado
    }

    public MovimientoInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
