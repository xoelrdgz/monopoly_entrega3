package paqueteJuego;

import static paqueteJuego.Valor.IMPUESTO_1;
import static paqueteJuego.Valor.IMPUESTO_2;

import java.util.ArrayList;
import java.util.HashMap;

import paqueteCasilla.*;
public class Tablero {
    // Atributos.
    private static ArrayList<ArrayList<Casilla>> posiciones; // Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; // Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    public static Jugador banca; // Un jugador que será la banca.
    private static float bote = 0; // Bote del juego.

    // Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero() {
        posiciones = new ArrayList<>(); // Inicializar la lista posiciones
        this.grupos = new HashMap<>(); // Inicializar el HashMap de grupos
        banca = new Jugador(); // Inicializar la banca
        inicializarGrupos(); // Inicializar los grupos
    }

    // Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    // Método para inicializar los grupos
    private void inicializarGrupos() {
        grupos.put("negro", new Grupo("negro"));
        grupos.put("cian", new Grupo("cian"));
        grupos.put("magenta", new Grupo("magenta"));
        grupos.put("amarillo", new Grupo("amarillo"));
        grupos.put("rojo", new Grupo("rojo"));
        grupos.put("marron", new Grupo("marron"));
        grupos.put("verde", new Grupo("verde"));
        grupos.put("azul", new Grupo("azul"));
    }

    // Getter para grupos
    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> ladoSur = new ArrayList<>();
        ladoSur.add(new Salida("Salida", "especial", 0, null, null)); // Salida
        ladoSur.add(new Solar("Solar1", "solar", 1, 600000, banca, grupos.get("negro"))); // Solar1
        ladoSur.add(new AccionCajaComunidad("Caja", "caja", 2, null, null)); // Caja de Comunidad
        ladoSur.add(new Solar("Solar2", "solar", 3, 600000, banca, grupos.get("negro"))); // Solar2
        ladoSur.add(new Impuesto("Impuesto1", "impuesto", 4, IMPUESTO_1, null, null)); // Impuestos
        ladoSur.add(new Transporte("Transporte1", "transporte", 5, 1301328.584f, banca, null)); // Transporte1
        ladoSur.add(new Solar("Solar3", "solar", 6, 520000, banca, grupos.get("cian"))); // Solar3
        ladoSur.add(new AccionSuerte("Suerte", "suerte", 7, null, null)); // Suerte
        ladoSur.add(new Solar("Solar4", "solar", 8, 520000, banca, grupos.get("cian"))); // Solar4
        ladoSur.add(new Solar("Solar5", "solar", 9, 520000, banca, grupos.get("cian"))); // Solar5
        ladoSur.add(new Carcel("Carcel", "especial", 10, null, null)); // Cárcel
        posiciones.add(ladoSur); // Añadimos el lado sur al tablero
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(new Solar("Solar6", "solar", 11, 676000, banca, grupos.get("magenta"))); // Solar6
        ladoOeste.add(new Servicio("Servicio1", "servicio", 12, 1301328.584f * 0.75f, banca, null)); // Servicio1
        ladoOeste.add(new Solar("Solar7", "solar", 13, 676000, banca, grupos.get("magenta"))); // Solar7
        ladoOeste.add(new Solar("Solar8", "solar", 14, 676000, banca, grupos.get("magenta"))); // Solar8
        ladoOeste.add(new Transporte("Transporte2", "transporte", 15, 1301328.584f, banca, null)); // Transporte2
        ladoOeste.add(new Solar("Solar9", "solar", 16, 878800, banca, grupos.get("amarillo"))); // Solar9
        ladoOeste.add(new AccionCajaComunidad("Caja", "caja", 17, null, null)); // Caja de Comunidad
        ladoOeste.add(new Solar("Solar10", "solar", 18, 878800, banca, grupos.get("amarillo"))); // Solar10
        ladoOeste.add(new Solar("Solar11", "solar", 19, 878800, banca, grupos.get("amarillo"))); // Solar11
        posiciones.add(ladoOeste); // Añadimos el lado oeste al tablero
    }

    // Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(new Parking("Parking", "parking", 20, null, null)); // Parking gratuito
        ladoNorte.add(new Solar("Solar12", "solar", 21, 1142440, banca, grupos.get("rojo"))); // Solar12
        ladoNorte.add(new AccionSuerte("Suerte", "suerte", 22, null, null)); // Suerte
        ladoNorte.add(new Solar("Solar13", "solar", 23, 1142440, banca, grupos.get("rojo"))); // Solar13
        ladoNorte.add(new Solar("Solar14", "solar", 24, 1142440, banca, grupos.get("rojo"))); // Solar14
        ladoNorte.add(new Transporte("Transporte3", "transporte", 25, 1301328.584f, banca, null)); // Transporte3
        ladoNorte.add(new Solar("Solar15", "solar", 26, 1485172, banca, grupos.get("marron"))); // Solar15
        ladoNorte.add(new Solar("Solar16", "solar", 27, 1485172, banca, grupos.get("marron"))); // Solar16
        ladoNorte.add(new Servicio("Servicio2", "servicio", 28, 1301328.584f * 0.75f, banca, null)); // Servicio2
        ladoNorte.add(new Solar("Solar17", "solar", 29, 1485172, banca, grupos.get("marron"))); // Solar16
        ladoNorte.add(new IrCarcel("IrCárcel", "especial", 30, null, null)); // Ir a la Cárcel
        posiciones.add(ladoNorte); // Añadimos el lado norte al tablero
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(new Solar("Solar18", "solar", 31, 1930723.6f, banca, grupos.get("verde"))); // Solar18
        ladoEste.add(new Solar("Solar19", "solar", 32, 1930723.6f, banca, grupos.get("verde"))); // Solar19
        ladoEste.add(new AccionCajaComunidad("Caja", "caja", 33, null, null)); // Caja de Comunidad
        ladoEste.add(new Solar("Solar20", "solar", 34, 1930723.6f, banca, grupos.get("verde"))); // Solar20
        ladoEste.add(new Transporte("Transporte4", "transporte", 35, 1301328.584f, banca, null)); // Transporte4
        ladoEste.add(new AccionSuerte("Suerte", "suerte", 36, null, null)); // Suerte
        ladoEste.add(new Solar("Solar21", "solar", 37, 3764911.02f, banca, grupos.get("azul"))); // Solar21
        ladoEste.add(new Impuesto("Impuesto2", "impuesto", 38, IMPUESTO_2, null, null)); // Impuestos
        ladoEste.add(new Solar("Solar22", "solar", 39, 3764911.02f, banca, grupos.get("azul"))); // Solar22
        posiciones.add(ladoEste); // Añadimos el lado este al tablero
    }

    // Método para representar el tablero como texto
    @Override
    public String toString() {
        // Inicializa un array bidimensional de 11x11 para representar el tablero
        String[][] board = new String[11][11];

        // Rellena el tablero con espacios en blanco
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                board[i][j] = String.format("%-15s", " ");
            }
        }

        // Obtiene las casillas de cada lado del tablero
        ArrayList<Casilla> ladoSur = posiciones.get(0);
        ArrayList<Casilla> ladoOeste = posiciones.get(1);
        ArrayList<Casilla> ladoNorte = posiciones.get(2);
        ArrayList<Casilla> ladoEste = posiciones.get(3);

        // Llena el lado sur
        for (int i = 0; i < ladoSur.size(); i++) {
            Casilla casilla = ladoSur.get(i);
            String color = getColorGrupo(casilla.getGrupo());
            board[10][10 - i] = color + String.format("%-15s", casilla.getNombre()) + Valor.RESET;
        }

        // Llena el lado oeste
        for (int i = 0; i < ladoOeste.size(); i++) {
            Casilla casilla = ladoOeste.get(i);
            String color = getColorGrupo(casilla.getGrupo());
            board[9 - i][0] = color + String.format("%-15s", casilla.getNombre()) + Valor.RESET + "|";
        }

        // Llena el lado norte
        for (int i = 0; i < ladoNorte.size(); i++) {
            Casilla casilla = ladoNorte.get(i);
            String color = getColorGrupo(casilla.getGrupo());
            board[0][i] = color + String.format("%-15s", casilla.getNombre()) + Valor.RESET;
        }

        // Llena el lado este
        for (int i = 0; i < ladoEste.size(); i++) {
            Casilla casilla = ladoEste.get(i);
            String color = getColorGrupo(casilla.getGrupo());
            board[i + 1][10] = color + String.format("%-14s", casilla.getNombre()) + Valor.RESET;
        }

        // Crea un StringBuilder para construir el tablero como una cadena de texto
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            // Añade la línea superior de cada casilla
            for (int j = 0; j < 11; j++) {
                if (!board[i][j].trim().isEmpty()) {
                    sb.append("+---------------");
                } else {
                    sb.append("                ");
                }
            }
            sb.append("+\n");
            // Añade la información de cada casilla
            for (int j = 0; j < 11; j++) {
                if (!board[i][j].trim().isEmpty()) {
                    sb.append("|").append(board[i][j]);
                } else {
                    sb.append(" ").append(board[i][j]);
                }
            }
            sb.append("|\n");
        }
        // Añade la última línea del tablero
        for (int j = 0; j < 11; j++) {
            if (!board[10][j].trim().isEmpty()) {
                sb.append("+---------------");
            } else {
                sb.append("                ");
            }
        }
        sb.append("+\n");

        // Devuelve el tablero como una cadena de texto
        return sb.toString();
    }

    // Método para obtener el color de un grupo
    private String getColorGrupo(Grupo grupo) {
        if (grupo == null)
            return Valor.RESET;
        return switch (grupo.getColorGrupo()) {
            case "negro" -> Valor.BLACK;
            case "cian" -> Valor.CYAN;
            case "magenta" -> Valor.PURPLE;
            case "amarillo" -> Valor.YELLOW;
            case "rojo" -> Valor.RED;
            case "marron" -> Valor.WHITE; // Non puxo marrón, son as 3:32 da mañá e estou demasiado cansado para buscalo
            case "verde" -> Valor.GREEN;
            case "azul" -> Valor.BLUE;
            default -> Valor.RESET;
        };
    }

    // Getter de todas las casillas
    public static ArrayList<ArrayList<Casilla>> getTodasCasillas() {
        return posiciones;
    }

    // Getter del bote
    public static float getBote() {
        return bote;
    }

    // Setter del bote
    public static void setBote(float nuevoBote) {
        bote = nuevoBote;
    }

    // Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
        for (ArrayList<Casilla> lado : getTodasCasillas()) {
            for (Casilla casilla : lado) {
                String[] partes = casilla.getNombre().split(" ");
                if (partes[0].equalsIgnoreCase(nombre)) {

                    return casilla;
                }
            }
        }
        return null;

    }
    // Metodo incrementar precio solares

    public void incrementarPrecioSolares() {
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                // Verificamos si la casilla es de tipo "solar"
                if (casilla.getTipo().equalsIgnoreCase("solar")) {
                    Solar solar = (Solar) casilla;
                    solar.incrementarPrecio(); // Incrementa el precio de la casilla "solar"
                }
            }
        }
    }

}
