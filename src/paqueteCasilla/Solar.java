package paqueteCasilla;

import paqueteEdificio.*;
import paqueteJuego.Grupo;
import paqueteJuego.Juego;
import paqueteJuego.Jugador;

import java.util.ArrayList;

public class Solar extends Propiedad {
    // Atributos para los diferentes tipos de edificios
    private Edificio casas;
    private Edificio hoteles;
    private Edificio piscinas;
    private Edificio pistasDeporte;

    // Getters para los diferentes tipos de edificios
    public Edificio getCasas() {
        return casas;
    }

    public Edificio getHoteles() {
        return hoteles;
    }

    public Edificio getPiscinas() {
        return piscinas;
    }

    public Edificio getPistasDeporte() {
        return pistasDeporte;
    }

    // Getter para obtener todos los edificios de una casilla
    public ArrayList<Edificio> getEdificios() {
        ArrayList<Edificio> edificios = new ArrayList<>();
        edificios.add(casas);
        edificios.add(hoteles);
        edificios.add(piscinas);
        edificios.add(pistasDeporte);
        return edificios;
    }


    public Solar(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, valor, duenho, grupo);
        setIds(new ArrayList<>(12));
        this.casas = new Casa(this);
        this.hoteles = new Hotel(this);
        this.piscinas = new Piscina(this);
        this.pistasDeporte = new PistaDeporte(this);

    }

    public float getAlquilerSolar(){float alquiler=0; alquiler=calcularAlquiler(); return alquiler;}

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        if (this.duenho != null && this.duenho != actual) {
            // Verificar si la casilla está hipotecada
            if (this.estaHipotecada()) {
                System.out.println("La casilla " + this.nombre + " está hipotecada. No se paga alquiler.");
                return true; // El alquiler no se paga, pero no se considera un fallo
            }

            float alquiler = calcularAlquiler();
            if (actual.getFortuna() >= alquiler) {
                actual.sumarFortuna(-alquiler);
                actual.pagoalquileres = actual.pagoalquileres + alquiler;
                this.duenho.sumarFortuna(alquiler);
                this.duenho.cobroalquileres = this.duenho.cobroalquileres + alquiler;
                this.casillahagenerado = this.casillahagenerado + alquiler;
            } else {
                System.out.println("No puedes pagar el alquiler");
                Juego.sinDinero(actual, this.duenho);
                return false; // No puede pagar el alquiler
            }
        } else if (this.duenho == null) {
            System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor);
            System.out.println("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
        }
        return true;
    }


    // Método para calcular el alquiler de una casilla de tipo solar.
    public float calcularAlquiler() {
        float alquiler = 0;
        if (this.grupo.esDuenhoGrupo(this.duenho)) {
            alquiler = this.valor * 0.2f;
        } else {
            alquiler = this.valor * 0.1f;
        }

        // Ajustar el alquiler según los edificios construidos
        int numCasas = this.getCasas().getNumEdificios();
        int numHoteles = this.getHoteles().getNumEdificios();
        int numPiscinas = this.getPiscinas().getNumEdificios();
        int numPistasDeporte = this.getPistasDeporte().getNumEdificios();

        // Incrementar el alquiler según el número de edificios
        switch (numCasas) {
            case 1 -> alquiler += this.valor * 5; // Una casa incrementa el alquiler 5 veces el valor de la casilla
            case 2 -> alquiler += this.valor * 15; // Dos casas incrementan el alquiler 15 veces el valor de la casilla
            case 3 -> alquiler += this.valor * 35; // Tres casas incrementan el alquiler 35 veces el valor de la casilla
            case 4 -> alquiler += this.valor * 70; // Cuatro casas incrementan el alquiler 70 veces el valor de la
            // casilla
        }
        alquiler += numHoteles * (this.valor * 70); // Cada hotel incrementa el alquiler 70 veces el valor de la casilla
        alquiler += numPiscinas * (this.valor * 25); // Cada piscina incrementa el alquiler 25 veces el valor de la
        // casilla
        alquiler += numPistasDeporte * (this.valor * 25); // Cada pista de deporte incrementa el alquiler 25 veces el
        // valor de la casilla

        return alquiler;
    }


    // Método para incrementar el precio en un 5%, solo si es "solar"
    public void incrementarPrecio() {
        if (this.tipo.equalsIgnoreCase("solar")) {
            this.valor *= 1.05; // Aumenta el precio en un 5%
        }
    }
}
