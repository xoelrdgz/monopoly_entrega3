package paqueteEdificio;

import paqueteCasilla.Casilla;
import paqueteConsola.ConsolaNormal;
import paqueteJuego.Jugador;

public class Piscina extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public Piscina(Casilla casilla) {
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

        if (getCasilla().getHoteles().getNumEdificios() == 0 || getCasilla().getCasas().getNumEdificios() < 2) {
            System.out.println("No puedes construir una piscina, necesitas 1 hotel y 2 casas.");
            return;
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
        getCasilla().getPiscinas().setNumEdificios(getCasilla().getPiscinas().getNumEdificios() + 1);
        getCasilla().casillahacostado = getCasilla().casillahacostado + getCosteEdificio();
        setNumEdificios(getNumEdificios() + 1);
        propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
        crearIDedif("Piscina");

        consola.imprimir("Has construido una piscina por " + getCosteEdificio());
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {

        if (getNumEdificios() == getMaxEdificios()) {
            consola.imprimir("Ya tienes el número máximo de piscinas edificadas.");
            return -1;
        }

        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getCasilla().getValor() * 0.4));
    }
    
}