package paqueteJuego;

import static paqueteJuego.Valor.SUMA_VUELTA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import paqueteAvatar.*;
import paqueteCasilla.Casilla;
import paqueteCasilla.Impuesto;
import paqueteCasilla.Propiedad;
import paqueteCasilla.Solar;
import paqueteConsola.ConsolaNormal;
import paqueteEdificio.Edificio;
import paqueteExcepcion.Comando.ComandoIncompletoException;
import paqueteExcepcion.Comando.ComandoInvalidoException;
import paqueteExcepcion.Comando.ComandoNoReconocidoException;
import paqueteExcepcion.avatar.AvatarNoEncontradoException;
import paqueteExcepcion.casilla.CasillaInvalidaException;
import paqueteExcepcion.casilla.CasillaNoEncontradaException;
import paqueteExcepcion.edificio.EdificioInvalidoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteExcepcion.propiedad.EstadoCasillaInvalidoException;
import paqueteExcepcion.propiedad.NoTienesPropiedadesException;

public class Juego implements Comando {

    ConsolaNormal consola = new ConsolaNormal();

    // Atributos
    public static ArrayList<Jugador> jugadores; // Jugadores de la partida.
    private static ArrayList<Avatar> avatares; // Avatares en la partida.
    private ArrayList<String> tipoavatar;// Lista con los tipos de avatares (sirve para generar la ID)
    public static int turno = 0; // Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; // Variable para contar el número de lanzamientos de un jugador en un turno.
    public static Tablero tablero; // Tablero en el que se juega.
    private Dado dado1; // Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private boolean tirado; // Booleano para comprobar si el jugador que tiene el turno ha tirado o no.

    public boolean getTirado() {
        return tirado;
    }

    private void setTurno(int turno) {
        Juego.turno = turno;
    }

    private void setTirado(boolean Tirado) {
        this.tirado = Tirado;
    }

