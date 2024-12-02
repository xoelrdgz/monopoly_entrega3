package paqueteExcepcion.Comando;

import paqueteExcepcion.JuegoExcepcion;

public class ComandoException extends JuegoExcepcion {
    public ComandoException(String message) {
        super(message);
    }
}