package paqueteEdificio;

import paqueteCasilla.Casilla;
import paqueteConsola.*;
import paqueteJuego.Jugador;

public class Casa extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public Casa(Casilla casilla) {
        super (casilla);
    }

    // Método para construir un edificio
    @Override
    public void construirEdificio() {

        Jugador propietario = this.getCasilla().getDuenho();
        calcCostes();

        if (getCasilla().getHipotecada()) {
            consola.imprimir("Esta casilla esta hipotecada, no puedes construir.");
            return;
        }

        if (esMaxEdificios() == -1) {
            return;
        }

        if (propietario.getFortuna() < getCosteEdificio()) {
            consola.imprimir("No tienes suficiente dinero para construir.");
            return;
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
        setNumEdificios(getNumEdificios() + 1);
        getCasilla().casillahacostado = getCasilla().casillahacostado + getCosteEdificio();
        propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
        getCasilla().getCasas().setNumEdificios(getCasilla().getCasas().getNumEdificios() + 1);
        crearIDedif("Casa");

        consola.imprimir("Has construido una casa por " + getCosteEdificio());
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {
        if (getNumEdificios() == 4) {
            consola.imprimir("Ya tienes el número máximo de casas edificadas, debes edificar un hotel.");
            return -1;
        }

        if (getCasilla().getHoteles().getNumEdificios() == getMaxEdificios()) {
            consola.imprimir("Ya tienes el número máximo de casas y hoteles edificados.");
            return -1;
        }

        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getCasilla().getValor() * 0.6));
    }
    
}