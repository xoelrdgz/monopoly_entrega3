package paqueteEdificio;

import paqueteCasilla.Casilla;
import paqueteConsola.ConsolaNormal;
import paqueteJuego.Jugador;

public class PistaDeporte extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public PistaDeporte(Casilla casilla) {
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

        if (getCasilla().getHoteles().getNumEdificios() < 2) {
            System.out.println("No puedes construir una pista de deporte, necesitas 2 hoteles.");
            return;
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
        getCasilla().getPistasDeporte().setNumEdificios(getCasilla().getPistasDeporte().getNumEdificios() + 1);
        setNumEdificios(getNumEdificios() + 1);
        getCasilla().casillahacostado = getCasilla().casillahacostado + getCosteEdificio();
        propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
        crearIDedif("PistaDeporte");

        consola.imprimir("Has construido una pista de deportes por " + getCosteEdificio());
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {

        if (getNumEdificios() == getMaxEdificios()) {
            consola.imprimir("Ya tienes el número máximo de pistas de deporte edificadas.");
            return -1;
        }

        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getCasilla().getValor() * 1.25));
    }
    
}