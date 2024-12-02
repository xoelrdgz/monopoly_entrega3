package paqueteExcepcion.propiedad;


import paqueteExcepcion.movimiento.MovimientoException;

public class PropiedadYaHipotecadaException extends PropiedadException {
    public PropiedadYaHipotecadaException() {
        super("La propiedad ya está hipotecada y no se puede volver a hipotecar."); // Mensaje predeterminado
    }

    public PropiedadYaHipotecadaException(String message) {
        super(message); // Mensaje personalizado
    }
}
