package paqueteExcepcion.propiedad;

import paqueteExcepcion.JuegoExcepcion;

public abstract class PropiedadException extends JuegoExcepcion {
    public PropiedadException(String message) {
        super(message);
    }
}
