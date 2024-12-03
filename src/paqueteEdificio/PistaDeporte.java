package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
import paqueteExcepcion.Comando.ComandoIncompletoException;
import paqueteExcepcion.edificio.LimiteEdificiosException;
import paqueteExcepcion.edificio.SolarHipotecadoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteJuego.Jugador;

public class PistaDeporte extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public PistaDeporte(Solar solar) {
        super (solar);
    }

    // Método para construir un edificio
    @Override
    public void construirEdificio() {
        try{
            Jugador propietario = this.getSolar().getDuenho();
            calcCostes();

            if (getSolar().getHipotecada()) {
                throw new SolarHipotecadoException("Esta casilla esta hipotecada, no puedes construir.");

            }

            if (esMaxEdificios() == -1) {
                return;
            }

            if (propietario.getFortuna() < getCosteEdificio()) {
                throw new FondosInsuficientesException("No tienes suficiente dinero para construir.");

            }

            if (getSolar().getHoteles().getNumEdificios() < 2) {
                throw new ComandoIncompletoException("No puedes construir una pista de deporte, necesitas 2 hoteles.");

            }

            propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
            propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
            getSolar().getPistasDeporte().setNumEdificios(getSolar().getPistasDeporte().getNumEdificios() + 1);

            getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
            propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
            crearIDedif("PistaDeporte");

            consola.imprimir("Has construido una pista de deportes por " + getCosteEdificio());
    } catch (SolarHipotecadoException e) {
            consola.imprimir(e.getMessage());
        } catch (FondosInsuficientesException e) {
            consola.imprimir(e.getMessage());
        } catch (ComandoIncompletoException e) {
            consola.imprimir(e.getMessage());
        }
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {
    try {
        if (getNumEdificios() == getMaxEdificios()) {
            throw new LimiteEdificiosException("Ya tienes el número máximo de pistas de deporte edificadas.");

        }
    } catch (LimiteEdificiosException e) {
        consola.imprimir(e.getMessage());
    }


        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getSolar().getValor() * 1.25));
    }
    
}