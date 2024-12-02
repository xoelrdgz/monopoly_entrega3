package paqueteExcepcion.propiedad;


import paqueteExcepcion.movimiento.MovimientoException;

public class PropiedadYaCompradaException extends PropiedadException {
    public PropiedadYaCompradaException() {
        super("Esta propiedad ya ha sido comprada por otro jugador."); // Mensaje predeterminado
    }

    public PropiedadYaCompradaException(String message) {
        super(message); // Mensaje personalizado
    }
}
