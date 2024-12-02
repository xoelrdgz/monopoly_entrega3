package paqueteExcepcion.edificio;


import paqueteExcepcion.propiedad.PropiedadException;

public class EdificioInvalidoException extends EdificioException {
    public EdificioInvalidoException() {
        super("El edificio especificado no es válido para esta acción."); // Mensaje predeterminado
    }

    public EdificioInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
