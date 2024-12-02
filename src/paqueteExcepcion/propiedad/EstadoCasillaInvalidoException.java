package paqueteExcepcion.propiedad;


public class EstadoCasillaInvalidoException extends PropiedadException {
    public EstadoCasillaInvalidoException() {
        super("El estado de la casilla no permite realizar esta acción."); // Mensaje predeterminado
    }

    public EstadoCasillaInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
