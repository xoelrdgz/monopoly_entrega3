package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.*;
import paqueteExcepcion.edificio.LimiteEdificiosException;
import paqueteExcepcion.edificio.SolarHipotecadoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteJuego.Jugador;

public class Casa extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public Casa(Solar solar) {
        super (solar);
    }

    // Método para construir un edificio
    @Override
    public void construirEdificio() {

        Jugador propietario = this.getSolar().getDuenho();
        calcCostes();
        try{
            if (getSolar().getHipotecada()) {
                throw new SolarHipotecadoException("Esta casilla esta hipotecada, no puedes construir.");

            }
        } catch (SolarHipotecadoException e) {
            consola.imprimir(e.getMessage());
        }


        if (esMaxEdificios() == -1) {
            return;
        }
        try{
            if (propietario.getFortuna() < getCosteEdificio()) {
                throw new FondosInsuficientesException("No tienes suficiente dinero para construir.");

            }
        } catch (FondosInsuficientesException e) {
            consola.imprimir(e.getMessage());
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();

        getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
        propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
        getSolar().getCasas().setNumEdificios(getSolar().getCasas().getNumEdificios() + 1);
        crearIDedif("Casa");

        consola.imprimir("Has construido una casa por " + getCosteEdificio());
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {
        try {
            if (getNumEdificios() == 4) {
                throw new LimiteEdificiosException("Ya tienes el número máximo de casas edificadas, debes edificar un hotel.");

            }
        } catch (LimiteEdificiosException e) {
            consola.imprimir(e.getMessage());
        }
        try {
            if (getSolar().getHoteles().getNumEdificios() == getMaxEdificios()) {
                throw new LimiteEdificiosException("Ya tienes el número máximo de casas y hoteles edificados.");

            }
        }catch (LimiteEdificiosException e) {
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