    public static Tablero getTablero() {
        return tablero;
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {

        // bienvenida
        consola.imprimir("Bienvenido! El tablero es el siguiente");
        // Paso 1: Crear Tablero
        tablero = new Tablero();
        tablero.generarCasillas(); // Generar las 40 casillas del tablero
        Casilla inicio = Tablero.getTodasCasillas().get(0).get(0);// Casilla de salida

        // Paso 2: Imprimir el tablero
        consola.imprimir(tablero.toString()); // Esto imprimirá el tablero en el formato del método toString()

        // Paso 3: Pedir la cantidad de jugadores
        int numJugadores = 0;
        while (numJugadores < 2 || numJugadores > 6) {
                consola.imprimir("¿Cuántos jugadores participarán? (Entre 2 y 6 jugadores)");
                numJugadores = consola.leerInt();

            try {
                if (numJugadores < 2) {
                    throw new ComandoInvalidoException("El número mínimo de jugadores es 2.");
                } else if (numJugadores > 6) {
                    throw new ComandoInvalidoException("Solo se permiten hasta 6 jugadores.");
                }
            } catch (ComandoInvalidoException e) {
                consola.imprimir( e.getMessage());
            }
        }

        jugadores = new ArrayList<>(); // Inicializamos la lista de jugadores
        avatares = new ArrayList<>();
        tipoavatar = new ArrayList<>();

        // Paso 4: Registrar jugadores esperando comandos como 'crear jugador Pedro
        // coche'
        while (jugadores.size() < numJugadores) {
            try {
                consola.imprimir(
                        "Introduce el comando para crear un jugador: (ej. crear jugador Pedro coche. Los avatares son: Coche, esfinge, sombrero y pelota)");
                String comando = consola.leer();

                // Verificar si el comando empieza con 'crear jugador'
                if (comando.startsWith("crear jugador")) {
                    // Extraer el nombre del jugador y el tipo de avatar
                    String[] partes = comando.split(" ");
                    if (partes.length == 4) {
                        String nombreJugador = partes[2];
                        String tipoAvatar = partes[3];
                        tipoavatar.add(tipoAvatar);

                        // Validar el tipo de avatar
                        if (tipoAvatar.equalsIgnoreCase("coche") || tipoAvatar.equalsIgnoreCase("esfinge")
                                || tipoAvatar.equalsIgnoreCase("sombrero") || tipoAvatar.equalsIgnoreCase("pelota")) {

                            // Crear el jugador y su avatar
                            Jugador jugador = new Jugador(nombreJugador, tipoAvatar, inicio, avatares);
                            jugadores.add(jugador);

                            if (tipoAvatar.equalsIgnoreCase("coche")) {
                                Avatar avatar = new Coche(tipoAvatar, jugador, inicio, avatares);
                                avatares.add(avatar);
                            } else if (tipoAvatar.equalsIgnoreCase("esfinge")) {
                                Avatar avatar = new Esfinge(tipoAvatar, jugador, inicio, avatares);
                                avatares.add(avatar);
                            } else if (tipoAvatar.equalsIgnoreCase("sombrero")) {
                                Avatar avatar = new Sombrero(tipoAvatar, jugador, inicio, avatares);
                                avatares.add(avatar);
                            } else if (tipoAvatar.equalsIgnoreCase("pelota")) {
                                Avatar avatar = new Pelota(tipoAvatar, jugador, inicio, avatares);
                                avatares.add(avatar);
                            }

                            consola.imprimir("Jugador creado: " + nombreJugador + " con avatar " + tipoAvatar);

                        } else {
                            throw new AvatarNoEncontradoException("Tipo de avatar no válido. Usa 'coche', 'esfinge', 'sombrero' o 'pelota'.");
                        }
                    } else {
                        throw new ComandoInvalidoException("Comando incorrecto. Usa: crear jugador <nombre> <avatar>");
                    }
                } else {
                    throw new ComandoNoReconocidoException("Comando no reconocido. Usa: crear jugador <nombre> <avatar>");
                }
            } catch (ComandoNoReconocidoException | ComandoInvalidoException | AvatarNoEncontradoException e) {
                consola.imprimir( e.getMessage());
            }
        }


        consola.imprimir("¡Todos los jugadores han sido creados! Iniciando el juego...");
        for (int i = 0; i <= jugadores.size() - 1; i++) {
            consola.imprimir(
                    "El ID de " + jugadores.get(i).getNombre() + " es " + jugadores.get(i).getAvatar().getId());
        }
        // Paso 5: Inicializar salida de los avatares en tablero
        for (int i = 0; i < avatares.size(); i++) {
            inicio.anhadirAvatar(jugadores.get(i).getAvatar());
            avatares.get(i).setLugar(inicio);
            inicio.setNombreAnhadirID(jugadores.get(i).getAvatar());
        }

        consola.imprimir(tablero.toString());

        // Ahora el juego sigue en bucle a la espera de comandos de los jugadores

        // Bucle de turnos de cada jugador
        while (true) {
            // Código para el movimiento avanzado del coche

            int turno1 = turno - 1;
            if (turno1 < 0) {
                turno1 = jugadores.size() + turno1;
            }
            jugadores.get(turno1).setCasollegaralcuartoturnosextracocheavanzado(false);
            if (jugadores.get(turno1).isEnCarcel()) {
                jugadores.get(turno1).setCocheExtra(false);
                jugadores.get(turno1).turnoscocheavanzado = 0;
            }
            int turnoscocheavanzado1 = jugadores.get(turno1).turnoscocheavanzado + 1;
            if (jugadores.get(turno1).getCocheExtra()) {
                consola.imprimir(jugadores.get(turno1).getNombre() + " lleva " + turnoscocheavanzado1
                        + " turnos extra de los 3 posibles");
                jugadores.get(turno1).turnoscocheavanzado++;
                turno = turno - 1;
                if (turno < 0) {
                    turno = jugadores.size() + turno;
                }
                if (jugadores.get(turno).turnoscocheavanzado == 3) {
                    consola.imprimir("Has llegado a 3 turnos del modo avanzado, este es el último");
                    jugadores.get(turno).turnoscocheavanzado = 0;
                    jugadores.get(turno).setCocheExtra(false);
                    jugadores.get(turno).setCasollegaralcuartoturnosextracocheavanzado(true);
                }
            }
            if (jugadores.get(turno).getCocheProhibido() && jugadores.get(turno).turnossinjugarcocheavanzado < 2) {
                consola.imprimir("Nos saltamos el turno de " + jugadores.get(turno).getNombre()
                        + " debido a que sacó menor a 4");
                jugadores.get(turno).turnossinjugarcocheavanzado++;
                turno++;
            } else if (jugadores.get(turno).getCocheProhibido()
                    && jugadores.get(turno).turnossinjugarcocheavanzado == 2) {
                consola.imprimir("Ya han pasado los 2 turnos sin jugar, puedes jugar");
                jugadores.get(turno).turnossinjugarcocheavanzado = 0;
                jugadores.get(turno).setCocheProhibido(false);
            }

            // El turno de un jugador sigue en bucle hasta que ponga acabar turno o exit
            String nuevoComando;
            while (true) {
                consola.imprimir("Turno de " + avatares.get(turno).getJugador().getNombre());
                if (jugadores.get(turno).isEnCarcel()) {
                    setTirado(true);
                    salirCarcel();
                } else {

                    consola.imprimir(
                            "Introduce un comando para continuar (escribe ayuda para ver los comandos) o escribe 'exit' para terminar:");

                    nuevoComando = consola.leer(); // Cambié el nombre de la variable

                    // Si el jugador quiere salir del juego
                    if (nuevoComando.equalsIgnoreCase("exit")) {
                        consola.imprimir("Saliendo del juego...");
                        break; // Salir del bucle y finalizar la partida
                    }

                    // Aquí puedes analizar el comando y ejecutar las acciones correspondientes

                    analizarComando(nuevoComando);
                    if (nuevoComando.equalsIgnoreCase("acabar turno")) {
                        break;
                    }
                    if (jugadores.get(turno1).isEnCarcel()) {
                        break;
                    }
                }

            }
            if (nuevoComando.equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    /*
     * Método que interpreta el comando introducido y toma la accion
     * correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {
        int i = 0;// Uso de bucle do para repetir el turno en caso de que un comando se introduzca
                  // mal
        do {
            if (i == 2) {
                consola.imprimir("Vuelve a introducir el comando");
                comando = consola.leer();
            }
            // Dividir el comando en partes para analizar la acción y sus parámetros
            String[] partes = comando.split(" ");

            // Obtener la acción principal (primer elemento del comando)
            String accion = partes[0];

            switch (accion.toLowerCase()) {
                case "describir":
                        if (partes.length < 3) {
                            try {
                                throw new ComandoIncompletoException("Comando incompleto. Uso: describir [jugador/avatar/casilla] [nombre/ID]");


                    } catch (ComandoIncompletoException e) {
                            consola.imprimir(e.getMessage());

                    }  } else{
                    // Subcomando que puede ser jugador, avatar o casilla
                    String tipoDescripcion = partes[1];
                    String argumento = partes[2];

                    switch (tipoDescripcion.toLowerCase()) {
                        case "jugador":
                            descJugador(partes);
                            i = 1;// Comando introducido bien: termina el do
                            break;
                        case "avatar":
                            descAvatar(argumento);
                            i = 1;
                            break;
                        case "casilla":
                            descCasilla(argumento);
                            i = 1;
                            break;
                        default:
                            try {
                                i = 2; // Comando erróneo, se repite el proceso
                                throw new ComandoInvalidoException("Tipo de descripción no válido. Uso: describir [jugador/avatar/casilla]");
                            } catch (ComandoInvalidoException e) {
                                consola.imprimir(e.getMessage());
                            }}
                }
                            break;

                case "lanzar":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("dados")) {
                        lanzarDados();
                        i = 1;
                    } else if (partes.length > 1 && partes[1].equalsIgnoreCase("trucados")) {
                        lanzarTrucados();
                        i = 1;
                    } else {
                        try {
                            i = 2; // Comando erróneo, se repite el proceso
                            throw new ComandoInvalidoException("Comando no válido. Uso: lanzar dados");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }}
                    break;

                case "comprar":
                    if (partes.length < 2) {
                        try {
                            i = 2;
                            throw new ComandoIncompletoException("Comando incompleto. Uso: comprar [nombre_casilla]");


                        } catch (ComandoIncompletoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }else {
                    String nombreCasilla = partes[1];
                    comprar(nombreCasilla);
                    i = 1;
                   }
                    break;

                case "salir":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("carcel")) {
                        salirCarcel();
                        i = 1;
                    } else {
                        try {
                            i = 2; // Comando erróneo, se repite el proceso
                            throw new ComandoInvalidoException("Comando no válido. Uso: salir carcel");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }}
                    break;

                case "listar":
                    if (partes.length < 2) {
                        try {
                            i = 2;
                            throw new ComandoIncompletoException("Comando incompleto. Uso: listar [jugadores/avatares/enventa]");


                        } catch (ComandoIncompletoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    String tipoLista = partes[1];

                    switch (tipoLista.toLowerCase()) {
                        case "jugadores":
                            listarJugadores();
                            i = 1;
                            break;
                        case "avatares":
                            listarAvatares();
                            i = 1;
                            break;
                        case "enventa":
                            listarVenta();
                            i = 1;
                            break;
                        case "hipotecados":
                            listarHipoteca();
                            i = 1;
                            break;
                        case "edificios":
                            listarEdificios();
                            i = 1;
                            break;
                        case "edificios grupo":
                            if (partes.length < 3) {
                                try {
                                    i = 2;
                                    throw new ComandoIncompletoException("Comando incompleto. Uso: listar edificios grupo [color_grupo]");


                                } catch (ComandoIncompletoException e) {
                                    consola.imprimir(e.getMessage());
                                }
                            }
                            String colorGrupo = partes[2];
                            listarEdificiosGrupo(colorGrupo);
                            i = 1;
                            break;
                        case "tratos":
                            listarTratos(jugadores.get(turno));
                            i = 1;
                            break;
                        default:

                            try {
                                i = 2;
                                throw new ComandoIncompletoException("Opción de lista no válida. Uso: listar [jugadores/avatares/enventa/hipotecados/edificios]");


                            } catch (ComandoIncompletoException e) {
                                consola.imprimir(e.getMessage());
                            }
                    }
                    break;

                case "estadisticas":
                    if (partes.length < 2) {
                        mostrarEstadisticasPartida();
                    } else if (partes[1].equalsIgnoreCase(jugadores.get(turno).getNombre())) {
                        jugadores.get(turno).mostrarEstadisticasJugador();
                    } else {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Nombre no reconocido");
                    } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    break;

                case "acabar":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("turno")) {
                        acabarTurno();
                        i = 1;
                    } else {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("Comando no valido: Uso - acabar [turno]");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    break;

                case "ayuda":
                    mostrarAyuda(); // Llamada al método para mostrar los comandos disponibles
                    i = 1;
                    break;
                case "siguiente":
                    break;
                case "jugador":
                    jugador();
                    i = 1;
                    break;

                case "ver":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("tablero")) {
                        verTablero();
                        i = 1;
                    } else {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("Comando no valido: Uso ver tablero");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    break;

                case "mover":
                    if (partes.length < 2) {
                        try {
                            i = 2;
                            throw new ComandoInvalidoException("Comando no valido: Uso ver mover [numero_casillas]");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }

                    int casillas = Integer.parseInt(partes[1]);
                    mover(casillas);
                    i = 1;
                    break;

                case "exit":
                    consola.imprimir("Saliendo del juego...");
                    System.exit(0); // Finaliza el programa
                    break;
                case "hipotecar":
                    if (partes.length < 2) {
                        try{

                            i = 2;
                            throw new ComandoInvalidoException("Comando no valido: Uso hipotecar [nombre_casilla]");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    String nombreCasillaHipoteca = partes[1];
                    hipotecar(nombreCasillaHipoteca);
                    i = 1;
                    break;

                case "deshipotecar":
                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("Comando no valido: Uso deshipotecar [nombre_casilla]");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }

                    }
                    String nombreCasillaDeshipoteca = partes[1];
                    deshipotecar(nombreCasillaDeshipoteca);
                    i = 1;
                    break;

                case "edificar":
                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("Comando incompleto. Uso: edificar [casa/hotel/piscina/pistadeporte] [nombre_casilla]");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }

                    }else {
                    String tipoEdific = partes[1];
                    Casilla pos = jugadores.get(turno).getAvatar().getLugar();



                    if (pos instanceof Solar) {
                        Solar solar = (Solar) pos;
                        solar.edificar(tipoEdific);
                        i = 1;
                    } else {
                         try{
                             i = 2;
                             throw new CasillaInvalidaException("No puedes edificar en esta casilla");

                    } catch (CasillaInvalidaException e) {
                             consola.imprimir(e.getMessage());
                         }
                    }}
                    break;

                case "vender":
                    if (partes.length < 4) {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("Comando incompleto. Uso: vender [tipo_edificio] [nombre_casilla] [cantidad]");

                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }

                    }
                    String tipoEdificio = partes[1];
                    String nombreCasillaVenta = partes[2];
                    int cantidad;
                    try {
                        cantidad = Integer.parseInt(partes[3]);
                    } catch (NumberFormatException e) {
                        consola.imprimir("Cantidad no válida. Debe ser un número entero.");
                        i = 2;
                        return;
                    }
                    venderEdificios(tipoEdificio, nombreCasillaVenta, cantidad);
                    i = 1;
                    break;


                case "bancarrota":
                    bancarrotamenu(jugadores.get(turno), Tablero.banca);
                    i = 1;
                    break;

                case "cambiar":
                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoInvalidoException("El uso correcto del comando es: cambiar movimiento");
                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    } else if (partes.length == 2 && partes[1].equalsIgnoreCase("movimiento")) {
                        if (getTirado()) {
                            try{
                                i = 1;
                                throw new ComandoInvalidoException("No se puede cambiar el tipo de movimiento ya que ya se lanzaron los dados");
                            } catch (ComandoInvalidoException e) {
                                consola.imprimir(e.getMessage());
                            }
                        } else {
                            jugadores.get(turno).setModomovimiento();
                            consola.imprimir("El avatar " + jugadores.get(turno).getAvatar().getId()
                                    + " ha pasado a modo de movimiento " + jugadores.get(turno).getModomovimiento());
                            i = 1;
                        }
                    } else {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Error reconociendo el comando introducido");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }}
                    break;
                case "trato":

                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoIncompletoException("Comando incompleto: Uso [jugador] [detalles]");
                        } catch (ComandoIncompletoException e) {
                            consola.imprimir(e.getMessage());
                        }}

                    // trato jugador: cambiar (detalles1, detalles2) detalles puede ser (propiedad/dinero) o (propiedad y dinero)
                    String regex = "trato\\s+(\\w+):\\s+cambiar\\s*\\(([^,]+),\\s*([^\\)]+)\\)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(comando);

                    if (matcher.find()) {
                        String jugador = matcher.group(1); // Nombre del jugador
                        String detalle1 = matcher.group(2).trim(); // Primer detalle
                        String detalle2 = matcher.group(3).trim(); // Segundo detalle

                        String propiedad1 = null, propiedad2 = null;
                        int cantidadDinero1 = 0, cantidadDinero2 = 0;

                        // Analizar detalle1
                        if (detalle1.contains("y")) {
                            // Caso: "Solar1 y 25000"
                            partes = detalle1.split("y");
                            for (String parte : partes) {
                                parte = parte.trim();
                                if (esNumero(parte)) {
                                    cantidadDinero1 = Integer.parseInt(parte);
                                } else {
                                    propiedad1 = parte;
                                }
                            }
                        } else {
                            // Caso simple: "Solar1" o "25000"
                            if (esNumero(detalle1)) {
                                cantidadDinero1 = Integer.parseInt(detalle1);
                            } else {
                                propiedad1 = detalle1;
                            }
                        }

                        // Analizar detalle2
                        if (detalle2.contains("y")) {
                            // Caso complejo con "y"
                            partes = detalle2.split("y");
                            propiedad2 = partes[0].trim();
                            cantidadDinero2 = Integer.parseInt(partes[1].trim());
                        } else {
                            if (esNumero(detalle2)) {
                            cantidadDinero2 = Integer.parseInt(detalle2);
                            } else {
                                propiedad2 = detalle2;
                            }
                        }


                        Jugador jugador1 = jugadores.get(turno);
                        Jugador jugador2 = null;
                        for (Jugador j : jugadores) {
                            if (j.getNombre().equalsIgnoreCase(jugador)) {
                                jugador2 = j;
                                break;
                            }
                        }
                        if (jugador2 == null) {
                            try{
                                throw new ComandoIncompletoException("Jugador no encontrado" + jugador);
                            } catch (ComandoIncompletoException e) {
                                consola.imprimir(e.getMessage());
                            }
                        }

                        // Convertir prpiedad1 y propiedad2 a objetos Propiedad
                        Propiedad propiedad1Obj = null, propiedad2Obj = null;

                        if (propiedad1 != null) {
                            propiedad1Obj = (Propiedad) tablero.encontrar_casilla(propiedad1);
                        }
                        if (propiedad2 != null) {
                            propiedad2Obj = (Propiedad) tablero.encontrar_casilla(propiedad2);
                        }

                        Tratos trato = new Tratos(jugador1, jugador2, cantidadDinero1, cantidadDinero2, propiedad1Obj, propiedad2Obj);

                        // Llamar al método correspondiente
                        trato.proponerTrato();
                    } else {
                        try{
                            throw new ComandoNoReconocidoException("Comando no reconocido");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    i = 1;
                    break;

                case "aceptar":
                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Comando Incompleto: Uso aceptar [id_trato]");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    String idTrato = partes[1];

                    // Buscar el trato por ID en la lista de tratos del jugador
                    Tratos trato = null;
                    for (Tratos t : jugadores.get(turno).getListaTratos()) {
                        if (t.getIdTrato().equals(idTrato)) {
                            trato = t;
                            break;
                        }
                    }

                    if (trato == null) {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Trato no encontrado");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                            break;
                        }
                    }

                    if (trato.getJugador2() == jugadores.get(turno)) {
                        trato.aceptar(idTrato, jugadores.get(turno));
                        i = 1;
                    } else {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("No puedes aceptar un trato que no te han propuesto");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    i = 1;
                    break;


                case "eliminar":
                    if (partes.length < 2) {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Comando Incompleto: Uso eliminar [id_trato]");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }}
                    String idTratoE = partes[1];

                    // Buscar el trato por ID en la lista de tratos del jugador
                    Tratos tratoE = null;
                    for (Tratos t : jugadores.get(turno).getListaTratos()) {
                        if (t.getIdTrato().equals(idTratoE)) {
                            tratoE = t;
                            break;
                        }
                    }

                    if (tratoE == null) {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("Trato no encontrado");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                            break;
                        }
                    }

                    if (tratoE.getJugador1() == jugadores.get(turno) || tratoE.getJugador2() == jugadores.get(turno)) {
                        tratoE.eliminar(idTratoE, jugadores.get(turno));
                        i = 1;
                    } else {
                        try{
                            i = 2;
                            throw new ComandoNoReconocidoException("No puedes eliminar un trato que no has propuesto o recibido");
                        } catch (ComandoNoReconocidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    i = 1;
                    break;
                default:
                    try{
                        i = 2;
                        throw new ComandoNoReconocidoException("Comando no reconocido.");

            } catch (ComandoNoReconocidoException e) {
                        consola.imprimir(e.getMessage());
                    }
            }
        } while (i == 2);

    }

    private boolean esNumero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para ver las estadísticas de la partida
    @Override
    public void mostrarEstadisticasPartida() {
        consola.imprimir("{\n casillaMasRentable: " + casillarentable() + "\n" +
                "grupoMasRentable: " + gruporentable() + "\n" +
                "casillaMasFrecuentada: " + maxcasilla() + "\n" +
                "jugadorMasVueltas: " + maxvueltas() + "\n" +
                "jugadorMasVecesDados: " + maxvecesdado() + "\n" +
                "jugadorEnCabeza: " + jugadorcabeza() +
                "\n}");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    @Override
    public void descJugador(String[] partes) {
        if (partes.length < 3) {
            try {
                throw new ComandoInvalidoException("Comando incompleto. Uso: describir jugador [nombre]");

        } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        String nombreJugador = partes[2];
        Jugador jugador = null;

        // Buscar el jugador por nombre
        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombreJugador)) {
                jugador = j;
                break;
            }
        }

        if (jugador == null) {
            try {
                throw new ComandoInvalidoException("Jugador no encontrado" + nombreJugador);

            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Imprimir detalles del jugador
        consola.imprimir("{");
        consola.imprimir("    nombre: " + jugador.getNombre() + ",");
        consola.imprimir("    avatar: " + jugador.getAvatar().getId() + ",");
        consola.imprimir("    fortuna: " + jugador.getFortuna() + ",");

        // Listar propiedades y edificios
        ArrayList<String> propies = new ArrayList<>();
        for (Casilla propiedad : jugador.getPropiedades()) {
            String[] partes1 = propiedad.getNombre().split(" ");
            propies.add(partes1[0]);
        }
        consola.imprimir("    propiedades: " + propies + ",");

        // Listar edificios
        consola.imprimir("    edificios: {");
        for (Casilla propiedad : jugador.getPropiedades()) {
            if (propiedad instanceof Solar) {
                Solar solar = (Solar) propiedad;
                consola.imprimir("        " + propiedad.getNombre() + ": {");
                consola.imprimir("            casas: " + solar.getCasas().getNumEdificios() + ",");
                consola.imprimir("            hoteles: " + solar.getHoteles().getNumEdificios() + ",");
                consola.imprimir("            piscinas: " + solar.getPiscinas().getNumEdificios() + ",");
                consola.imprimir("            pistas de deporte: " + solar.getPistasDeporte().getNumEdificios());
                consola.imprimir("        },");
            }
        }
        consola.imprimir("    }");
        consola.imprimir("}");
        consola.imprimir("");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    @Override
    public void descAvatar(String ID) {
        Avatar avatar = null;

        if (!ID.startsWith("&")) {
            ID = "&" + ID;
        }

        // Buscar el avatar por ID
        for (Jugador av : jugadores) {
            if (av.getAvatar().getId().equals(ID)) {
                avatar = av.getAvatar();
                break;
            }
        }

        if (avatar == null) {
            try {
                throw new ComandoInvalidoException("Jugador no encontrado" + ID);

            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Imprimir detalles del avatar
        consola.imprimir("{");
        consola.imprimir("    id: " + avatar.getId() + ",");
        consola.imprimir("    tipo: " + avatar.getTipo() + ",");
        if (avatar.getLugar().getNombre().contains("&")) {
            String[] partes = avatar.getLugar().getNombre().split(" ");
            consola.imprimir("    casilla: " + partes[0] + ",");
        } else {
            consola.imprimir("    casilla: " + avatar.getLugar().getNombre());
        }
        consola.imprimir("    jugador: " + avatar.getJugador().getNombre());
        consola.imprimir("}");
        consola.imprimir("");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir
     * nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    @Override
    public void descCasilla(String nombre) {
        String[] partes = nombre.split(" ");
        nombre = partes[0];
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if (casilla == null) {
            try {
                throw new ComandoInvalidoException("Csilla no encontrado" + nombre);

            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }
        consola.imprimir("{");
        switch (casilla.getTipo().toLowerCase()) {
            case "solar":
                Solar solar = (Solar) casilla;
                consola.imprimir("    tipo: " + casilla.getTipo() + ",");
                consola.imprimir("    grupo: " + casilla.getGrupo().getColorGrupo() + ",");
                consola.imprimir("    propietario: "
                        + (casilla.getDuenho() != null ? casilla.getDuenho().getNombre() : "Ninguno") + ",");
                consola.imprimir("    valor: " + solar.getValor() + ",");
                consola.imprimir("    alquiler base: " + casilla.getAlquiler() + ",");
                consola.imprimir("    edificios: {");
                consola.imprimir("        casas: " + solar.getCasas().getNumEdificios() + ",");
                consola.imprimir("        hoteles: " + solar.getHoteles().getNumEdificios() + ",");
                consola.imprimir("        piscinas: " + solar.getPiscinas().getNumEdificios() + ",");
                consola.imprimir("        pistas de deporte: " + solar.getPistasDeporte().getNumEdificios());
                consola.imprimir("    },");
                break;
            case "impuesto":
                Impuesto impuesto = (Impuesto) casilla;
                consola.imprimir("    tipo: " + casilla.getTipo() + ",");
                consola.imprimir("    a pagar: " + impuesto.getImpuesto());
                break;
            case "parking":
                consola.imprimir("    bote: " + Tablero.getBote() + ",");
                consola.imprimir("    jugadores: " + casilla.JugadoresenCasilla());
                break;
            case "carcel":
                consola.imprimir("    salir: " + SUMA_VUELTA / 4 + ",");
                consola.imprimir("    jugadores: " + casilla.getJugadoresEnCarcel());
                break;
            default:
                try{
                    throw new CasillaInvalidaException("Tipo de casilla no válido para descripción.");

        } catch (CasillaInvalidaException e) {
                    consola.imprimir(e.getMessage());
                }
        }
        consola.imprimir("}");
        consola.imprimir("");
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'lanzar
    // dados'.
    @Override
    public void lanzarDados() {

        Jugador jugadorActual = jugadores.get(turno);

        if (getTirado()) {
            try {

                throw new ComandoInvalidoException("Ya has lanzado los dados en este turno");

            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
            return;
        }
        lanzamientos++;
        dado1 = new Dado();
        dado1.hacerTirada();
        dado2 = new Dado();
        dado2.hacerTirada();
        setTirado(true);

        int tirada1 = dado1.getValor();
        int tirada2 = dado2.getValor();
        consola.imprimir("Has sacado un " + tirada1 + " y un " + tirada2);
        Dado.setSuma(tirada1 + tirada2);
        jugadorActual.vecesdados++;

        // Mover avatar

        jugadorActual.getAvatar().moverJugador(jugadorActual, Dado.getSuma());
        consola.imprimir(tablero.toString());

        if (jugadorActual.isEnCarcel()) {
            acabarTurno();
        } else {
            // Comprobar si ha sacado dobles
            if (tirada1 == tirada2 && !jugadorActual.getCocheExtra() && !jugadorActual.getCocheProhibido()
                    && !jugadorActual.casosacarmenosdecuatroenunturnoextracocheavanzado) {
                consola.imprimir("¡Has sacado dobles! Tira otra vez.");
                setTirado(false);
                consola.imprimir(tablero.toString());
                if (lanzamientos == 3) {
                    consola.imprimir("Has sacado dobles 3 veces seguidas. Vas a la cárcel.");
                    jugadorActual.setEnCarcel(true);
                    jugadorActual.vecesencarcel++;
                    jugadorActual.setTiradasCarcel(0);
                    acabarTurno();
                    Casilla carcel = tablero.encontrar_casilla("Carcel");
                    if (carcel != null) {
                        jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                        jugadorActual.getAvatar().setLugar(carcel);
                        jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                        consola.imprimir(tablero.toString());
                    } else {
                        try {
                            throw new CasillaNoEncontradaException("Error: No se encontró la casilla de la carcel");

                        } catch (CasillaNoEncontradaException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                }
            }
            jugadorActual.setCasosacarmenosdecuatroenunturnoextracocheavanzado(false);

        }
    }

    // Método de dados trucados
    @Override
    public void lanzarTrucados() {
        // Corrección de error tipográfico
        if (getTirado()) {
            try {
                throw new ComandoInvalidoException("Ya has lanzado los dados en este turno");

            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
            return;
        }
        consola.imprimir("Introduce los valores de tus dados trucados:");

        // Usar solo una instancia de Scanner

        // Leer los dos números de entrada
        consola.imprimir("Introduce el valor del primer dado: ");
        int tirada1 = consola.leerInt();

        consola.imprimir("Introduce el valor del segundo dado: ");
        int tirada2 = consola.leerInt();

        Jugador jugadorActual = jugadores.get(turno);

        Dado.setSuma(tirada1 + tirada2);
        jugadorActual.vecesdados++;
        setTirado(true);
        lanzamientos++;

        // Mover avatar
        jugadorActual.getAvatar().moverJugador(jugadorActual, Dado.getSuma());
        consola.imprimir(tablero.toString());
        if (jugadorActual.isEnCarcel()) {
            acabarTurno();
        } else {
            // Comprobar si ha sacado dobles
            if (tirada1 == tirada2 && !jugadorActual.getCocheExtra() && !jugadorActual.getCocheProhibido()
                    && !jugadorActual.casosacarmenosdecuatroenunturnoextracocheavanzado) {
                consola.imprimir("¡Has sacado dobles! Tira otra vez.");
                setTirado(false);
                consola.imprimir(tablero.toString());
                if (lanzamientos == 3) {
                    consola.imprimir("Has sacado dobles 3 veces seguidas. Vas a la cárcel.");
                    jugadorActual.setEnCarcel(true);
                    jugadorActual.vecesencarcel++;
                    jugadorActual.setTiradasCarcel(0);
                    acabarTurno();
                    Casilla carcel = tablero.encontrar_casilla("Carcel");
                    if (carcel != null) {
                        jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                        jugadorActual.getAvatar().setLugar(carcel);
                        jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                        consola.imprimir(tablero.toString());
                    } else {
                        try {
                            throw new CasillaNoEncontradaException("Error: No se encontró la casilla de la carcel");

                        } catch (CasillaNoEncontradaException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                }
            }
            jugadorActual.setCasosacarmenosdecuatroenunturnoextracocheavanzado(false);

        }
    }

    // Método para cuando un jugador se queda sin dinero, puede hipotecar o
    // declararse en bancarrota
    public static void sinDinero(Jugador jugador, Jugador acreedor) {

        ConsolaNormal consola = new ConsolaNormal();

        while (true) {
            consola.imprimir(jugador.getNombre() + ", te has quedado sin dinero. Elige una opción:");
            consola.imprimir("1. Hipotecar una propiedad");
            consola.imprimir("2. Declararse en bancarrota");

            int opcion = consola.leerInt();

            if (opcion == 1) {
                ArrayList<Casilla> propiedades = jugador.getPropiedades();
                if (propiedades.isEmpty()) {
                    try{
                        throw new NoTienesPropiedadesException("No tienes propiedades para hipotecar.");

                } catch (NoTienesPropiedadesException e) {
                        consola.imprimir(e.getMessage());
                    }
                }

                consola.imprimir("Elige una propiedad para hipotecar:");
                for (int i = 0; i < propiedades.size(); i++) {
                    Casilla propiedad = propiedades.get(i);
                    consola.imprimir((i + 1) + ". " + propiedad.getNombre());
                }

                int propiedadIndex = consola.leerInt() - 1;

                if (propiedadIndex >= 0 && propiedadIndex < propiedades.size()) {
                    Casilla casilla = propiedades.get(propiedadIndex);
                    if (casilla instanceof Propiedad) {
                        Propiedad propiedad = (Propiedad) casilla;
                        propiedad.hipotecarCasilla(jugador, Tablero.banca);
                        consola.imprimir("Has hipotecado " + casilla.getNombre() + ".");
                    } else {
                        try{
                            throw new EstadoCasillaInvalidoException("Tipo de casilla no valido para hipotecar.");

                        } catch (EstadoCasillaInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                    consola.imprimir("Has hipotecado " + casilla.getNombre() + ".");
                    break;
                } else {
                    try {


                        throw new EstadoCasillaInvalidoException("Indice de propiedad no valido");
                    } catch (EstadoCasillaInvalidoException e) {
                        consola.imprimir(e.getMessage());
                    }
                }
            } else if (opcion == 2) {
                bancarrota(jugador, acreedor);
                break;
            } else {
                try {


                    throw new ComandoInvalidoException("Opción no válida.");
            } catch (ComandoInvalidoException e) {
                    consola.imprimir(e.getMessage());
                }
            }
        }
    }

    // Método para bancarrota desde el menu, de otra forma no iba
    @Override
    public void bancarrotamenu(Jugador jugador, Jugador acreedor) {
        bancarrota(jugador, acreedor);
    }

    // Método para declararse en bancarrota
    // @Override
    public static void bancarrota(Jugador jugador, Jugador acreedor) {

        ConsolaNormal consola = new ConsolaNormal();

        if (acreedor == Tablero.banca) {
            // Transferir toda la fortuna del jugador a la banca
            acreedor.sumarFortuna(jugador.getFortuna());
            jugador.setFortuna(0);
            // Transferir todas las propiedades del jugador a la banca
            for (Casilla propiedad : jugador.getPropiedades()) {
                acreedor.anhadirPropiedad(propiedad);
                propiedad.setDuenho(acreedor);
            }
            consola.imprimir(jugador.getNombre() + " se ha declarado en bancarrota. Toda su fortuna y propiedades se han transferido a la banca.");
        } else {
            // Transferir toda la fortuna del jugador al acreedor
            acreedor.sumarFortuna(jugador.getFortuna());
            acreedor.premiosinversionesbote = acreedor.premiosinversionesbote + jugador.getFortuna();
            jugador.setFortuna(0);
            // Transferir todas las propiedades del jugador al acreedor
            for (Casilla propiedad : jugador.getPropiedades()) {
                acreedor.anhadirPropiedad(propiedad);
                propiedad.setDuenho(acreedor);
            }
            consola.imprimir(jugador.getNombre()
                    + " se ha declarado en bancarrota. Toda su fortuna y propiedades se han transferido a "
                    + acreedor.getNombre() + ".");
        }
    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    @Override
    public void comprar(String nombre) {
        // Buscar la casilla por nombre
        String nombre1 = nombre + " " + jugadores.get(turno).getAvatar().getId();
        if (!jugadores.get(turno).getAvatar().getLugar().getNombre().equals(nombre1)) {
            try {


                throw new CasillaInvalidaException("No estás en esta casilla, no puedes comprarla");
        } catch (CasillaInvalidaException e) {
                consola.imprimir(e.getMessage());
            }
        }
        else {
            Casilla casilla = tablero.encontrar_casilla(nombre);
            if (!listarVenta().contains(nombre)) {
                try {


                    throw new CasillaInvalidaException("Esa casilla no puede comprar");
                } catch (CasillaInvalidaException e) {
                    consola.imprimir(e.getMessage());
                }
            } else {
                if (casilla != null) {
                    // Llamar al método comprarCasilla
                    Jugador jugadorActual = jugadores.get(turno);
                    if (casilla instanceof Propiedad) {
                        Propiedad propiedad = (Propiedad) casilla;
                        propiedad.comprarCasilla(jugadorActual, Tablero.banca);
                    } else {
                        try {


                            throw new CasillaInvalidaException("Este tipo de casilla no puede comprar");
                        } catch (CasillaInvalidaException e) {
                            consola.imprimir(e.getMessage());
                        }
                    }
                } else {
                    consola.imprimir("La casilla " + nombre + " no existe.");
                }
            }
        }
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    @Override
    public void salirCarcel() {
        Jugador jugadorActual = jugadores.get(turno);

        if (!jugadorActual.isEnCarcel()) {
            try {


                throw new CasillaNoEncontradaException("No estas en la carcel");
            } catch (CasillaNoEncontradaException e) {
                consola.imprimir(e.getMessage());
            }
        }

        if (jugadorActual.turnosEnCarcel == 3) {
            if (jugadorActual.getFortuna() >= SUMA_VUELTA / 4) {
                jugadorActual.sumarFortuna(-SUMA_VUELTA / 4);
                jugadorActual.pagotasasimpuestos = jugadorActual.pagotasasimpuestos + SUMA_VUELTA / 4;
                jugadorActual.setEnCarcel(false);
                setTirado(false);
                jugadorActual.turnosEnCarcel = 0;
                consola.imprimir("Has pagado la multa y has salido de la cárcel.");
            } else {
                try {


                    throw new FondosInsuficientesException("No tienes suficiente dinero para pagar la multa");
                } catch (FondosInsuficientesException e) {
                    consola.imprimir(e.getMessage());
                }
            }

        } else if (jugadorActual.turnosEnCarcel < 3) {
            // Opción de pagar la multa o tirar los dados

            consola.imprimir("Elige una opción para salir de la cárcel:");
            consola.imprimir("1. Pagar una multa de " + (SUMA_VUELTA / 4) + ".");
            consola.imprimir("2. Tirar los dados.");

            int opcion = consola.leerInt();

            if (opcion == 1) {
                if (jugadorActual.getFortuna() >= SUMA_VUELTA / 4) {
                    jugadorActual.sumarFortuna(-SUMA_VUELTA / 4);
                    jugadorActual.pagotasasimpuestos = jugadorActual.pagotasasimpuestos + SUMA_VUELTA / 4;
                    jugadorActual.setEnCarcel(false);
                    setTirado(false);
                    jugadorActual.turnosEnCarcel = 0;
                    consola.imprimir("Has pagado la multa y has salido de la cárcel.");
                } else {
                    try {


                        throw new FondosInsuficientesException("No tienes suficiente dinero para pagar la multa");
                    } catch (FondosInsuficientesException e) {
                        consola.imprimir(e.getMessage());
                    }
                }
            } else if (opcion == 2) {

                Dado dado1 = new Dado();
                Dado dado2 = new Dado();
                int tirada1 = dado1.hacerTirada();
                int tirada2 = dado2.hacerTirada();

                consola.imprimir("Has tirado los dados: " + tirada1 + " y " + tirada2);
                jugadorActual.vecesdados++;

                if (tirada1 == tirada2) {
                    jugadorActual.setEnCarcel(false);
                    setTirado(false);
                    consola.imprimir("Has sacado dobles y has salido de la cárcel.");
                    jugadorActual.turnosEnCarcel = 0;
                } else {
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                    consola.imprimir("No has sacado dobles. Sigues en la cárcel.");
                    incrementarTurnosEnCarcel();
                    acabarTurno();

                }

            } else {
                try {


                    throw new ComandoInvalidoException("Opcion no valida");
                } catch (ComandoInvalidoException e) {
                    consola.imprimir(e.getMessage());
                }
            }
        }
    }

    // metodo que incrementa los turnos en carcel
    public void incrementarTurnosEnCarcel() {
        if (jugadores.get(turno).isEnCarcel()) {
            jugadores.get(turno).turnosEnCarcel++; // Incrementar si está en la cárcel
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    @Override
    public ArrayList<String> listarVenta() {
        ArrayList<String> enVenta = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            if (i == 0) {
                for (int j = 0; j <= 10; j++)
                    if (Tablero.getTodasCasillas().get(i).get(j).getDuenho() == Tablero.banca) {
                        String[] partes = Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i == 1) {
                for (int j = 0; j <= 8; j++)
                    if (Tablero.getTodasCasillas().get(i).get(j).getDuenho() == Tablero.banca) {
                        String[] partes = Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i == 2) {
                for (int j = 0; j <= 10; j++)
                    if (Tablero.getTodasCasillas().get(i).get(j).getDuenho() == Tablero.banca) {
                        String[] partes = Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i == 3) {
                for (int j = 0; j <= 8; j++)
                    if (Tablero.getTodasCasillas().get(i).get(j).getDuenho() == Tablero.banca) {
                        String[] partes = Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
        }
        consola.imprimir("Las casillas en venta son:");
        consola.imprimir(enVenta.toString());
        return enVenta;
    }

    // Método que realiza las acciones asociadas al comando 'listar hipotecadas'.
    @Override
    public ArrayList<String> listarHipoteca() {
        ArrayList<String> hipotecadas = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            // Recorremos cada tipo de casilla
            for (int j = 0; j < Tablero.getTodasCasillas().get(i).size(); j++) {
                Casilla casilla = Tablero.getTodasCasillas().get(i).get(j);

                // Verificamos si la casilla está hipotecada
                if (casilla instanceof Propiedad) {
                    Propiedad propiedad = (Propiedad) casilla;
                    if (propiedad.estaHipotecada()) {
                        String[] partes = casilla.getNombre().split(" ");
                        hipotecadas.add(partes[0]);
                    }
                }
            }
        }

        // Mensaje informativo
        if (hipotecadas.isEmpty()) {
            consola.imprimir("No hay casillas hipotecadas.");
        } else {
            consola.imprimir("Las casillas hipotecadas son:");
            consola.imprimir(hipotecadas.toString());
        }

        return hipotecadas;
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    @Override
    public void listarJugadores() {
        for (Jugador jugador : jugadores) {
            consola.imprimir("{");
            consola.imprimir("    nombre: " + jugador.getNombre() + ",");
            consola.imprimir("    avatar: " + jugador.getAvatar().getId() + ",");
            consola.imprimir("    fortuna: " + jugador.getFortuna() + ",");

            // Listar propiedades y edificios
            ArrayList<String> propies = new ArrayList<>();
            for (Casilla propiedad : jugador.getPropiedades()) {
                String[] partes = propiedad.getNombre().split(" ");
                propies.add(partes[0]);
            }
            consola.imprimir("    propiedades: " + propies + ",");

            // Listar edificios
            consola.imprimir("    edificios: {");
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad instanceof Solar) {
                    Solar solar = (Solar) propiedad;
                    consola.imprimir("        " + propiedad.getNombre() + ": {");
                    consola.imprimir("            casas: " + solar.getCasas().getNumEdificios() + ",");
                    consola.imprimir("            hoteles: " + solar.getHoteles().getNumEdificios() + ",");
                    consola.imprimir("            piscinas: " + solar.getPiscinas().getNumEdificios() + ",");
                    consola.imprimir(
                            "            pistas de deporte: " + solar.getPistasDeporte().getNumEdificios());
                    consola.imprimir("        },");
                }
            }
            consola.imprimir("    }");
            consola.imprimir("}");
            consola.imprimir("");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    @Override
    public void listarAvatares() {
        for (Jugador jugador : jugadores) {
            Avatar avatar = jugador.getAvatar();
            consola.imprimir("{");
            consola.imprimir("    id: " + avatar.getId() + ",");
            consola.imprimir("    tipo: " + avatar.getTipo() + ",");
            if (avatar.getLugar().getNombre().contains("&")) {
                String[] partes = avatar.getLugar().getNombre().split(" ");
                consola.imprimir("    casilla: " + partes[0] + ",");
            } else {
                consola.imprimir("    casilla: " + avatar.getLugar().getNombre() + ",");
            }
            consola.imprimir("    jugador: " + jugador.getNombre());
            consola.imprimir("},");
            consola.imprimir("");
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    @Override
    public void acabarTurno() {
        lanzamientos = 0;
        turno++;
        setTurno(turno);
        if (turno == jugadores.size()) {
            turno = 0;
            setTurno(turno);
        }
        setTirado(false);
    }

    // Método que indica que jugador tiene el turno.
    @Override
    public void jugador() {
        consola.imprimir("{");
        consola.imprimir("    nombre: " + jugadores.get(turno).getNombre() + ",");
        consola.imprimir("    avatar: " + jugadores.get(turno).getAvatar().getId());
        consola.imprimir("}");
        consola.imprimir("");
    }

    // Método que muestra el tablero.
    @Override
    public void verTablero() {
        consola.imprimir(tablero.toString());
    }

    // Método que realiza las acciones asociadas al comando 'mover'.
    @Override
    public void mover(int casillas) {
        Jugador jugadorActual = jugadores.get(turno);
        jugadorActual.getAvatar().moverJugador(jugadorActual, casillas);
        consola.imprimir(tablero.toString());
        if (jugadorActual.isEnCarcel()) {
            acabarTurno();
        }
    }

    // Metodo que hipoteca una propiedad
    @Override
    public void hipotecar(String nombre) {
        // Buscar la casilla por nombre
        String nombre1 = nombre + " " + jugadores.get(turno).getAvatar().getId();
        if (!jugadores.get(turno).getAvatar().getLugar().getNombre().equals(nombre1)) {
            try{
                throw new CasillaInvalidaException("No estás en esta casilla, no puedes hipotecarla");
        } catch (CasillaInvalidaException e) {
                consola.imprimir(e.getMessage());
            }
        }
            else {
            Casilla casilla = tablero.encontrar_casilla(nombre);
            if (listarHipoteca().contains(nombre)) {
                try{
                    throw new CasillaInvalidaException("Esa casilla no se puede hipotecar");
                } catch (CasillaInvalidaException e) {
                    consola.imprimir(e.getMessage());
                }
            } else {
                if (casilla != null && casilla instanceof Propiedad) {
                    Propiedad propiedad = (Propiedad) casilla;
                    // Llamar al método hipotecarCasilla
                    Jugador jugadorAhora = jugadores.get(turno);
                    propiedad.hipotecarCasilla(jugadorAhora, Tablero.banca);
                } else {
                    try{
                        throw new CasillaInvalidaException("La casilla " + nombre + " no existe.");
                    } catch (CasillaInvalidaException e) {
                        consola.imprimir(e.getMessage());
                    }
                }
            }
        }
    }

    // Metodo que deshipoteca una propiedad
    @Override
    public void deshipotecar(String nombre) {
        // Buscar la casilla por nombre
        Casilla casilla = tablero.encontrar_casilla(nombre);
        Jugador jugadorAhora = jugadores.get(turno);

        // Verificar si la casilla existe y si pertenece al jugador actual
        if (casilla != null && casilla.getDuenho() == jugadorAhora) {

            // Verificar si la casilla es una propiedad
            if (!(casilla instanceof Propiedad)) {
                try{
                    throw new CasillaInvalidaException("La casilla " + nombre + " no es una propiedad");
                } catch (CasillaInvalidaException e) {
                    consola.imprimir(e.getMessage());
                }
            }

            Propiedad propiedad = (Propiedad) casilla;

            // Verificar si la casilla está hipotecada
            if (propiedad.estaHipotecada()) {
                // Calcular el costo de deshipoteca con un recargo del 10%
                float costoDeshipoteca = propiedad.calcularValorHipoteca() * 1.10f;

                // Verificar si el jugador tiene suficiente dinero para deshipotecar
                if (jugadorAhora.getFortuna() >= costoDeshipoteca) {
                    // Realizar el pago y deshipotecar
                    jugadorAhora.sumarFortuna(-costoDeshipoteca);
                    jugadorAhora.pagotasasimpuestos = jugadorAhora.pagotasasimpuestos + costoDeshipoteca;
                    propiedad.setHipotecada(false);
                    casilla.casillahacostado = casilla.casillahacostado + costoDeshipoteca;

                    // Informar al jugador
                    consola.imprimir(jugadorAhora.getNombre() + " paga " + costoDeshipoteca + " por deshipotecar " + nombre + ". Ahora puede recibir alquileres y edificar en el grupo " + casilla.getNombre() + ".");
                }

                    else {
                        try{
                            throw new FondosInsuficientesException(jugadorAhora.getNombre() + " no tiene suficiente dinero para deshipotecar " + nombre + ".");
                    } catch (FondosInsuficientesException e) {
                            consola.imprimir(e.getMessage());
                        }
                }
                } else {
                    try{
                        throw new ComandoInvalidoException(jugadorAhora.getNombre() + " no puede deshipotecar " + nombre + ". No está hipotecada.");
                } catch (ComandoInvalidoException e) {
                        consola.imprimir(e.getMessage());
                    }
            }
            } else {
            try{
                throw new ComandoInvalidoException(jugadorAhora.getNombre() + " no puede deshipotecar " + nombre + ". No es una propiedad que le pertenece.");
            } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
            }
    }

    /*
    // Método que realiza las acciones asociadas a las cartas de Suerte.
    public static void accionSuerte(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas) {

        ConsolaNormal consola = new ConsolaNormal();

        ArrayList<String> cartasSuerte = new ArrayList<>();
        cartasSuerte.add(
                "Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Decides hacer un viaje de placer hasta Solar15, sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("Vendes tu billete de avión y cobras 500000.");
        cartasSuerte.add("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Los acreedores te persiguen. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("¡Has ganado el bote de la lotería! Recibe 1000000.");

        barajarCartas(cartasSuerte);

        // Selección de carta por parte del jugador
        consola.imprimir("Elige un número de carta entre 1 y " + cartasSuerte.size() + ": ");
        int eleccion = consola.leerInt();

        if (eleccion < 1 || eleccion > cartasSuerte.size()) {
            consola.imprimir("Número de carta inválido. Debe ser entre 1 y " + cartasSuerte.size() + ".");
            return;
        }

        // Obtener la carta seleccionada
        String cartaSeleccionada = cartasSuerte.get(eleccion - 1);
        consola.imprimir("Acción: " + cartaSeleccionada);
        // Ejecutar la acción basada en el texto de la carta
        Jugador jugadorActual = jugadores.get(turno);

        switch (eleccion) {
            case 1:
                consola.imprimir(
                        "Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                int pppp = jugadorActual.getAvatar().getLugar().getPosicion();
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 5);
                consola.imprimir(tablero);

                if (pppp > 5) {
                    consola.imprimir("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                    jugador.sumarFortuna(1301328.584f);
                    jugador.pasarporcasillasalida = jugador.pasarporcasillasalida + 1301328.584f;
                }
                jugador.vecesvueltas++;
                break;

            case 2:
                consola.imprimir(
                        "Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 26);
                consola.imprimir(tablero);

                break;

            case 3:
                System.out
                        .println("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000.");
                jugador.sumarFortuna(500000);
                jugador.premiosinversionesbote = jugador.premiosinversionesbote + 500000;
                break;

            case 4:
                consola.imprimir("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                int ppp = jugadorActual.getAvatar().getLugar().getPosicion();
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 6);
                if (ppp > 6) {
                    consola.imprimir("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                    jugador.sumarFortuna(1301328.584f);
                    jugador.pasarporcasillasalida = jugador.pasarporcasillasalida + 1301328.584f;
                }
                jugador.vecesvueltas++;
                consola.imprimir(tablero);
                break;

            case 5:
                consola.imprimir(
                        "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);
                jugadorActual.vecesencarcel++;
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                consola.imprimir(tablero);
                break;

            case 6:
                consola.imprimir("¡Has ganado el bote de la lotería! Recibe 1000000.");
                jugador.sumarFortuna(1000000);
                jugador.premiosinversionesbote = jugador.premiosinversionesbote + 1000000;
                break;

            default:
                consola.imprimir("No se reconoce la carta seleccionada.");
                break;
        }

    }

    // Método que realiza las acciones asociadas a las cartas de Comunidad.
    public static void accionComunidad(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas) {

        ConsolaNormal consola = new ConsolaNormal();

        ArrayList<String> cartasComunidad = new ArrayList<>();
        cartasComunidad.add("Paga 500000 por un fin de semana en un balneario de 5 estrellas.");
        cartasComunidad.add("Te investigan por fraude de identidad. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasComunidad.add("Colócate en la casilla de Salida y cobra la cantidad habitual.");
        cartasComunidad.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000.");
        cartasComunidad.add("Paga 1000000 por invitar a todos tus amigos a un viaje a Solar14.");
        cartasComunidad.add("Alquilas una villa en Solar7 y pagas 200000 a cada jugador.");

        // Barajar manualmente las cartas
        barajarCartas(cartasComunidad);

        // Selección de carta por parte del jugador
        consola.imprimir("Elige un número de carta entre 1 y " + cartasComunidad.size() + ": ");
        int eleccion = consola.leerInt();

        if (eleccion < 1 || eleccion > cartasComunidad.size()) {
            consola.imprimir("Número de carta inválido. Debe ser entre 1 y " + cartasComunidad.size() + ".");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);

        // Ejecutar la acción basada en el texto de la carta
        switch (eleccion) {
            case 1:
                consola.imprimir("Paga 500000 por un fin de semana en un balneario de 5 estrellas.");
                jugador.sumarFortuna(-500000);
                jugador.pagotasasimpuestos = jugador.pagotasasimpuestos + 500000;

                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }

                break;
            case 2:
                consola.imprimir(
                        "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);
                jugadorActual.vecesencarcel++;
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                consola.imprimir(tablero);
                break;
            case 3:
                consola.imprimir("Colócate en la casilla de Salida y cobra la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 0);
                consola.imprimir("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugador.sumarFortuna(1301328.584f);
                jugador.pasarporcasillasalida = jugador.pasarporcasillasalida + 1301328.584f;
                jugador.vecesvueltas++;
                // dddjJJDD
                break;
            case 4:
                consola.imprimir("Tu compañía de Internet obtiene beneficios. Recibe 2000000.");
                jugador.sumarFortuna(2000000);
                jugador.premiosinversionesbote = jugador.premiosinversionesbote + 2000000;
                break;
            case 5:
                consola.imprimir("Paga 1000000 por invitar a todos tus amigos a un viaje a Solar14.");
                jugador.sumarFortuna(-1000000);
                jugador.pagotasasimpuestos = jugador.pagotasasimpuestos + 1000000;
                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }
                break;
            case 6:
                consola.imprimir("Alquilas una villa en Solar7 y pagas 200000 a cada jugador.");

                int cantidad = 200000;
                for (int i = 0; i < jugadores.size(); i++) { // Itera sobre todos los jugadores
                    if (i != turno) { // Omite el jugador en turno
                        jugadores.get(i).sumarFortuna(cantidad);
                        jugadores.get(i).premiosinversionesbote = jugadores.get(i).premiosinversionesbote + cantidad; // Realiza
                                                                                                                      // el
                                                                                                                      // pago
                    }
                }

                jugador.sumarFortuna(-cantidad * (jugadores.size() - 1));
                jugador.pagotasasimpuestos = jugador.pagotasasimpuestos + (cantidad * (jugadores.size() - 1)); // Resta
                                                                                                               // el
                                                                                                               // total
                                                                                                               // pagado

                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }

                break;
            default:
                consola.imprimir("No se reconoce la carta seleccionada.");
                break;
        }

    }

    // Método para barajar manualmente las cartas sin usar shuffle
    private static ArrayList<String> barajarCartas(ArrayList<String> cartas) {
        ArrayList<String> cartasBarajadas = new ArrayList<>();
        Random random = new Random();
        ArrayList<Integer> indicesUsados = new ArrayList<>();

        while (cartasBarajadas.size() < cartas.size()) {
            int indiceAleatorio = random.nextInt(cartas.size());
            if (!indicesUsados.contains(indiceAleatorio)) {
                cartasBarajadas.add(cartas.get(indiceAleatorio));
                indicesUsados.add(indiceAleatorio);
            }
        }

        return cartasBarajadas;
    } */

    // Metodo que lista los edificios construidos por los jugadores.
    @Override
    public void listarEdificios() {
        ArrayList<Edificio> edificios = new ArrayList<>();
        HashSet<String> idsUnicos = new HashSet<>();

        for (Jugador jugador : jugadores) {
            for (Casilla propiedad : jugador.getPropiedades()) {
                Solar solar = (Solar) propiedad;
                for (Edificio edificio : solar.getEdificios()) {
                    if (edificio != null && idsUnicos.add(edificio.getIds().toString())) {
                        edificios.add(edificio);
                    }
                }
            }
        }

        for (Edificio edificio : edificios) {
            for (String id : edificio.getIds()) {
                consola.imprimir("{");
                consola.imprimir("    id: " + id + ",");
                consola.imprimir("    propietario: " + edificio.getSolar().getDuenho().getNombre() + ",");
                consola.imprimir("    casilla: " + edificio.getSolar().getNombre() + ",");
                consola.imprimir("    grupo: " + edificio.getSolar().getGrupo().getColorGrupo() + ",");
                consola.imprimir("    coste: " + edificio.getCosteEdificio());
                consola.imprimir("},");
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar edificiosgrupo'.
    @Override
    public void listarEdificiosGrupo(String colorGrupo) {
        Grupo grupo = tablero.getGrupos().get(colorGrupo);
        if (grupo == null) {
            try{
                throw new ComandoInvalidoException("El grupo especificado no existe.");

        } catch (ComandoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }


        ArrayList<Casilla> propiedades = grupo.getMiembros();
        int maxCasas = 0, maxHoteles = 0, maxPiscinas = 0, maxPistasDeporte = 0;
        int casasConstruidas = 0, hotelesConstruidos = 0, piscinasConstruidas = 0, pistasDeporteConstruidas = 0;

        for (Casilla propiedad : propiedades) {
            Solar solar = (Solar) propiedad;
            Edificio casas = solar.getCasas();
            Edificio hoteles = solar.getHoteles();
            Edificio piscinas = solar.getPiscinas();
            Edificio pistasDeporte = solar.getPistasDeporte();

            maxCasas += casas.getMaxEdificios();
            maxHoteles += hoteles.getMaxEdificios();
            maxPiscinas += piscinas.getMaxEdificios();
            maxPistasDeporte += pistasDeporte.getMaxEdificios();

            casasConstruidas += casas.getNumEdificios();
            hotelesConstruidos += hoteles.getNumEdificios();
            piscinasConstruidas += piscinas.getNumEdificios();
            pistasDeporteConstruidas += pistasDeporte.getNumEdificios();

            consola.imprimir("{");
            consola.imprimir("    propiedad: " + propiedad.getNombre() + ",");
            consola.imprimir("    hoteles: " + (hoteles.getIds().isEmpty() ? "-" : hoteles.getIds()) + ",");
            consola.imprimir("    casas: " + (casas.getIds().isEmpty() ? "-" : casas.getIds()) + ",");
            consola.imprimir("    piscinas: " + (piscinas.getIds().isEmpty() ? "-" : piscinas.getIds()) + ",");
            consola.imprimir(
                    "    pistasDeDeporte: " + (pistasDeporte.getIds().isEmpty() ? "-" : pistasDeporte.getIds()) + ",");
            consola.imprimir("    alquiler: " + propiedad.getAlquiler());
            consola.imprimir("},");
        }

        consola.imprimir("Todavía se pueden construir:");
        consola.imprimir("Casas: " + (maxCasas - casasConstruidas));
        consola.imprimir("Hoteles: " + (maxHoteles - hotelesConstruidos));
        consola.imprimir("Piscinas: " + (maxPiscinas - piscinasConstruidas));
        consola.imprimir("Pistas de deporte: " + (maxPistasDeporte - pistasDeporteConstruidas));
    }

    // Método para vender edificios de una casilla
    @Override
    public void venderEdificios(String tipoEdificio, String nombreCasilla, int cantidad) {
        // Buscar la casilla por nombre
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        Jugador jugadorAhora = jugadores.get(turno);

        // Verificar si la casilla existe y si pertenece al jugador actual
        if (casilla != null && casilla.getDuenho() == jugadorAhora) {
            Edificio edificio = null;
            Solar solar = (Solar) casilla;
            switch (tipoEdificio.toLowerCase()) {
                case "casa":
                case "casas":
                    edificio = solar.getCasas();
                    break;
                case "hotel":
                case "hoteles":
                    edificio = solar.getHoteles();
                    break;
                case "piscinas":
                case "piscina":
                    edificio = solar.getPiscinas();
                    break;
                case "pistasdeporte":
                case "pistadeporte":
                case "pistas":
                case "pista":
                case "pistadeportes":
                    edificio = solar.getPistasDeporte();
                    break;
                default:
                    try{
                        throw new EdificioInvalidoException("Tipo de edificio no válido.");

            } catch (EdificioInvalidoException e) {
                        throw new RuntimeException(e);
                    }
            }

            if (edificio != null) {
                int numEdificios = edificio.getNumEdificios();
                if (numEdificios >= cantidad) {
                    // Calcular el precio de venta
                    float precioVenta = edificio.getCosteEdificio() * 0.5f * cantidad;

                    // Actualizar la cantidad de edificios y la fortuna del jugador
                    edificio.setNumEdificios(numEdificios - cantidad);
                    jugadorAhora.sumarFortuna(precioVenta);
                    jugadorAhora.premiosinversionesbote = jugadorAhora.premiosinversionesbote + precioVenta;
                    edificio.getSolar().casillahagenerado = edificio.getSolar().casillahagenerado + precioVenta;
                    jugadorAhora.valorpropiedades = jugadorAhora.valorpropiedades - precioVenta;
                    // Eliminar los IDs de los edificios vendidos
                    for (int i = 0; i < cantidad; i++) {
                        edificio.getIds().remove(edificio.getIds().size() - 1);
                    }

                    // Informar al jugador
                    consola.imprimir(jugadorAhora.getNombre() + " ha vendido " + cantidad + " " + tipoEdificio
                            + " en " + nombreCasilla + ", recibiendo " + precioVenta + ". En la propiedad queda "
                            + (numEdificios - cantidad) + " " + tipoEdificio + ".");
                } else {
                    consola.imprimir("Solamente se puede vender " + numEdificios + " " + tipoEdificio
                            + ", recibiendo " + (edificio.getCosteEdificio() * 0.5f * numEdificios) + ".");
                    edificio.setNumEdificios(0);
                    jugadorAhora.sumarFortuna(edificio.getCosteEdificio() * 0.5f * numEdificios);
                    jugadorAhora.premiosinversionesbote = jugadorAhora.premiosinversionesbote
                            + (edificio.getCosteEdificio() * 0.5f * numEdificios);
                    edificio.getSolar().casillahagenerado = edificio.getSolar().casillahagenerado
                            + (edificio.getCosteEdificio() * 0.5f * numEdificios);
                    jugadorAhora.valorpropiedades = jugadorAhora.valorpropiedades
                            - (edificio.getCosteEdificio() * 0.5f * numEdificios);
                }
            } else  {
                try{
                    throw new EdificioInvalidoException("No se pueden vender " + tipoEdificio + " en " + nombreCasilla + ".");
            } catch (EdificioInvalidoException e) {
                    consola.imprimir(e.getMessage());
                }
            }
        } else {
            try{
                throw new EdificioInvalidoException("No se pueden vender " + tipoEdificio + " en " + nombreCasilla
                    + ". Esta propiedad no pertenece a " + jugadorAhora.getNombre() + ".");
        } catch (EdificioInvalidoException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Método para listar los tratos activos de un jugador, no podrá ver los de los demás
    @Override
    public void listarTratos(Jugador jugador) {
        consola.imprimir("Tratos de " + jugador.getNombre() + ":");
        for (Tratos trato : jugador.getListaTratos()) {
            if (trato.getJugador1() != jugador) {
                consola.imprimir("{");
                consola.imprimir("    id: " + trato.getIdTrato() + ",");
                consola.imprimir("    jugador: " + trato.getJugador1().getNombre() + ",");
                switch (trato.getTipoTrato()) {
                    case 1:
                        consola.imprimir("    cambiar: (" + trato.getPropiedad1().getNombre() + ", " + trato.getPropiedad2().getNombre() + ")");
                        break;
                    case 2:
                        consola.imprimir("    cambiar: (" + trato.getPropiedad1().getNombre() + ", " + trato.getCantidadDinero2() + ")");
                        break;
                    case 3:
                        consola.imprimir("    cambiar: (" + trato.getCantidadDinero1() + ", " + trato.getPropiedad2().getNombre() + ")");
                        break;
                    case 4:
                        consola.imprimir("    cambiar: (" + trato.getPropiedad1() + ", " + trato.getPropiedad2().getNombre() + " y " + trato.getCantidadDinero2() + ")");
                        break;
                        case 5:
                        consola.imprimir("    cambiar: (" + trato.getPropiedad1().getNombre() + " y " + trato.getCantidadDinero1() + ", " + trato.getPropiedad2().getNombre() + ")");
                    default:
                        try {
                            throw new ComandoInvalidoException("Tipo de trato no válido.");

                        } catch (ComandoInvalidoException e) {
                            consola.imprimir(e.getMessage());
                        }
                }
                consola.imprimir("},");
            }
        }
    }

    // Método que muestra la lista de comandos disponibles.
    @Override
    public void mostrarAyuda() {
        consola.imprimir("Comandos disponibles:");
        consola.imprimir("{");
        consola.imprimir("    jugador");
        consola.imprimir("    describir [jugador/avatar/casilla] [nombre/ID]");
        consola.imprimir("    lanzar dados");
        consola.imprimir("    comprar [nombre_casilla]");
        consola.imprimir("    salir carcel");
        consola.imprimir("    listar [jugadores/avatares/enventa/hipotecados/edificios/edificios grupo [color_grupo]]/tratos");
        consola.imprimir("    acabar turno");
        consola.imprimir("    exit");
        consola.imprimir("    ver tablero");
        consola.imprimir("    mover [numero_casillas]");
        consola.imprimir("    hipotecar [nombre_casilla]");
        consola.imprimir("    deshipotecar [nombre_casilla]");
        consola.imprimir("    estadisticas [jugador/nada]");
        consola.imprimir("    edificar");
        consola.imprimir("    vender [casas/hoteles/piscinas/pistasdeporte] [nombre_casilla] [cantidad]");
        consola.imprimir("    bancarrota");
        consola.imprimir("    cambiar movimiento");
        consola.imprimir("    trato [jugador]: cambiar [detalles]");
        consola.imprimir("    aceptar [ID_trato]");
        consola.imprimir("    eliminar [ID_trato]");
        consola.imprimir("}");
        consola.imprimir("");
    }

    // Método que verifica si todos los jugadores han completado al menos 4 vueltas.
    public static boolean todosHanCompletadoCuatroVueltas() {
        for (Jugador jugador : jugadores) {
            // Verificamos si las vueltas del jugador NO son divisibles entre 4
            if (jugador.getVueltas() % 4 != 0 || jugador.getVueltas() == 0) {
                return false; // Si algún jugador no ha dado vueltas divisibles entre 4, retornamos false
            }
        }
        return true; // Si todos los jugadores han dado un número de vueltas divisible entre 4,
                     // retornamos true
    }

    /* Método para obtener la estadística de jugador con más lanzamientos de dado */
    public String maxvecesdado() {
        ArrayList<Integer> ppp = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            ppp.add(jugadores.get(i).vecesdados);
        }
        int z = 0;
        for (int i = 0; i <= ppp.size() - 2; i++) {
            if (ppp.get(z) >= ppp.get(i + 1)) {
            } else {
                z = i + 1;
            }
        }
        return jugadores.get(z).getNombre();
    }

    /* Método para obtener la estadística de jugador con más vueltas */
    public String maxvueltas() {
        ArrayList<Integer> ppp = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            ppp.add(jugadores.get(i).vecesvueltas);
        }
        int z = 0;
        for (int i = 0; i <= ppp.size() - 2; i++) {
            if (ppp.get(z) >= ppp.get(i + 1)) {
            } else {
                z = i + 1;
            }
        }
        return jugadores.get(z).getNombre();
    }

    /* Método para obtener la estadística de casilla más frecuentada */
    public String maxcasilla() {
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
        int z = 0;
        for (int i = 0; i <= ppp.size() - 2; i++) {
            if (ppp.get(z).vecescasilla >= ppp.get(i + 1).vecescasilla) {
            } else {
                z = i + 1;
            }
        }
        return ppp.get(z).getNombre();
    }

    public String casillarentable() {
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
        int z = 0;
        for (int i = 0; i <= ppp.size() - 2; i++) {
            if ((ppp.get(z).casillahagenerado - ppp.get(z).casillahacostado) >= (ppp.get(i + 1).casillahagenerado
                    - ppp.get(i + 1).casillahacostado)) {
            } else {
                z = i + 1;
            }
        }
        return ppp.get(z).getNombre();
    }

    public String gruporentable() {
        HashMap<String, Grupo> grupos = new HashMap<>();
        grupos = tablero.getGrupos();
        ArrayList<Float> rentabilidadgrupos = new ArrayList<>();
        for (Map.Entry<String, Grupo> entry : grupos.entrySet()) {
            rentabilidadgrupos.add(entry.getValue().rentabilidadgrupo());
        }

        int z = 0;
        for (int i = 0; i <= rentabilidadgrupos.size() - 2; i++) {
            if (rentabilidadgrupos.get(z) >= rentabilidadgrupos.get(i + 1)) {
            } else {
                z = i + 1;
            }
        }
        if (z == 0) {
            return "negro";
        } else if (z == 1) {
            return "cian";
        } else if (z == 2) {
            return "magenta";
        } else if (z == 3) {
            return "amarillo";
        } else if (z == 4) {
            return "rojo";
        } else if (z == 5) {
            return "marron";
        } else if (z == 6) {
            return "verde";
        } else {
            return "azul";
        }

    }

    public String jugadorcabeza() {
        ArrayList<Float> patrimonio = new ArrayList<>();
        for (int i = 0; i <= jugadores.size() - 1; i++) {
            patrimonio.add(jugadores.get(i).getFortuna() + jugadores.get(i).getValorpropiedades());
        }
        int z = 0;
        for (int i = 0; i <= patrimonio.size() - 2; i++) {
            if (patrimonio.get(z) >= patrimonio.get(i + 1)) {
            } else {
                z = i + 1;
            }
        }
        return jugadores.get(z).getNombre();
    }
}