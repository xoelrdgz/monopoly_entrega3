package paqueteExcepcion.trato;

import paqueteExcepcion.JuegoExcepcion;

public abstract class TratoException extends JuegoExcepcion {
    public TratoException(String message) {
        super(message);
    }
}