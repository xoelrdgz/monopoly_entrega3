package paqueteExcepcion.movimiento;
import paqueteConsola.*;

import paqueteExcepcion.JuegoExcepcion;

public abstract class MovimientoException extends JuegoExcepcion {
    public MovimientoException(String message) {
        super(message);
    }
}
