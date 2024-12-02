package paqueteExcepcion.movimiento;


import paqueteExcepcion.carta.CartaException;

public class CasillaNoeExistenteException extends MovimientoException {
    public CasillaNoeExistenteException() {
        super("La casilla especificada no existe en el tablero."); // Mensaje predeterminado
    }

    public CasillaNoeExistenteException(String message) {
        super(message); // Mensaje personalizado
    }
}
