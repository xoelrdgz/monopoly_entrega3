package paqueteEdificio;

import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
import paqueteExcepcion.edificio.LimiteEdificiosException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteExcepcion.propiedad.PropiedadYaHipotecadaException;
import paqueteJuego.Jugador;

public class Piscina extends Edificio {

    private ConsolaNormal consola = new ConsolaNormal();

    // Constructor
    public Piscina(Solar solar) {
        super (solar);
    }

    // Método para construir un edificio
    @Override
    public void construirEdificio() {
        try{
            Jugador propietario = this.getSolar().getDuenho();
            calcCostes();

            if (getSolar().getHipotecada()) {
               throw new PropiedadYaHipotecadaException("Esta casilla esta hipotecada, no puedes construir.");

            }

            if (esMaxEdificios() == -1) {
                return;
            }

            if (propietario.getFortuna() < getCosteEdificio()) {
                throw new FondosInsuficientesException("No tienes suficiente dinero para construir.");

            }

            if (getSolar().getHoteles().getNumEdificios() == 0 || getSolar().getCasas().getNumEdificios() < 2) {
                consola.imprimir("No puedes construir una piscina, necesitas 1 hotel y 2 casas.");
                return;
            }

            propietario.setFortuna(propietario.getFortuna() - getCosteEdificio());
            propietario.dineroinvertido = propietario.dineroinvertido + getCosteEdificio();
            getSolar().getPiscinas().setNumEdificios(getSolar().getPiscinas().getNumEdificios() + 1);
            getSolar().casillahacostado = getSolar().casillahacostado + getCosteEdificio();
            setNumEdificios(getNumEdificios() + 1);
            propietario.valorpropiedades = propietario.valorpropiedades + getCosteEdificio();
            crearIDedif("Piscina");

            consola.imprimir("Has construido una piscina por " + getCosteEdificio());
        } catch (PropiedadYaHipotecadaException e) {
            consola.imprimir(e.getMessage());
        } catch (FondosInsuficientesException e) {
            consola.imprimir(e.getMessage());        }
    }


    // Método que comprueba si se ha alcanzado el número máximo de edificios
    @Override
    public int esMaxEdificios() {
        try{
            if (getNumEdificios() == getMaxEdificios()) {
                throw new LimiteEdificiosException("Ya tienes el número máximo de piscinas edificadas.");

            }
        } catch (LimiteEdificiosException e) {
            consola.imprimir(e.getMessage());
        }

        return 0;
    }

    // Método que calcula el coste de un edificio
    @Override
    public void calcCostes() {
        setCosteEdificio ((int) (getSolar().getValor() * 0.4));
    }
    
}