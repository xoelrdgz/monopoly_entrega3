package paqueteEdificio;

import paqueteCasilla.Solar;

import java.util.ArrayList;

public abstract class Edificio {

    // Atributos de la clase Edificio
    private final Solar solar;
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
    public Edificio(Solar solar) {
        this.solar = solar;
        setNumEdificios(0);
        calcCostes();
        calcMaxEdificios();
        setIds(solar.getIds());
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

    public Solar getSolar() {
        return solar;
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
    public abstract void construirEdificio();

    // Método que comprueba si se ha alcanzado el número máximo de edificios
    public abstract int esMaxEdificios();

    // Método que calcula el coste de un edificio
    public abstract void calcCostes();

    // Método que calcula el número máximo de edificios que se pueden construir,
    // dependiendo del color del grupo
    public void calcMaxEdificios() {
        String color = solar.getGrupo().getColorGrupo();
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