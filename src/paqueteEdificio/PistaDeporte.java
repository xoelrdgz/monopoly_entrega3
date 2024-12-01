package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
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

        Jugador propietario = this.getSolar().getDuenho();
        calcCostes();

        if (getSolar().getHipotecada()) {
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

        if (getSolar().getHoteles().getNumEdificios() < 2) {
            consola.imprimir("No puedes construir una pista de deporte, necesitas 2 hoteles.");
            return;
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
        getSolar().getPistasDeporte().setNumEdificios(getSolar().getPistasDeporte().getNumEdificios() + 1);
        setNumEdificios(getNumEdificios() + 1);
        getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
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
        setCosteEdificio ((int) (getSolar().getValor() * 1.25));
    }
    
}