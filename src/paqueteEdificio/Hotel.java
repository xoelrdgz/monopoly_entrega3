package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
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

        if (getSolar().getCasas().getNumEdificios() < 4) {
            System.out.println("No puedes construir un hotel, debes tener 4 casas edificadas.");
            return;
        }

        propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
        propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
        // Se eliminan las 4 casas y se añade un hotel
        getSolar().getCasas().setNumEdificios(getSolar().getCasas().getNumEdificios() - 4);
        getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
        // Se borran las 4 casas de la lista de ids
        for (int i = 0; i < 4; i++) {
            getSolar().getCasas().getIds().remove(getSolar().getCasas().getIds().size() - 1);
        }
        setNumEdificios(getNumEdificios() + 1);
        propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
        getSolar().getHoteles().setNumEdificios(getSolar().getHoteles().getNumEdificios() + 1);
        crearIDedif("Hotel");

        consola.imprimir("Has construido un hotel por " + getCosteEdificio());
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {
        
        if (getNumEdificios() == getMaxEdificios()) {
            consola.imprimir("Ya tienes el número máximo de hoteles edificados.");
            return -1;
        }

        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getSolar().getValor() * 0.6));
    }
    
}