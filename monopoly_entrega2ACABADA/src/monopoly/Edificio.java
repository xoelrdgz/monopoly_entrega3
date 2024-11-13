package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Edificio {

    // Atributos de la clase Edificio
    private final Casilla casilla;
    private final String tipoEdificio;
    private int numEdificios;
    private int maxEdificios;
    private int costeEdificio;
    private ArrayList<String> ids;

    // Numero de edificios construidos
    static private int nIDCasas = 0;
    static private int nIDHoteles = 0;
    static private int nIDPiscinas = 0;
    static private int nIDPistasDeporte = 0;

    // Constructor de la clase Edificio, recibe una casilla y un tipo de edificio
    public Edificio(Casilla casilla, String tipoEdificio) {
        this.casilla = casilla;
        this.tipoEdificio = tipoEdificio;
        this.numEdificios = 0;
        calcCostes();
        calcMaxEdificios();
        this.ids = casilla.getIds();
    }

    // Getters
    public int getNumEdificios() {
        return numEdificios;
    }

    public int getMaxEdificios() {
        return maxEdificios;
    }

    public int getCosteEdificio() {
        return costeEdificio;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    // Setters
    public void setNumEdificios(int numEdificios) {
        this.numEdificios = numEdificios;
    }

    public void setMaxEdificios(int maxEdificios) {
        this.maxEdificios = maxEdificios;
    }

    public void setCosteEdificio(int costeEdificio) {
        this.costeEdificio = costeEdificio;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    // Método para construir un edificio
    public void construirEdificio() {
        Jugador propietario = this.casilla.getDuenho();
        calcCostes();
        if (casilla.getHipotecada()) {
            System.out.println("Esta casilla esta hipotecada, no puedes construir.");
            return;
        }
        if (esMaxEdificios() == -1) {
            return;
        }
        if (propietario.getFortuna() < costeEdificio) {
            System.out.println("No tienes suficiente dinero para construir.");
            return;
        }

        switch (tipoEdificio) {
            case "Casa":
                if (casilla.getCasas().getNumEdificios() == 4) {
                    System.out.println("Ya tienes 4 casas edificadas, edifica un hotel.");
                    return;
                }
                propietario.setFortuna(propietario.getFortuna() - costeEdificio);
                propietario.dineroinvertido = propietario.dineroinvertido + costeEdificio;
                this.numEdificios++;
                casilla.casillahacostado = casilla.casillahacostado + costeEdificio;
                propietario.valorpropiedades = propietario.valorpropiedades + costeEdificio;
                casilla.getCasas().setNumEdificios(casilla.getCasas().getNumEdificios() + 1);
                crearIDedif("Casa");
                break;

            case "Hotel":
                if (casilla.getCasas().getNumEdificios() < 4) {
                    System.out.println("No puedes construir un hotel, debes tener 4 casas edificadas.");
                    return;
                }
                propietario.setFortuna(propietario.getFortuna() - costeEdificio);
                propietario.dineroinvertido = propietario.dineroinvertido + costeEdificio;
                // Se eliminan las 4 casas y se añade un hotel
                casilla.getCasas().setNumEdificios(casilla.getCasas().getNumEdificios() - 4);
                casilla.casillahacostado = casilla.casillahacostado + costeEdificio;
                for (int i = 0; i < 4; i++) {

                    casilla.getCasas().getIds().remove(casilla.getCasas().getIds().size() - 1);
                }
                this.numEdificios++;
                propietario.valorpropiedades = propietario.valorpropiedades + costeEdificio;
                casilla.getHoteles().setNumEdificios(casilla.getHoteles().getNumEdificios() + 1);
                crearIDedif("Hotel");
                break;

            case "Piscina":
                if (casilla.getHoteles().getNumEdificios() == 0 || casilla.getCasas().getNumEdificios() < 2) {
                    System.out.println("No puedes construir una piscina, necesitas 1 hotel y 2 casas.");
                    return;
                }
                propietario.setFortuna(propietario.getFortuna() - costeEdificio);
                propietario.dineroinvertido = propietario.dineroinvertido + costeEdificio;
                casilla.getPiscinas().setNumEdificios(casilla.getPiscinas().getNumEdificios() + 1);
                casilla.casillahacostado = casilla.casillahacostado + costeEdificio;
                this.numEdificios++;
                propietario.valorpropiedades = propietario.valorpropiedades + costeEdificio;
                crearIDedif("Piscina");
                break;

            case "PistaDeporte":
                if (casilla.getHoteles().getNumEdificios() < 2) {
                    System.out.println("No puedes construir una pista de deporte, necesitas 2 hoteles.");
                    return;
                }
                propietario.setFortuna(propietario.getFortuna() - costeEdificio);
                propietario.dineroinvertido = propietario.dineroinvertido + costeEdificio;
                casilla.getPistasDeporte().setNumEdificios(casilla.getPistasDeporte().getNumEdificios() + 1);
                this.numEdificios++;
                casilla.casillahacostado = casilla.casillahacostado + costeEdificio;
                propietario.valorpropiedades = propietario.valorpropiedades + costeEdificio;
                crearIDedif("PistaDeporte");
                break;

            default:
                System.out.println("No se ha podido construir el edificio.");
                return;
        }
        System.out.println("Has construido un(a) " + tipoEdificio + " por " + costeEdificio + " €");
    }

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    public int esMaxEdificios() {
        if (numEdificios == maxEdificios) {
            System.out.println("Ya tienes el número máximo permitido de " + tipoEdificio + "s edificados.");
            return -1;
        }
        return 0;
    }

    // Método que calcula el coste de un edificio
    public void calcCostes() {
        switch (tipoEdificio) {
            case "Casa", "Hotel":
                costeEdificio = (int) (casilla.getValor() * 0.6);
                break;
            case "Piscina":
                costeEdificio = (int) (casilla.getValor() * 0.4);
                break;
            case "PistaDeporte":
                costeEdificio = (int) (casilla.getValor() * 1.25);
                break;
        }
    }

    // Método que calcula el número máximo de edificios que se pueden construir,
    // dependiendo del color del grupo
    public void calcMaxEdificios() {
        String color = casilla.getGrupo().getColorGrupo();
        maxEdificios = (color.equals("negro") || color.equals("azul")) ? 2 : 3;
    }

    // Método que crea los identificadores de cada edificio
    public void crearIDedif(String edificio) {
        int num;
        String stringAux = switch (edificio) {
            case "Casa" -> {
                num = nIDCasas++;
                yield "casa-" + num;
            }
            case "Hotel" -> {
                num = nIDHoteles++;
                yield "hotel-" + num;
            }
            case "Piscina" -> {
                num = nIDPiscinas++;
                yield "piscina-" + num;
            }
            case "PistaDeportes" -> {
                num = nIDPistasDeporte++;
                yield "deporte-" + num;
            }
            default -> "";
        };

        ids.add(stringAux);
    }
}