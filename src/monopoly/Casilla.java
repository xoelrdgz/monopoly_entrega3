package monopoly;

import partida.*;

import java.util.ArrayList;

import static monopoly.Valor.IMPUESTO_1;
import static monopoly.Valor.IMPUESTO_2;

public class Casilla {

    private String nombre;
    private String tipo;
    private float valor;
    private int posicion;
    private Jugador duenho;
    private Grupo grupo;
    private float impuesto;
    // private float hipoteca;
    private ArrayList<Avatar> avatares; // Avatares que están en esta casilla
    // private Tablero tablero;
    private boolean hipotecada;
    private ArrayList<String> ids;
    public int vecescasilla;
    public float casillahacostado;
    public float casillahagenerado;
    // Constructores y otros métodos...

    // Getters
    public String getNombre() {
        return nombre;
    }

    public float getCasillahacostado() {
        return casillahacostado;
    }

    public float getCasillahagenerado() {
        return casillahagenerado;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public float getValor() {
        return valor;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public boolean getHipotecada() {
        return hipotecada;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

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

    // Setter para añadir el ID al nombre de una Casilla (y así actualizar el
    // tablero a medida que se muevan los avatares)
    public void setNombreAnhadirID(Avatar avatar) {
        this.nombre = this.nombre + " " + avatar.getId();
    }

    // Setter para eliminar el ID al nombre de una Casilla
    public void setNombreEliminarID(Avatar avatar) {
        this.nombre = this.nombre.replaceAll(avatar.getId(), "");
        // this.nombre = this.nombre.replaceAll(" ","");
    }

    // Setter para cambiar el dueño de una casilla
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public void setCasillahacostado(float casillahacostado) {
        this.casillahacostado = casillahacostado;
    }

    public void setCasillahagenerado(float casillahagenerado) {
        this.casillahagenerado = casillahagenerado;
    }

    // Constructores:
    public Casilla() {

    }// Parámetros vacíos

    /*
     * Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte),
     * posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        String[] partesnombre = nombre.split(" ");
        if (partesnombre.length >= 2) {
            this.nombre = partesnombre[0];
        } else {
            this.nombre = nombre;
        }
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.grupo = grupo;
        this.avatares = new ArrayList<>();
        this.vecescasilla = 0;
        this.casillahacostado = 0;
        this.casillahagenerado = 0;
        setIds(new ArrayList<>(12));
        this.casas = new Edificio(this, "Casa");
        this.hoteles = new Edificio(this, "Hotel");
        this.piscinas = new Edificio(this, "Piscina");
        this.pistasDeporte = new Edificio(this, "PistaDeporte");

    }

    /*
     * Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        String[] partesnombre = nombre.split(" ");
        if (partesnombre.length >= 2) {
            this.nombre = partesnombre[0];
        } else {
            this.nombre = nombre;
        }
        this.tipo = "impuesto";
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        this.vecescasilla = 0;
        this.casillahacostado = 0;
        this.casillahagenerado = 0;
    }

    /*
     * Constructor utilizado para crear las otras casillas (Suerte, Caja de
     * comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición
     * en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        String[] partesnombre = nombre.split(" ");
        if (partesnombre.length >= 2) {
            this.nombre = partesnombre[0];
        } else {
            this.nombre = nombre;
        }
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        this.vecescasilla = 0;
        this.casillahacostado = 0;
        this.casillahagenerado = 0;
    }

    // Método para ver los jugadores en casilla
    public ArrayList<Jugador> JugadoresenCasilla() {
        ArrayList<Jugador> Jugadoresencasilla = new ArrayList<>();
        for (int i = 0; i == avatares.size() - 1; i++) {
            Jugadoresencasilla.add(avatares.get(i).getJugador());
        }
        return Jugadoresencasilla;
    }

    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    /*
     * Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de
     * servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las
     * deudas), y false
     * en caso de no cumplirlas.
     */
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        switch (this.tipo) {
            case "solar":
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
                        Menu.sinDinero(actual, this.duenho);
                        return false; // No puede pagar el alquiler
                    }
                } else if (this.duenho == null) {
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor);
                    System.out.println("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
                }
                break;
            case "impuesto":
                String[] partes3 = this.getNombre().split(" ");

                if (partes3[0].equals("Impuesto1")) {
                    System.out.println("Has caido en una casilla de impuesto, has de pagar" + IMPUESTO_1);
                    if (actual.getFortuna() >= IMPUESTO_1) {
                        actual.sumarFortuna(-IMPUESTO_1);
                        banca.sumarFortuna(IMPUESTO_1);
                        Tablero.setBote(Tablero.getBote() + IMPUESTO_1);
                        actual.pagotasasimpuestos = actual.pagotasasimpuestos + IMPUESTO_1;
                    } else {
                        System.out.println("No puedes pagar el impuesto");
                        Menu.sinDinero(actual, Tablero.banca);
                        return false; // No puede pagar el impuesto BANCARROTA LUEGO
                    }
                    break;

                }
                if (partes3[0].equals("Impuesto2")) {
                    System.out.println("Has caido en una casilla de impuesto, has de pagar" + IMPUESTO_2);
                    if (actual.getFortuna() >= IMPUESTO_2) {
                        actual.sumarFortuna(-IMPUESTO_2);
                        banca.sumarFortuna(IMPUESTO_2);
                        Tablero.setBote(Tablero.getBote() + IMPUESTO_2);
                        actual.pagotasasimpuestos = actual.pagotasasimpuestos + IMPUESTO_2;
                    } else {
                        System.out.println("No puedes pagar el impuesto");
                        Menu.sinDinero(actual, Tablero.banca);
                        return false; // No puede pagar el impuesto BANCARROTA LUEGO
                    }
                    break;

                }

            case "servicio":
                if (this.duenho != null && this.duenho != actual) {
                    float alquiler = calcularAlquilerServicio();
                    if (actual.getFortuna() >= alquiler) {
                        actual.sumarFortuna(-alquiler);
                        actual.pagoalquileres = actual.pagoalquileres + alquiler;
                        this.duenho.sumarFortuna(alquiler);
                        this.duenho.cobroalquileres = this.duenho.cobroalquileres + alquiler;
                        this.casillahagenerado = this.casillahagenerado + alquiler;
                    } else {
                        Menu.sinDinero(actual, this.duenho);
                        return false; // No puede pagar el alquiler
                    }
                } else if (this.duenho == null) {
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor);
                    System.out.println("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
                }
                break;

            case "transporte":
                if (this.duenho != null && this.duenho != actual) {
                    float alquilerTransporte = calcularAlquilerTransporte();
                    if (actual.getFortuna() >= alquilerTransporte) {
                        actual.sumarFortuna(-alquilerTransporte);
                        actual.pagoalquileres = actual.pagoalquileres + alquilerTransporte;
                        this.duenho.sumarFortuna(alquilerTransporte);
                        this.duenho.cobroalquileres = this.duenho.cobroalquileres + alquilerTransporte;
                        this.casillahagenerado = this.casillahagenerado + alquilerTransporte;
                    } else {
                        Menu.sinDinero(actual, this.duenho);
                        return false; // No puede pagar el alquiler
                    }
                } else if (this.duenho == null) {
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor);
                    System.out.println("Si desea comprarla, escriba 'comprar " + this.nombre + "'");
                }
                break;

            case "especial":
                String[] partes = this.nombre.split(" ");
                if (partes[0].equals("IrCárcel")) {
                    System.out.println("A la Cárcel!!!");
                    Casilla carcel = Tablero.getTodasCasillas().get(0).get(10);
                    actual.setEnCarcel(true);
                    actual.vecesencarcel++;
                    actual.setTiradasCarcel(0);

                    actual.getAvatar().getLugar().setNombreEliminarID(actual.getAvatar());
                    actual.getAvatar().setLugar(carcel);
                    actual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                }

                break;

            case "parking":
                // El jugador cobra el bote de los impuestos
                System.out.println("Se te suma el bote " + Tablero.getBote());
                actual.sumarFortuna(Tablero.getBote());
                actual.premiosinversionesbote = actual.premiosinversionesbote + Tablero.getBote();
                Tablero.setBote(0);
                break;

            case "caja":
                System.out.println("¡Has caido en una casilla de tipo Comunidad!");
                Menu.accionComunidad(actual, Tablero.getTodasCasillas());
                break;

            case "suerte":
                System.out.println("¡Has caído en una casilla de tipo suerte!");
                Menu.accionSuerte(actual, Tablero.getTodasCasillas());
                break;

            default:
                break;
        }
        return true;
    }

    /*
     * Método usado para comprar una casilla determinada. Parámetros:
     * Jugador que solicita la compra de la casilla.
     * Banca del monopoly (es el dueño de las casillas no compradas aún).
     */
    public void comprarCasilla(Jugador comprador, Jugador banca) {
        if (this.duenho == banca && comprador.getFortuna() >= this.valor) {
            comprador.sumarFortuna(-this.valor);
            comprador.sumarGastos(this.valor);
            comprador.dineroinvertido = comprador.dineroinvertido + this.valor;
            this.casillahacostado = this.casillahacostado + this.valor;
            comprador.valorpropiedades = comprador.valorpropiedades + this.valor;
            comprador.anhadirPropiedad(this);
            this.setDuenho(comprador);
            String[] partes = this.nombre.split(" ");
            System.out.println(comprador.getNombre() + " ha comprado la casilla " + partes[0]);
        } else {
            String[] partes = this.nombre.split(" ");
            System.out.println("No se puede comprar la casilla " + partes[0]);
        }
    }

    // Metodo usado para hipotecar una casilla
    // Método usado para hipotecar una casilla determinada. Parámetros:
    // Jugador que solicita la hipoteca de la casilla.
    public void hipotecarCasilla(Jugador hipotecador, Jugador banca) {
        // Verifica que el jugador sea el dueño de la casilla
        if (this.duenho == hipotecador) {
            // Verifica si la casilla ya está hipotecada
            if (this.estaHipotecada()) {
                String[] partes = this.nombre.split(" ");
                System.out.println("La casilla " + partes[0] + " ya está hipotecada.");
            } else {
                // Calcular la cantidad a obtener por la hipoteca
                float cantidadHipoteca = calcularValorHipoteca();

                // Actualizar la fortuna del jugador
                hipotecador.sumarFortuna(cantidadHipoteca);
                hipotecador.premiosinversionesbote = hipotecador.premiosinversionesbote + cantidadHipoteca;
                hipotecador.sumarGastos(-cantidadHipoteca); // Se suma a la fortuna y se restan gastos
                this.casillahagenerado = this.casillahagenerado + cantidadHipoteca;
                // Marcar la casilla como hipotecada
                this.setHipotecada(true);

                // Informar al jugador sobre la hipoteca
                String[] partes = this.nombre.split(" ");
                System.out.println(hipotecador.getNombre() + " ha hipotecado la casilla " + partes[0]
                        + " y ha recibido " + cantidadHipoteca + " monedas.");
            }
        } else {
            // Mensaje de error si no puede hipotecar
            String[] partes = this.nombre.split(" ");
            System.out.println("No se puede hipotecar la casilla " + partes[0]);
        }
    }

    // Método que calcula el valor de la hipoteca de la casilla
    public float calcularValorHipoteca() {
        // Suponiendo que el valor de la hipoteca es la mitad del valor original
        return this.valor / 2;
    }

    // Método que verifica si la casilla está hipotecada
    public boolean estaHipotecada() {
        return this.hipotecada; // Suponiendo que 'hipotecada' es una variable booleana en la clase
    }

    // Método para establecer el estado de hipoteca de la casilla
    public void setHipotecada(boolean estado) {
        this.hipotecada = estado; // Cambia el estado de la variable booleana
    }

    /*
     * Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de
     * todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.
     */
    /*
     * public void sumarValor(float suma) {
     * this.valor += suma; // Suma el valor adicional a la propiedad actual
     * }
     */

    /*
     * Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.
     */
    public String infoCasilla() {
        String info = "Casilla: " + this.nombre + ", Tipo: " + this.tipo + ", Posición: " + this.posicion + ", Valor: "
                + this.valor;

        if (this.duenho != null) {
            info += ", Dueño: " + this.duenho.getNombre(); // Si hay dueño, muestra el nombre del dueño
        } else {
            info += ", Dueño: Ninguno"; // Si no hay dueño
        }

        return info; // Devuelve la cadena con la información

    }

    /*
     * Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        if (this.duenho == null) { // Si no hay dueño, la casilla está en venta
            return "La casilla " + this.nombre + " está en venta por " + this.valor;
        } else {
            return "La casilla " + this.nombre + " ya tiene dueño.";
        }
    }

    public float getAlquiler() {
        float alquiler = 0;
        switch (this.tipo) {
            case "solar":
                alquiler = calcularAlquiler();
                break;
            case "servicio":
                alquiler = calcularAlquilerServicio();
                break;
            case "transporte":
                alquiler = calcularAlquilerTransporte();
                break;
            case "impuesto":
                alquiler = this.impuesto;
                break;
            default:
                alquiler = 0;
                break;
        }
        return alquiler;
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

    // Método para calcular el alquiler de una casilla de tipo servicio.
    private float calcularAlquilerServicio() {
        Jugador propietario = this.getDuenho();
        if (propietario == null) {
            return 0;
        }

        int valorDados = Dado.getSuma();

        int numServicios = 0;
        Jugador jugador = this.getDuenho();
        if (jugador.getPropiedades() == null) {
            return 0;
        } else {
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad.getTipo().equalsIgnoreCase("servicio")) {
                    numServicios++;
                }
            }

            float factorServicio = Valor.SUMA_VUELTA / 200;
            if (numServicios == 1) {
                return 4 * valorDados * factorServicio;
            } else if (numServicios == 2) {
                return 10 * valorDados * factorServicio;
            } else {
                return 0;
            }
        }
    }

    // Método para calcular el alquiler de una casilla de tipo transporte.
    private float calcularAlquilerTransporte() {
        int numTransportes = 0;
        Jugador jugador = this.getDuenho();
        float alquiler;
        if (jugador.getPropiedades() == null) {
            alquiler = Valor.SUMA_VUELTA;
        } else {
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad.getTipo().equalsIgnoreCase("transporte")) {
                    numTransportes++;
                }
            }

            float porcentaje = switch (numTransportes) {
                case 1 -> 0.25f;
                case 2 -> 0.50f;
                case 3 -> 0.75f;
                case 4 -> 1.00f;
                default -> 0;
            };

            float operacionTransporte = Valor.SUMA_VUELTA;

            alquiler = operacionTransporte * porcentaje;
        }
        return alquiler;
    }

    // Método para obtener los jugadores que están en la cárcel
    public ArrayList<Jugador> getJugadoresEnCarcel() {
        ArrayList<Jugador> jugadorescarcel = new ArrayList<>();
        for (int i = 0; i <= avatares.size() - 1; i++) {
            if (avatares.get(i).getJugador().isEnCarcel()) {
                jugadorescarcel.add(avatares.get(i).getJugador());
            }
        }
        return jugadorescarcel;
    }

    // Método para incrementar el precio en un 5%, solo si es "solar"
    public void incrementarPrecio() {
        if (this.tipo.equalsIgnoreCase("solar")) {
            this.valor *= 1.05; // Aumenta el precio en un 5%
        }
    }

    // Método para obtener casilla a partir de su posicion
    public static ArrayList<Casilla> casillasporposiciones() {
        ArrayList<ArrayList<Casilla>> casillas = Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp = new ArrayList<>();

        for (int posicion = 0; posicion < 40; posicion++) {

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }
        return ppp;
    }

    public float rentabilidadcasilla() {
        int posicioncasilla = this.getPosicion();
        ArrayList<ArrayList<Casilla>> casillas = Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp = new ArrayList<>();

        for (int posicion = 0; posicion < 40; posicion++) {

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }
        return ppp.get(posicioncasilla).casillahagenerado - ppp.get(posicioncasilla).casillahacostado;
    }
}
