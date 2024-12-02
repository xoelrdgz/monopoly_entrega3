package paqueteExcepcion.Comando;

import paqueteExcepcion.casilla.CasillaException;

public class ComandoNoReconocidoException extends ComandoException {
    public ComandoNoReconocidoException() {
        super("El comando introducido no es reconocido. Prueba introduciendo ayuda"); // Mensaje predeterminado
    }

    public ComandoNoReconocidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
