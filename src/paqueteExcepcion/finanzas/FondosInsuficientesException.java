package paqueteExcepcion.finanzas;

public class FondosInsuficientesException extends FinanzasException {
    public FondosInsuficientesException() {
        super("El jugador no tiene fondos suficientes para completar esta operación."); // Mensaje predeterminado
    }

    public FondosInsuficientesException(String message) {
        super(message); // Mensaje personalizado
    }
}
