package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
import paqueteExcepcion.Comando.ComandoIncompletoException;
import paqueteExcepcion.edificio.LimiteEdificiosException;
import paqueteExcepcion.edificio.SolarHipotecadoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteJuego.Jugador;

public class Hotel extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public Hotel(Solar solar) {
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

            if (getSolar().getCasas().getNumEdificios() < 4) {
                throw new ComandoIncompletoException("No puedes construir un hotel, debes tener 4 casas edificadas.");
            }

            propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
            propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
            // Se eliminan las 4 casas y se añade un hotel
            getSolar().getCasas().setNumEdificios(getSolar().getCasas().getNumEdificios() - 4);
            getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
            // Se borran las 4 casas de la lista de ids
            for (int i = 0; i < 4; i++) {
                // Se elimina el último elemento de la lista
                getSolar().getCasas().getIds().removeLast();
            }
            propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
            getSolar().getHoteles().setNumEdificios(getSolar().getHoteles().getNumEdificios() + 1);
            crearIDedif("Hotel");

            consola.imprimir("Has construido un hotel por " + getCosteEdificio());
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
        try{
            if (getNumEdificios() == getMaxEdificios()) {
                throw new LimiteEdificiosException("Ya tienes el número máximo de hoteles edificados.");

            }
    } catch (LimiteEdificiosException e) {
            consola.imprimir(e.getMessage());
        }


        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getSolar().getValor() * 0.6));
    }
    
}