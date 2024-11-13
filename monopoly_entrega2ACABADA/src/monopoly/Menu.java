package monopoly;

import partida.*;

import java.util.*;


import static monopoly.Valor.SUMA_VUELTA;

public class Menu {

    // Atributos
    private static ArrayList<Jugador> jugadores; //Jugadores de la partida.

    private static ArrayList<Avatar> avatares; //Avatares en la partida.
    private ArrayList<String> tipoavatar;//Lista con los tipos de avatares (sirve para generar la ID)
    public static int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    public static Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    public Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.


    private int jugadormasvueltas;//Hechiwi
    private int jugadormasvecesdado; //Hecho
    private int jugadorencabeza;//Hecho
    private int casillamasrentable;//Hecho
    private int casillamasfrecuentada;//Hecho
    private int grupomasrentable;//Hecho

    public boolean getTirado() {
        return tirado;
    }
    private int getTurno(){return turno;}
    private void setTurno(int turno){Menu.turno= turno;}
    private void setTirado(boolean Tirado) {
        this.tirado = Tirado;
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {

        //bienvenida
        System.out.println("Bienvenido! El tablero es el siguiente");
        Scanner scanner=new Scanner(System.in);
        //Paso 1: Crear Tablero
        tablero = new Tablero();
        tablero.generarCasillas();  // Generar las 40 casillas del tablero
        Casilla inicio=Tablero.getTodasCasillas().get(0).get(0);//Casilla de salida
         banca = new Jugador();

        // Paso 2: Imprimir el tablero
        System.out.println(tablero);  // Esto imprimirá el tablero en el formato del método toString()

        // Paso 3: Pedir la cantidad de jugadores
        int numJugadores = 0;
        while (numJugadores < 2 || numJugadores > 6) {
            System.out.println("¿Cuántos jugadores participarán? (Entre 2 y 6 jugadores)");
            numJugadores = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            if (numJugadores < 2) {
                System.out.println("El número mínimo de jugadores es 2.");
            } else if (numJugadores > 6) {
                System.out.println("Solo se permiten hasta 6 jugadores.");
            }
        }

        jugadores = new ArrayList<>();  // Inicializamos la lista de jugadores
        avatares = new ArrayList<>();
        tipoavatar = new ArrayList<>();


        // Paso 4: Registrar jugadores esperando comandos como 'crear jugador Pedro coche'
        while (jugadores.size() < numJugadores) {
            System.out.println("Introduce el comando para crear un jugador: (ej. crear jugador Pedro coche. Los avatares son: Coche, esfinge, sombrero y pelota)");
            String comando = scanner.nextLine();

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
                        Avatar avatar = new Avatar(tipoAvatar, jugador, inicio, avatares);
                        avatares.add(avatar);

                        System.out.println("Jugador creado: " + nombreJugador + " con avatar " + tipoAvatar);
                    } else {
                        System.out.println("Tipo de avatar no válido. Usa 'coche', 'esfinge', 'sombrero' o 'pelota'.");
                    }
                } else {
                    System.out.println("Comando incorrecto. Usa: crear jugador <nombre> <avatar>");
                }
            } else {
                System.out.println("Comando no reconocido. Usa: crear jugador <nombre> <avatar>");
            }

        }

        System.out.println("¡Todos los jugadores han sido creados! Iniciando el juego...");
        for(int i=0;i<=jugadores.size()-1;i++) {
            System.out.println("El ID de " + jugadores.get(i).getNombre() + " es " + jugadores.get(i).getAvatar().getId());
        }
        //Paso 5: Inicializar salida de los avatares en tablero
        for (int i=0;i<avatares.size();i++) {
            inicio.anhadirAvatar(jugadores.get(i).getAvatar());
            avatares.get(i).setLugar(inicio);
            inicio.setNombreAnhadirID(jugadores.get(i).getAvatar());
        }

        System.out.println(tablero);

        // Ahora el juego sigue en bucle a la espera de comandos de los jugadores

        //Bucle de turnos de cada jugador
        while (true) {
            //Código para el movimiento avanzado del coche

            int turno1=turno-1;if(turno1<0){turno1=jugadores.size()+turno1;}
            jugadores.get(turno1).setCasollegaralcuartoturnosextracocheavanzado(false);
            if(jugadores.get(turno1).isEnCarcel()){jugadores.get(turno1).setCocheExtra(false);jugadores.get(turno1).turnoscocheavanzado=0;}int turnoscocheavanzado1=jugadores.get(turno1).turnoscocheavanzado+1;
            if(jugadores.get(turno1).getCocheExtra()){System.out.println(jugadores.get(turno1).getNombre()+" lleva "+turnoscocheavanzado1 +" turnos extra de los 3 posibles");jugadores.get(turno1).turnoscocheavanzado++;turno=turno-1;if(turno<0){turno=jugadores.size()+turno;}
                if(jugadores.get(turno).turnoscocheavanzado==3){System.out.println("Has llegado a 3 turnos del modo avanzado, este es el último");jugadores.get(turno).turnoscocheavanzado=0;jugadores.get(turno).setCocheExtra(false);jugadores.get(turno).setCasollegaralcuartoturnosextracocheavanzado(true);}}
            if(jugadores.get(turno).getCocheProhibido() && jugadores.get(turno).turnossinjugarcocheavanzado<2){System.out.println("Nos saltamos el turno de "+ jugadores.get(turno).getNombre()+" debido a que sacó menor a 4");
                jugadores.get(turno).turnossinjugarcocheavanzado++;turno++;}
            else if(jugadores.get(turno).getCocheProhibido() && jugadores.get(turno).turnossinjugarcocheavanzado==2){System.out.println("Ya han pasado los 2 turnos sin jugar, puedes jugar");jugadores.get(turno).turnossinjugarcocheavanzado=0;jugadores.get(turno).setCocheProhibido(false);}

            //El turno de un jugador sigue en bucle hasta que ponga acabar turno o exit
            String nuevoComando;
            while(true){
            System.out.println("Turno de "+avatares.get(turno).getJugador().getNombre());
            if(jugadores.get(turno).isEnCarcel()){setTirado(true); salirCarcel();}
            else {


                System.out.println("Introduce un comando para continuar (escribe ayuda para ver los comandos) o escribe 'exit' para terminar:");

                nuevoComando = scanner.nextLine();  // Cambié el nombre de la variable

                // Si el jugador quiere salir del juego
                if (nuevoComando.equalsIgnoreCase("exit")) {
                    System.out.println("Saliendo del juego...");
                    break;  // Salir del bucle y finalizar la partida
                }

                // Aquí puedes analizar el comando y ejecutar las acciones correspondientes

                analizarComando(nuevoComando);
                if (nuevoComando.equalsIgnoreCase("acabar turno")){break;}
                if(jugadores.get(turno1).isEnCarcel()){break;}
            }

            }if (nuevoComando.equalsIgnoreCase("exit")) {break;}
        }
        // Cerrar el scanner
scanner.close();
    }

    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        int i=0;//Uso de bucle do para repetir el turno en caso de que un comando se introduzca mal
        do {if (i==2){
            System.out.println("Vuelve a introducir el comando");
            Scanner scanner = new Scanner(System.in);
            comando = scanner.nextLine();
            // Cerrar el scanner

        }
            // Dividir el comando en partes para analizar la acción y sus parámetros
            String[] partes = comando.split(" ");

            // Obtener la acción principal (primer elemento del comando)
            String accion = partes[0];

            switch (accion.toLowerCase()) {
                case "describir":
                    if (partes.length < 3) {
                        System.out.println("Comando incompleto. Uso: describir [jugador/avatar/casilla] [nombre/ID]");i=2;//Comando erróneo, se repite el proceso
                        return;
                    }
                    // Subcomando que puede ser jugador, avatar o casilla
                    String tipoDescripcion = partes[1];
                    String argumento = partes[2];

                    switch (tipoDescripcion.toLowerCase()) {
                        case "jugador":
                            descJugador(partes);i=1;//Comando introducido bien: termina el do
                            break;
                        case "avatar":
                            descAvatar(argumento);i=1;
                            break;
                        case "casilla":
                            descCasilla(argumento);i=1;
                            break;
                        default:
                            System.out.println("Tipo de descripción no válido. Uso: describir [jugador/avatar/casilla]");i=2;
                    }
                    break;

                case "lanzar":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("dados")) {
                        lanzarDados();i=1;
                    }
                    else if (partes.length > 1 && partes[1].equalsIgnoreCase("trucados")) {
                        lanzarTrucados(); i=1;
                    }
                    else {
                        System.out.println("Comando no válido. Uso: lanzar dados");i=2;
                    }
                    break;

                case "comprar":
                    if (partes.length < 2) {
                        System.out.println("Comando incompleto. Uso: comprar [nombre_casilla]");i=2;
                        return;
                    }
                    String nombreCasilla = partes[1];
                    comprar(nombreCasilla);i=1;
                    break;

                case "salir":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("carcel")) {
                        salirCarcel();i=1;
                    } else {
                        System.out.println("Comando no válido. Uso: salir carcel");i=2;
                    }
                    break;

                case "listar":
                    if (partes.length < 2) {
                        System.out.println("Comando incompleto. Uso: listar [jugadores/avatares/enventa]");i=2;
                        return;
                    }
                    String tipoLista = partes[1];

                    switch (tipoLista.toLowerCase()) {
                        case "jugadores":
                            listarJugadores();i=1;
                            break;
                        case "avatares":
                            listarAvatares();i=1;
                            break;
                        case "enventa":
                            listarVenta();i=1;
                            break;
                        case "hipotecados":
                            listarHipoteca();i=1;
                            break;
                        case "edificios":
                            listarEdificios();i=1;
                            break;
                        case "edificios grupo":
                            if (partes.length < 3) {
                                System.out.println("Comando incompleto. Uso: listar edificios grupo [color_grupo]");i=2;
                                return;
                            }
                            String colorGrupo = partes[2];
                            listarEdificiosGrupo(colorGrupo);i=1;
                            break;
                        default:
                            System.out.println("Opción de lista no válida. Uso: listar [jugadores/avatares/enventa/hipotecados/edificios]");i=2;
                    }
                    break;


                case "estadisticas":
                    if (partes.length < 2) {
                        mostrarEstadisticasPartida();
                        }
                        else if (partes[1].equalsIgnoreCase(jugadores.get(turno).getNombre())) {
                            jugadores.get(turno).mostrarEstadisticasJugador();
                        }else{System.out.println("Nombre no reconocido");}
                        break;


                case "acabar":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("turno")) {
                        acabarTurno();i=1;
                    } else {
                        System.out.println("Comando no válido. Uso: acabar turno");i=2;
                    }
                    break;

                case "ayuda":
                    mostrarAyuda(); // Llamada al método para mostrar los comandos disponibles
                    i = 1;
                    break;
                case "siguiente":break;
                case "jugador":
                    jugador();
                    i=1;
                    break;

                case "ver":
                    if (partes.length > 1 && partes[1].equalsIgnoreCase("tablero")) {
                        verTablero();
                        i=1;
                    } else {
                        System.out.println("Comando no válido. Uso: ver tablero");i=2;
                    }
                    break;

                case "mover":
                    if (partes.length < 2) {
                        System.out.println("Comando incompleto. Uso: mover [numero_casillas]");i=2;
                        return;
                    }
                    int casillas = Integer.parseInt(partes[1]);
                    mover(casillas);i=1;
                    break;

                case "exit":
                    System.out.println("Saliendo del juego...");
                    System.exit(0); // Finaliza el programa
                    break;
                case "hipotecar":
                    if (partes.length < 2) {
                        System.out.println("Comando incompleto. Uso: hipotecar [nombre_casilla]");i=2;
                        return;
                    }
                    String nombreCasillaHipoteca = partes[1];
                    hipotecar(nombreCasillaHipoteca);i=1;
                    break;

                case "deshipotecar":
                    if (partes.length < 2) {
                        System.out.println("Comando incompleto. Uso: deshipotecar [nombre_casilla]");i=2;
                        return;
                    }
                    String nombreCasillaDeshipoteca = partes[1];
                    deshipotecar(nombreCasillaDeshipoteca);i=1;
                    break;

                case "construir":
                    construir();
                    i=1;
                    break;
                case "vender":
                    if (partes.length < 4) {
                        System.out.println("Comando incompleto. Uso: vender [tipo_edificio] [nombre_casilla] [cantidad]");
                        i = 2;
                        return;
                    }
                    String tipoEdificio = partes[1];
                    String nombreCasillaVenta = partes[2];
                    int cantidad;
                    try {
                        cantidad = Integer.parseInt(partes[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("Cantidad no válida. Debe ser un número entero.");
                        i = 2;
                        return;
                    }
                    venderEdificios(tipoEdificio, nombreCasillaVenta, cantidad);
                    i = 1;
                    break;

                case "bancarrota":
                    bancarrota(jugadores.get(turno), Tablero.banca);
                    i=1;
                    break;

                case "cambiar":
                    if (partes.length<2){
                        System.out.println("El uso correcto del comando es: cambiar movimiento");i=2;
                    }
                    else if (partes.length==2 && partes[1].equalsIgnoreCase("movimiento")){
                        if(getTirado()){System.out.println("No se puede cambiar el modo movim ya que ya se lanzaron los dados");i=1;}
                        else{jugadores.get(turno).setModomovimiento();
                        System.out.println("El avatar "+ jugadores.get(turno).getAvatar().getId()+" ha pasado a modo de movimiento "+ jugadores.get(turno).getModomovimiento());
                    i=1;}}
                    else{System.out.println("Error reconciendo el comando cambiar movimiento");i=2;}


                    break;
                default:
                    System.out.println("Comando no reconocido.");i=2;
            }
        }while(i==2);

    }


    //Método para ver las estadísticas de la partida
    private void mostrarEstadisticasPartida(){
        System.out.println("{\n casillaMasRentable: "+ casillarentable() + "\n" +
                "grupoMasRentable: " + gruporentable() + "\n" +
                "casillaMasFrecuentada: " + maxcasilla() + "\n" +
                "jugadorMasVueltas: " + maxvueltas() + "\n" +
                "jugadorMasVecesDados: " + maxvecesdado() + "\n" +
                "jugadorEnCabeza: " + jugadorcabeza()  +
                 "\n}");
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        if (partes.length < 3) {
            System.out.println("Comando incompleto. Uso: describir jugador [nombre]");
            return;
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
            System.out.println("Jugador no encontrado: " + nombreJugador);
            return;
        }

        // Imprimir detalles del jugador
        System.out.println("{");
        System.out.println("    nombre: " + jugador.getNombre() + ",");
        System.out.println("    avatar: " + jugador.getAvatar().getId() + ",");
        System.out.println("    fortuna: " + jugador.getFortuna() + ",");

        // Listar propiedades y edificios
        ArrayList<String> propies = new ArrayList<>();
        for (Casilla propiedad : jugador.getPropiedades()) {
            String[] partes1 = propiedad.getNombre().split(" ");
            propies.add(partes1[0]);
        }
        System.out.println("    propiedades: " + propies + ",");

        // Listar edificios
        System.out.println("    edificios: {");
        for (Casilla propiedad : jugador.getPropiedades()) {
            if (propiedad.getTipo().equalsIgnoreCase("Solar")) {
                System.out.println("        " + propiedad.getNombre() + ": {");
                System.out.println("            casas: " + propiedad.getCasas().getNumEdificios() + ",");
                System.out.println("            hoteles: " + propiedad.getHoteles().getNumEdificios() + ",");
                System.out.println("            piscinas: " + propiedad.getPiscinas().getNumEdificios() + ",");
                System.out.println("            pistas de deporte: " + propiedad.getPistasDeporte().getNumEdificios());
                System.out.println("        },");
            }
        }
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
        Avatar avatar = null;

        if (!ID.startsWith("&")){ID="&"+ID;}

        // Buscar el avatar por ID
        for (Jugador av : jugadores) {
            if (av.getAvatar().getId().equals(ID)) {
                avatar = av.getAvatar();
                break;
            }
        }

        if (avatar == null) {
            System.out.println("Avatar no encontrado: " + ID);
            return;
        }

        // Imprimir detalles del avatar
        System.out.println("{");
        System.out.println("    id: " + avatar.getId() + ",");
        System.out.println("    tipo: " + avatar.getTipo() + ",");
        if(avatar.getLugar().getNombre().contains("&")){
            String[] partes=avatar.getLugar().getNombre().split(" ");
            System.out.println("    casilla: " + partes[0] + ",");
        }else{System.out.println("    casilla: " +avatar.getLugar().getNombre());}
        System.out.println("    jugador: " + avatar.getJugador().getNombre());
        System.out.println("}");
        System.out.println();
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
        String[] partes = nombre.split(" ");
        nombre = partes[0];
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if (casilla == null) {
            System.out.println("Casilla no encontrada: " + nombre);
            return;
        }
        System.out.println("{");
        switch (casilla.getTipo().toLowerCase()) {
            case "solar":
                System.out.println("    tipo: " + casilla.getTipo() + ",");
                System.out.println("    grupo: " + casilla.getGrupo().getColorGrupo() + ",");
                System.out.println("    propietario: " + (casilla.getDuenho() != null ? casilla.getDuenho().getNombre() : "Ninguno") + ",");
                System.out.println("    valor: " + casilla.getValor() + ",");
                System.out.println("    alquiler base: " + casilla.getAlquiler() + ",");
                System.out.println("    edificios: {");
                System.out.println("        casas: " + casilla.getCasas().getNumEdificios() + ",");
                System.out.println("        hoteles: " + casilla.getHoteles().getNumEdificios() + ",");
                System.out.println("        piscinas: " + casilla.getPiscinas().getNumEdificios() + ",");
                System.out.println("        pistas de deporte: " + casilla.getPistasDeporte().getNumEdificios());
                System.out.println("    },");
                break;
            case "impuesto":
                System.out.println("    tipo: " + casilla.getTipo() + ",");
                System.out.println("    apagar: " + casilla.getValor());
                break;
            case "parking":
                System.out.println("    bote: " + Tablero.getBote() + ",");
                System.out.println("    jugadores: " + casilla.JugadoresenCasilla());
                break;
            case "carcel":
                System.out.println("    salir: " + SUMA_VUELTA / 4 + ",");
                System.out.println("    jugadores: " + casilla.getJugadoresEnCarcel());
                break;
            default:
                System.out.println("Tipo de casilla no válido para descripción.");
                break;
        }
        System.out.println("}");
        System.out.println();
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {

        Jugador jugadorActual = jugadores.get(turno);

        if (getTirado()) {
            System.out.println("Ya has lanzado los dados en este turno.");
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
        System.out.println("Has sacado un " + tirada1 + " y un " + tirada2);
        Dado.setSuma(tirada1 + tirada2);jugadorActual.vecesdados++;

        //Mover avatar

        moverJugador(jugadorActual, Dado.getSuma());
        System.out.println(tablero);

        if (jugadorActual.isEnCarcel()) {
            acabarTurno();
        } else {
            //Comprobar si ha sacado dobles
            if (tirada1 == tirada2 && !jugadorActual.getCocheExtra() && !jugadorActual.getCocheProhibido() && !jugadorActual.casosacarmenosdecuatroenunturnoextracocheavanzado) {
                System.out.println("¡Has sacado dobles! Tira otra vez.");
                setTirado(false);
                System.out.println(tablero);
                if (lanzamientos == 3) {
                    System.out.println("Has sacado dobles 3 veces seguidas. Vas a la cárcel.");
                    jugadorActual.setEnCarcel(true);jugadorActual.vecesencarcel++;
                    jugadorActual.setTiradasCarcel(0);acabarTurno();
                    Casilla carcel = tablero.encontrar_casilla("Carcel");
                    if (carcel != null) {
                        jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                        jugadorActual.getAvatar().setLugar(carcel);
                        jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                        System.out.println(tablero);
                    } else {
                        System.out.println("Error: No se encontró la casilla de la cárcel.");
                    }
                }
            }
            jugadorActual.setCasosacarmenosdecuatroenunturnoextracocheavanzado(false);

        }
    }

    //Método de dados trucados
    private void lanzarTrucados() {
        // Corrección de error tipográfico
        if (getTirado()) {
            System.out.println("Ya has lanzado los dados en este turno.");
            return;
        }
        Scanner scanner= new Scanner(System.in);
        System.out.println("Introduce los valores de tus dados trucados:");

        // Usar solo una instancia de Scanner


        // Leer los dos números de entrada
        System.out.print("Introduce el valor del primer dado: ");
        int tirada1 = scanner.nextInt();

        System.out.print("Introduce el valor del segundo dado: ");
        int tirada2 = scanner.nextInt();

        // Cerrar el scanner


        Jugador jugadorActual = jugadores.get(turno);


        Dado.setSuma(tirada1+tirada2);jugadorActual.vecesdados++;
        setTirado(true);lanzamientos++;
        //Mover avatar

        moverJugador(jugadorActual, Dado.getSuma());
        System.out.println(tablero);
        if (jugadorActual.isEnCarcel()) {
            acabarTurno();
        } else {
            //Comprobar si ha sacado dobles
            if (tirada1 == tirada2 && !jugadorActual.getCocheExtra() && !jugadorActual.getCocheProhibido() && !jugadorActual.casosacarmenosdecuatroenunturnoextracocheavanzado) {
                System.out.println("¡Has sacado dobles! Tira otra vez.");
                setTirado(false);
                System.out.println(tablero);
                if (lanzamientos == 3) {
                    System.out.println("Has sacado dobles 3 veces seguidas. Vas a la cárcel.");
                    jugadorActual.setEnCarcel(true);jugadorActual.vecesencarcel++;
                    jugadorActual.setTiradasCarcel(0);acabarTurno();
                    Casilla carcel = tablero.encontrar_casilla("Carcel");
                    if (carcel != null) {
                        jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                        jugadorActual.getAvatar().setLugar(carcel);
                        jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                        System.out.println(tablero);
                    } else {
                        System.out.println("Error: No se encontró la casilla de la cárcel.");
                    }
                }
            }jugadorActual.setCasosacarmenosdecuatroenunturnoextracocheavanzado(false);

        }
    }

    //Método que mueve al jugador por el tablero la cantidad de casillas indicada
    private void moverJugador(Jugador jugador, int valorTirada) {
        Avatar avatar = jugador.getAvatar();
        int posicionActual = avatar.getLugar().getPosicion();// Obtener la posición actual del avatar
        int nuevaPosicion=0;


        if (jugador.getModomovimiento().equalsIgnoreCase("basico")){
            nuevaPosicion = posicionActual + valorTirada;
            // Verificar si el jugador pasó por la casilla de salida (casilla 0)
            if (nuevaPosicion > 39 && posicionActual <= 39) {
                jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                jugador.vecesvueltas++;
                // Verificar si todos los jugadores han completado al menos 4 vueltas
                if (todosHanCompletadoCuatroVueltas()) {
                    tablero.incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                    System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                }
            }

            // Ajustar la nueva posición en el tablero si excede el número de casillas
            nuevaPosicion = nuevaPosicion % 40;

            // Actualizar la posición del avatar
            avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

            Casilla casillaActual = avatar.getLugar();

            // Evaluar la casilla en la que cayó
            casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);

        }
        else if (jugador.getModomovimiento().equalsIgnoreCase("avanzado")){
            if(jugador.getAvatar().getTipo().equalsIgnoreCase("pelota")){

                if (valorTirada>4){nuevaPosicion = posicionActual + valorTirada;
                    // Verificar si el jugador pasó por la casilla de salida (casilla 0)
                    if (nuevaPosicion > 39 && posicionActual <= 39) {
                        jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                        System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                        jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                        jugador.vecesvueltas++;
                        // Verificar si todos los jugadores han completado al menos 4 vueltas
                        if (todosHanCompletadoCuatroVueltas()) {
                            tablero.incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                            System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                        }
                    }

                int numcasillasintermedias=0;
                for (int i=5; i<=valorTirada; i++ ) {
                    if (i%2!=0 ){
                numcasillasintermedias++;}}
                if(valorTirada%2==0){numcasillasintermedias++;}
                    ArrayList<Integer> casillasintermedias=new ArrayList<>();

                if(valorTirada==5){
                casillasintermedias.add(5);}
                else if(valorTirada==6){
                    casillasintermedias.add(5);
                    casillasintermedias.add(1);
                 }
                else if (valorTirada==7){
                    casillasintermedias.add(5);
                    casillasintermedias.add(2);
                }
                else if (valorTirada>=8){
                    casillasintermedias.add(5);
                    for (int j=1;j<=numcasillasintermedias-2;j++){
                        casillasintermedias.add(2);
                    }if (valorTirada%2==0){casillasintermedias.add(1);}
                }
                else{System.out.println("Error raro en mov avanzado de pelota");}

                    // Ajustar la nueva posición en el tablero si excede el número de casillas
                    nuevaPosicion = nuevaPosicion % 40;


                for (int j:casillasintermedias) {
                    // Actualizar la posición del avatar
                    posicionActual=avatar.getLugar().getPosicion();
                    avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionActual+j) % 40);

                    Casilla casillaActual = avatar.getLugar();

                    // Evaluar la casilla en la que cayó
                    casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
                    String[] partes=casillaActual.getNombre().split(" ");
                    if(partes[0].equalsIgnoreCase("IrCárcel") || jugador.isEnCarcel()){
                        break;
                    }

                }

                }
                if (valorTirada<=4){nuevaPosicion = posicionActual - valorTirada;
                    // Verificar si el jugador pasó por la casilla de salida (casilla 0)
                    if (nuevaPosicion<0 && posicionActual>0) {
                        jugador.disminuirVueltas();  // Aumentar el contador de vueltas del jugador
                        System.out.println("¡Has retrocdido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
                        if(jugador.getFortuna()>=SUMA_VUELTA){
                            jugador.sumarFortuna(-SUMA_VUELTA); }
                        else{sinDinero(jugador,Tablero.banca);}// Añadir el valor de la vuelta a la fortuna del jugador
                        jugador.vecesvueltas=jugador.vecesvueltas-1;jugador.pasarporcasillasalida=jugador.pasarporcasillasalida-SUMA_VUELTA;
                        // Verificar si todos los jugadores han completado al menos 4 vueltas

                    }

                    if(nuevaPosicion<0){nuevaPosicion=40+nuevaPosicion;}


                    // Ajustar la nueva posición en el tablero si excede el número de casillas
                    nuevaPosicion = nuevaPosicion % 40;

                    int numcasillasintermedias=0;
                    for (int i=0; i<=valorTirada; i++ ) {
                        if (i%2!=0 ){
                            numcasillasintermedias++;}}
                    if(valorTirada%2==0){numcasillasintermedias++;}
                    ArrayList<Integer> casillasintermedias=new ArrayList<>();

                    if(valorTirada==2){
                        casillasintermedias.add(1);
                    casillasintermedias.add(1);}
                    else if(valorTirada==3){
                        casillasintermedias.add(1);
                        casillasintermedias.add(2);
                    }
                    else if (valorTirada==4){
                        casillasintermedias.add(1);
                        casillasintermedias.add(2);
                        casillasintermedias.add(1);
                    }

                    else{System.out.println("Error raro en mov avanzado de pelota");}




                    for (int j:casillasintermedias) {
                        // Actualizar la posición del avatar
                        posicionActual=avatar.getLugar().getPosicion();
                        int posicionAct=posicionActual-j;
                        if(posicionAct<0){posicionAct=posicionAct+40;}
                        avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionAct) % 40);

                        Casilla casillaActual = avatar.getLugar();

                        // Evaluar la casilla en la que cayó
                        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
                        String[] partes=casillaActual.getNombre().split(" ");
                        if(partes[0].equalsIgnoreCase("IrCárcel") || jugador.isEnCarcel()){
                            break;
                        }

                    }}


            }
            else if(jugador.getAvatar().getTipo().equalsIgnoreCase("coche")){


                if (valorTirada>4){movercocheavanzadomayor4(jugador,valorTirada, posicionActual);
                    }
                if (valorTirada<=4){movercocheavanzadomenor4(jugador,valorTirada, posicionActual);
                }
            }
            else {System.out.println("No hay movimiento avanzado permitido para este tipo de avatar aún");
                nuevaPosicion = posicionActual + valorTirada;
                // Verificar si el jugador pasó por la casilla de salida (casilla 0)
                if (nuevaPosicion > 39 && posicionActual <= 39) {
                    jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                    System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                    jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                    jugador.vecesvueltas++;
                    // Verificar si todos los jugadores han completado al menos 4 vueltas
                    if (todosHanCompletadoCuatroVueltas()) {
                        tablero.incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                        System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                    }
                }

                // Ajustar la nueva posición en el tablero si excede el número de casillas
                nuevaPosicion = nuevaPosicion % 40;

                // Actualizar la posición del avatar
                avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

                Casilla casillaActual = avatar.getLugar();

                // Evaluar la casilla en la que cayó
                casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);}
        }
        else {System.out.println("Hay problemas detectando si el movimiento es básico o avanzado, no se ha podido mover al jugador");}

    }

    // Método para cuando un jugador se queda sin dinero, puede hipotecar o declararse en bancarrota
    public static void sinDinero(Jugador jugador, Jugador acreedor) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(jugador.getNombre() + ", te has quedado sin dinero. Elige una opción:");
            System.out.println("1. Hipotecar una propiedad");
            System.out.println("2. Declararse en bancarrota");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            if (opcion == 1) {
                ArrayList<Casilla> propiedades = jugador.getPropiedades();
                if (propiedades.isEmpty()) {
                    System.out.println("No tienes propiedades para hipotecar.");
                    continue;
                }

                System.out.println("Elige una propiedad para hipotecar:");
                for (int i = 0; i < propiedades.size(); i++) {
                    Casilla propiedad = propiedades.get(i);
                    System.out.println((i + 1) + ". " + propiedad.getNombre());
                }

                int propiedadIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consumir la nueva línea

                if (propiedadIndex >= 0 && propiedadIndex < propiedades.size()) {
                    Casilla propiedad = propiedades.get(propiedadIndex);
                    propiedad.hipotecarCasilla(jugador, Tablero.banca);
                    System.out.println("Has hipotecado " + propiedad.getNombre() + ".");
                    break;
                } else {
                    System.out.println("Índice de propiedad no válido.");
                }
            } else if (opcion == 2) {
                bancarrota(jugador, acreedor);
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }
        // Cerrar el scanner

    }

    // Método para declararse en bancarrota
    private static void bancarrota(Jugador jugador, Jugador acreedor) {
        if (acreedor == Tablero.banca) {
            // Transferir toda la fortuna del jugador a la banca
            acreedor.sumarFortuna(jugador.getFortuna());
            jugador.setFortuna(0);
            // Transferir todas las propiedades del jugador a la banca
            for (Casilla propiedad : jugador.getPropiedades()) {
                acreedor.anhadirPropiedad(propiedad);
                propiedad.setDuenho(acreedor);
            }
            System.out.println(jugador.getNombre() + " se ha declarado en bancarrota. Toda su fortuna y propiedades se han transferido a la banca.");
        } else {
            // Transferir toda la fortuna del jugador al acreedor
            acreedor.sumarFortuna(jugador.getFortuna());acreedor.premiosinversionesbote= acreedor.premiosinversionesbote+jugador.getFortuna();
            jugador.setFortuna(0);
            // Transferir todas las propiedades del jugador al acreedor
            for (Casilla propiedad : jugador.getPropiedades()) {
                acreedor.anhadirPropiedad(propiedad);
                propiedad.setDuenho(acreedor);
            }
            System.out.println(jugador.getNombre() + " se ha declarado en bancarrota. Toda su fortuna y propiedades se han transferido a " + acreedor.getNombre() + ".");
        }
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        // Buscar la casilla por nombre
        String nombre1 = nombre + " " + jugadores.get(turno).getAvatar().getId();
        if (!jugadores.get(turno).getAvatar().getLugar().getNombre().equals(nombre1)) {
            System.out.println("No estás en esta casilla, no puedes comprarla");
        } else {
            Casilla casilla = tablero.encontrar_casilla(nombre);
            if (! listarVenta().contains(nombre)) {
                System.out.println("Esa casilla no se puede comprar");
            } else {
                if (casilla != null) {
                    // Llamar al método comprarCasilla
                    Jugador jugadorActual = jugadores.get(turno);
                    casilla.comprarCasilla(jugadorActual, Tablero.banca);
                } else {
                    System.out.println("La casilla " + nombre + " no existe.");
                }
            }
        }
    }
/*
    //Metodo que lee en cada turno si el jugador está en la carcel
     private void estaEnCarcel(){
        Jugador jugadorActual = jugadores.get(turno);
        // algo para tira = true y no puedas moverte
            System.out.println("El jugador " + jugadorActual.getNombre() + " está en la cárcel. Escriba 'salir carcel' para intentar salir.");
            // Impedir que el jugador lance los dados mientras esté en la cárcel
            setTirado(true);


        // Si no está en la cárcel, permitir lanzar los dados

    }

*/
    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
        Jugador jugadorActual = jugadores.get(turno);

        if (!jugadorActual.isEnCarcel()) {
            System.out.println("No estás en la cárcel.");
            return;
        }

        if (jugadorActual.turnosEnCarcel == 3) {if (jugadorActual.getFortuna() >= SUMA_VUELTA / 4) {
            jugadorActual.sumarFortuna(-SUMA_VUELTA / 4);jugadorActual.pagotasasimpuestos=jugadorActual.pagotasasimpuestos+SUMA_VUELTA/4;
            jugadorActual.setEnCarcel(false);
            setTirado(false);jugadorActual.turnosEnCarcel=0;
            System.out.println("Has pagado la multa y has salido de la cárcel.");
        } else {
            System.out.println("No tienes suficiente dinero para pagar la multa.");
        }

        } else if (jugadorActual.turnosEnCarcel<3){
            // Opción de pagar la multa o tirar los dados

                System.out.println("Elige una opción para salir de la cárcel:");
                System.out.println("1. Pagar una multa de " + (SUMA_VUELTA / 4) + ".");
                System.out.println("2. Tirar los dados.");


            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            // Cerrar el scanner



            if (opcion == 1) {
                if (jugadorActual.getFortuna() >= SUMA_VUELTA / 4) {
                    jugadorActual.sumarFortuna(-SUMA_VUELTA / 4);jugadorActual.pagotasasimpuestos=jugadorActual.pagotasasimpuestos+SUMA_VUELTA/4;
                    jugadorActual.setEnCarcel(false);
                    setTirado(false);jugadorActual.turnosEnCarcel=0;
                    System.out.println("Has pagado la multa y has salido de la cárcel.");
                } else {
                    System.out.println("No tienes suficiente dinero para pagar la multa.");
                }
            } else if (opcion == 2) {

                Dado dado1 = new Dado();
                Dado dado2 = new Dado();
                int tirada1 = dado1.hacerTirada();
                int tirada2 = dado2.hacerTirada();

                System.out.println("Has tirado los dados: " + tirada1 + " y " + tirada2);jugadorActual.vecesdados++;

                if (tirada1 == tirada2) {
                    jugadorActual.setEnCarcel(false);
                    setTirado(false);
                    System.out.println("Has sacado dobles y has salido de la cárcel.");
                    jugadorActual.turnosEnCarcel = 0;
                } else {
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                    System.out.println("No has sacado dobles. Sigues en la cárcel.");
                    incrementarTurnosEnCarcel();
                    acabarTurno();

                }

            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    //metodo que incrementa los turnos en carcel
    public void incrementarTurnosEnCarcel() {
        if (jugadores.get(turno).isEnCarcel()) {
            jugadores.get(turno).turnosEnCarcel++;  // Incrementar si está en la cárcel
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private ArrayList<String> listarVenta() {
        ArrayList<String> enVenta = new ArrayList<>();
        for(int i=0;i<=3;i++){
            if (i==0) {
                for(int j=0;j<=10;j++)
                    if(Tablero.getTodasCasillas().get(i).get(j).getDuenho()==Tablero.banca){
                        String[] partes=Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i==1) {
                for(int j=0;j<=8;j++)
                    if(Tablero.getTodasCasillas().get(i).get(j).getDuenho()==Tablero.banca){
                        String[] partes=Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i==2) {
                for(int j=0;j<=10;j++)
                    if(Tablero.getTodasCasillas().get(i).get(j).getDuenho()==Tablero.banca){
                        String[] partes=Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
            if (i==3) {
                for(int j=0;j<=8;j++)
                    if(Tablero.getTodasCasillas().get(i).get(j).getDuenho()==Tablero.banca){
                        String[] partes=Tablero.getTodasCasillas().get(i).get(j).getNombre().split(" ");
                        enVenta.add(partes[0]);
                    }
            }
        }
        System.out.println("Las casillas en venta son:");
        System.out.println(enVenta);
        return enVenta;
    }
    
    // Método que realiza las acciones asociadas al comando 'listar hipotecadas'.
    private ArrayList<String> listarHipoteca() {
        ArrayList<String> hipotecadas = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            // Recorremos cada tipo de casilla
            for (int j = 0; j < Tablero.getTodasCasillas().get(i).size(); j++) {
                Casilla casilla = Tablero.getTodasCasillas().get(i).get(j);

                // Verificamos si la casilla está hipotecada
                if (casilla.estaHipotecada()) {
                    String[] partes = casilla.getNombre().split(" ");
                    hipotecadas.add(partes[0]);
                }
            }
        }

        // Mensaje informativo
        if (hipotecadas.isEmpty()) {
            System.out.println("No hay casillas hipotecadas.");
        } else {
            System.out.println("Las casillas hipotecadas son:");
            System.out.println(hipotecadas);
        }

        return hipotecadas;
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for (Jugador jugador : jugadores) {
            System.out.println("{");
            System.out.println("    nombre: " + jugador.getNombre() + ",");
            System.out.println("    avatar: " + jugador.getAvatar().getId() + ",");
            System.out.println("    fortuna: " + jugador.getFortuna() + ",");

            // Listar propiedades y edificios
            ArrayList<String> propies = new ArrayList<>();
            for (Casilla propiedad : jugador.getPropiedades()) {
                String[] partes = propiedad.getNombre().split(" ");
                propies.add(partes[0]);
            }
            System.out.println("    propiedades: " + propies + ",");

            // Listar edificios
            System.out.println("    edificios: {");
            for (Casilla propiedad : jugador.getPropiedades()) {
                if (propiedad.getTipo().equalsIgnoreCase("Solar")) {
                    System.out.println("        " + propiedad.getNombre() + ": {");
                    System.out.println("            casas: " + propiedad.getCasas().getNumEdificios() + ",");
                    System.out.println("            hoteles: " + propiedad.getHoteles().getNumEdificios() + ",");
                    System.out.println("            piscinas: " + propiedad.getPiscinas().getNumEdificios() + ",");
                    System.out.println("            pistas de deporte: " + propiedad.getPistasDeporte().getNumEdificios());
                    System.out.println("        },");
                }
            }
            System.out.println("    }");
            System.out.println("}");
            System.out.println();
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Jugador jugador : jugadores) {
            Avatar avatar = jugador.getAvatar();
            System.out.println("{");
            System.out.println("    id: " + avatar.getId() + ",");
            System.out.println("    tipo: " + avatar.getTipo() + ",");
            if(avatar.getLugar().getNombre().contains("&")){
                String[] partes=avatar.getLugar().getNombre().split(" ");
                System.out.println("    casilla: " + partes[0] + ",");
            }
            else{
            System.out.println("    casilla: " + avatar.getLugar().getNombre() + ",");}
            System.out.println("    jugador: " + jugador.getNombre());
            System.out.println("},");
            System.out.println();
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        lanzamientos = 0;
        turno++;setTurno(turno);
        if (turno == jugadores.size()) {
            turno = 0;setTurno(turno);
        }
        setTirado(false);
    }

    // Método que indica que jugador tiene el turno.
    private void jugador() {
        System.out.println("{");
        System.out.println("    nombre: " + jugadores.get(turno).getNombre() + ",");
        System.out.println("    avatar: " + jugadores.get(turno).getAvatar().getId());
        System.out.println("}");
        System.out.println();
    }

    // Método que muestra el tablero.
    private void verTablero() {
        System.out.println(tablero);
    }

    private void mover(int casillas) {
        Jugador jugadorActual = jugadores.get(turno);
        moverJugador(jugadorActual, casillas);
        System.out.println(tablero);
        if (jugadorActual.isEnCarcel()) {
            acabarTurno();}
    }
    
    //Metodo que hipoteca una propiedad
    private void hipotecar(String nombre) {
        // Buscar la casilla por nombre
        String nombre1 = nombre + " " + jugadores.get(turno).getAvatar().getId();
        if (!jugadores.get(turno).getAvatar().getLugar().getNombre().equals(nombre1)) {
            System.out.println("No estás en esta casilla, no puedes hipotecarla");
        } else {
            Casilla casilla = tablero.encontrar_casilla(nombre);
            if (listarHipoteca().contains(nombre)) {
                System.out.println("Esa casilla no se puede hipotecar");
            } else {
                if (casilla != null) {
                    // Llamar al método hipotecarCasilla
                    Jugador jugadorAhora = jugadores.get(turno);
                    casilla.hipotecarCasilla(jugadorAhora, Tablero.banca);
                } else {
                    System.out.println("La casilla " + nombre + " no existe.");
                }
            }
        }
    }
   
    //Metodo que deshipoteca una propiedad
    private void deshipotecar(String nombre) {
        // Buscar la casilla por nombre
        Casilla casilla = tablero.encontrar_casilla(nombre);
        Jugador jugadorAhora = jugadores.get(turno);

        // Verificar si la casilla existe y si pertenece al jugador actual
        if (casilla != null && casilla.getDuenho() == jugadorAhora) {

            // Verificar si la casilla está hipotecada
            if (casilla.estaHipotecada()) {
                // Calcular el costo de deshipoteca con un recargo del 10%
                float costoDeshipoteca = casilla.calcularValorHipoteca() * 1.10f;

                // Verificar si el jugador tiene suficiente dinero para deshipotecar
                if (jugadorAhora.getFortuna() >= costoDeshipoteca) {
                    // Realizar el pago y deshipotecar
                    jugadorAhora.sumarFortuna(-costoDeshipoteca);jugadorAhora.pagotasasimpuestos=jugadorAhora.pagotasasimpuestos+costoDeshipoteca;
                    casilla.setHipotecada(false);casilla.casillahacostado=casilla.casillahacostado+costoDeshipoteca;

                    // Informar al jugador
                    System.out.println(jugadorAhora.getNombre() + " paga " + costoDeshipoteca + "€ por deshipotecar " + nombre +
                            ". Ahora puede recibir alquileres y edificar en el grupo " + casilla.getNombre() + ".");
                } else {
                    System.out.println(jugadorAhora.getNombre() + " no tiene suficiente dinero para deshipotecar " + nombre + ".");
                }
            } else {
                System.out.println(jugadorAhora.getNombre() + " no puede deshipotecar " + nombre + ". No está hipotecada.");
            }
        } else {
            System.out.println(jugadorAhora.getNombre() + " no puede deshipotecar " + nombre + ". No es una propiedad que le pertenece.");
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    // Método que realiza las acciones asociadas a las cartas de Suerte.
    public static void accionSuerte(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas) {
        ArrayList<String> cartasSuerte = new ArrayList<>();
        cartasSuerte.add("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Decides hacer un viaje de placer hasta Solar15, sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("Vendes tu billete de avión y cobras 500000€.");
        cartasSuerte.add("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Los acreedores te persiguen. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("¡Has ganado el bote de la lotería! Recibe 1000000€.");

        // Barajar manualmente las cartas
        ArrayList<String> cartasBarajadas = barajarCartas(cartasSuerte);

        // Selección de carta por parte del jugador
        System.out.print("Elige un número de carta entre 1 y " + cartasSuerte.size() + ": ");
        Scanner scanner=new Scanner(System.in);
        int eleccion = scanner.nextInt();

        if (eleccion < 1 || eleccion > cartasSuerte.size()) {
            System.out.println("Número de carta inválido. Debe ser entre 1 y " + cartasSuerte.size() + ".");
            return;
        }

        // Obtener la carta seleccionada
        String cartaSeleccionada = cartasSuerte.get(eleccion - 1);
        System.out.println("Acción: " + cartaSeleccionada);
        // Ejecutar la acción basada en el texto de la carta
        Jugador jugadorActual = jugadores.get(turno);

        switch (eleccion) {
            case 1:
            System.out.println("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                int pppp=jugadorActual.getAvatar().getLugar().getPosicion();
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 5);
                System.out.println(tablero);

                if (pppp>5){
                    System.out.println("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                    jugador.sumarFortuna(1301328.584f);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;}
                jugador.vecesvueltas++;
                break;

            case 2:
                System.out.println("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 26);
                System.out.println(tablero);

                break;

            case 3:
                System.out.println("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.");
                jugador.sumarFortuna(500000);jugador.premiosinversionesbote=jugador.premiosinversionesbote+500000;
                break;

            case 4:
                System.out.println("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                int ppp=jugadorActual.getAvatar().getLugar().getPosicion();
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 6);
                if (ppp>6){
                System.out.println("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugador.sumarFortuna(1301328.584f);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;}
                jugador.vecesvueltas++;
                System.out.println(tablero);
                break;

            case 5:
                System.out.println("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);jugadorActual.vecesencarcel++;
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                System.out.println(tablero);
                break;

            case 6:
                System.out.println("¡Has ganado el bote de la lotería! Recibe 1000000€.");
                jugador.sumarFortuna(1000000);jugador.premiosinversionesbote=jugador.premiosinversionesbote+1000000;
                break;

            default:
                System.out.println("No se reconoce la carta seleccionada.");
                break;
        }

    }

    // Método que realiza las acciones asociadas a las cartas de Comunidad.
    public static void accionComunidad(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas  ) {
        ArrayList<String> cartasComunidad = new ArrayList<>();
        cartasComunidad.add("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
        cartasComunidad.add("Te investigan por fraude de identidad. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasComunidad.add("Colócate en la casilla de Salida y cobra la cantidad habitual.");
        cartasComunidad.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
        cartasComunidad.add("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
        cartasComunidad.add("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");

        // Barajar manualmente las cartas
        ArrayList<String> cartasBarajadas = barajarCartas(cartasComunidad);

        // Selección de carta por parte del jugador
        System.out.print("Elige un número de carta entre 1 y " + cartasComunidad.size() + ": ");
        Scanner scanner=new Scanner(System.in);
        int eleccion = scanner.nextInt();

        if (eleccion < 1 || eleccion > cartasComunidad.size()) {
            System.out.println("Número de carta inválido. Debe ser entre 1 y " + cartasComunidad.size() + ".");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);

        // Ejecutar la acción basada en el texto de la carta
        switch (eleccion) {
            case 1:
                System.out.println("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
                jugador.sumarFortuna(-500000);jugador.pagotasasimpuestos= jugador.pagotasasimpuestos+500000;

                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }

                break;
            case 2:
                System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);jugadorActual.vecesencarcel++;
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                System.out.println(tablero);
                break;
            case 3:
                System.out.println("Colócate en la casilla de Salida y cobra la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 0);
                System.out.println("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugador.sumarFortuna(1301328.584f);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;
                jugador.vecesvueltas++;
                //dddjJJDD
                break;
            case 4:
                System.out.println("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
                jugador.sumarFortuna(2000000);jugador.premiosinversionesbote=jugador.premiosinversionesbote+2000000;
                break;
            case 5:
                System.out.println("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
                jugador.sumarFortuna(-1000000);jugador.pagotasasimpuestos=jugador.pagotasasimpuestos+1000000;
                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }
                break;
            case 6:
                System.out.println("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");

                int cantidad = 200000;
                for (int i = 0; i < jugadores.size(); i++) {  // Itera sobre todos los jugadores
                    if (i != turno) {  // Omite el jugador en turno
                        jugadores.get(i).sumarFortuna(cantidad);jugadores.get(i).premiosinversionesbote=jugadores.get(i).premiosinversionesbote+cantidad;  // Realiza el pago
                    }
                }

                jugador.sumarFortuna(-cantidad * (jugadores.size() - 1));jugador.pagotasasimpuestos=jugador.pagotasasimpuestos+(cantidad * (jugadores.size() - 1));  // Resta el total pagado

                if (jugador.getFortuna() < 0) {
                    sinDinero(jugador, Tablero.banca);
                }

                break;
            default:
                System.out.println("No se reconoce la carta seleccionada.");
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
    }

    // Método para obtener la posición de una casilla en la estructura de casillas
    private static int obtenerPosicionCasilla(String nombreCasilla, ArrayList<ArrayList<Casilla>> casillas) {
        for (int i = 0; i < casillas.size(); i++) {
            for (int j = 0; j < casillas.get(i).size(); j++) {
                if (casillas.get(i).get(j).getNombre().equals(nombreCasilla)) {
                    return i * casillas.get(i).size() + j; // Convertir a índice lineal
                }
            }
        }
        return -1; // Si no se encuentra la casilla
    }

    // Metodo que construye un edificio en una casilla de tipo solar comprada por el jugador.
    // El jugador debe tener todas las casillas del grupo para poder construir o haber caído más dos veces en la casilla.
    // Para hacer la prueba del test, no está implementado.
    private void construir() {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
       Scanner scanner = new Scanner(System.in);
        if (!casillaActual.getTipo().equalsIgnoreCase("Solar")) {
            System.out.println("No puedes construir en esta casilla. Debes estar en una casilla de tipo Solar.");
            return;
        }

        System.out.println("Introduce el tipo de edificio que deseas construir (Casa, Hotel, Piscina, PistaDeporte): ");
        String tipoEdificio = scanner.nextLine();

        Edificio edificio = new Edificio(casillaActual, tipoEdificio);
        edificio.construirEdificio();
    }

    // Metodo que lista los edificios construidos por los jugadores.
    private void listarEdificios() {
        ArrayList<Edificio> edificios = new ArrayList<>();
        HashSet<String> idsUnicos = new HashSet<>();

        for (Jugador jugador : jugadores) {
            for (Casilla propiedad : jugador.getPropiedades()) {
                for (Edificio edificio : propiedad.getEdificios()) {
                    if (edificio != null && idsUnicos.add(edificio.getIds().toString())) {
                        edificios.add(edificio);
                    }
                }
            }
        }

        for (Edificio edificio : edificios) {
            for (String id : edificio.getIds()) {
                System.out.println("{");
                System.out.println("    id: " + id + ",");
                System.out.println("    propietario: " + edificio.getCasilla().getDuenho().getNombre() + ",");
                System.out.println("    casilla: " + edificio.getCasilla().getNombre() + ",");
                System.out.println("    grupo: " + edificio.getCasilla().getGrupo().getColorGrupo() + ",");
                System.out.println("    coste: " + edificio.getCosteEdificio());
                System.out.println("},");
            }
        }
    }

    private void listarEdificiosGrupo(String colorGrupo) {
        Grupo grupo = tablero.getGrupos().get(colorGrupo);
        if (grupo == null) {
            System.out.println("El grupo especificado no existe.");
            return;
        }

        ArrayList<Casilla> propiedades = grupo.getMiembros();
        int maxCasas = 0, maxHoteles = 0, maxPiscinas = 0, maxPistasDeporte = 0;
        int casasConstruidas = 0, hotelesConstruidos = 0, piscinasConstruidas = 0, pistasDeporteConstruidas = 0;

        for (Casilla propiedad : propiedades) {
            Edificio casas = propiedad.getCasas();
            Edificio hoteles = propiedad.getHoteles();
            Edificio piscinas = propiedad.getPiscinas();
            Edificio pistasDeporte = propiedad.getPistasDeporte();

            maxCasas += casas.getMaxEdificios();
            maxHoteles += hoteles.getMaxEdificios();
            maxPiscinas += piscinas.getMaxEdificios();
            maxPistasDeporte += pistasDeporte.getMaxEdificios();

            casasConstruidas += casas.getNumEdificios();
            hotelesConstruidos += hoteles.getNumEdificios();
            piscinasConstruidas += piscinas.getNumEdificios();
            pistasDeporteConstruidas += pistasDeporte.getNumEdificios();

            System.out.println("{");
            System.out.println("    propiedad: " + propiedad.getNombre() + ",");
            System.out.println("    hoteles: " + (hoteles.getIds().isEmpty() ? "-" : hoteles.getIds()) + ",");
            System.out.println("    casas: " + (casas.getIds().isEmpty() ? "-" : casas.getIds()) + ",");
            System.out.println("    piscinas: " + (piscinas.getIds().isEmpty() ? "-" : piscinas.getIds()) + ",");
            System.out.println("    pistasDeDeporte: " + (pistasDeporte.getIds().isEmpty() ? "-" : pistasDeporte.getIds()) + ",");
            System.out.println("    alquiler: " + propiedad.getAlquiler());
            System.out.println("},");
        }

        System.out.println("Todavía se pueden construir:");
        System.out.println("Casas: " + (maxCasas - casasConstruidas));
        System.out.println("Hoteles: " + (maxHoteles - hotelesConstruidos));
        System.out.println("Piscinas: " + (maxPiscinas - piscinasConstruidas));
        System.out.println("Pistas de deporte: " + (maxPistasDeporte - pistasDeporteConstruidas));
    }

    // Método para vender edificios de una casilla
    private void venderEdificios(String tipoEdificio, String nombreCasilla, int cantidad) {
        // Buscar la casilla por nombre
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        Jugador jugadorAhora = jugadores.get(turno);
    
        // Verificar si la casilla existe y si pertenece al jugador actual
        if (casilla != null && casilla.getDuenho() == jugadorAhora) {
            Edificio edificio = null;
            switch (tipoEdificio.toLowerCase()) {
                case "casas":
                    edificio = casilla.getCasas();
                    break;
                case "hoteles":
                    edificio = casilla.getHoteles();
                    break;
                case "piscinas":
                    edificio = casilla.getPiscinas();
                    break;
                case "pistasdeporte":
                    edificio = casilla.getPistasDeporte();
                    break;
                default:
                    System.out.println("Tipo de edificio no válido.");
                    return;
            }
    
            if (edificio != null) {
                int numEdificios = edificio.getNumEdificios();
                if (numEdificios >= cantidad) {
                    // Calcular el precio de venta
                    float precioVenta = edificio.getCosteEdificio() * 0.5f * cantidad;
    
                    // Actualizar la cantidad de edificios y la fortuna del jugador
                    edificio.setNumEdificios(numEdificios - cantidad);
                    jugadorAhora.sumarFortuna(precioVenta);jugadorAhora.premiosinversionesbote=jugadorAhora.premiosinversionesbote+precioVenta;
                    edificio.getCasilla().casillahagenerado=edificio.getCasilla().casillahagenerado+precioVenta;
                    jugadorAhora.valorpropiedades=jugadorAhora.valorpropiedades-precioVenta;
                    // Eliminar los IDs de los edificios vendidos
                    for (int i = 0; i < cantidad; i++) {
                        edificio.getIds().remove(edificio.getIds().size() - 1);
                    }
    
                    // Informar al jugador
                    System.out.println(jugadorAhora.getNombre() + " ha vendido " + cantidad + " " + tipoEdificio + " en " + nombreCasilla + ", recibiendo " + precioVenta + "€. En la propiedad queda " + (numEdificios - cantidad) + " " + tipoEdificio + ".");
                } else {
                    System.out.println("Solamente se puede vender " + numEdificios + " " + tipoEdificio + ", recibiendo " + (edificio.getCosteEdificio() * 0.5f * numEdificios) + "€.");
                    edificio.setNumEdificios(0);
                    jugadorAhora.sumarFortuna(edificio.getCosteEdificio() * 0.5f * numEdificios);
                    jugadorAhora.premiosinversionesbote=jugadorAhora.premiosinversionesbote+(edificio.getCosteEdificio() * 0.5f * numEdificios);
                    edificio.getCasilla().casillahagenerado=edificio.getCasilla().casillahagenerado+(edificio.getCosteEdificio() * 0.5f * numEdificios);
                jugadorAhora.valorpropiedades=jugadorAhora.valorpropiedades-(edificio.getCosteEdificio() * 0.5f * numEdificios);
                }
            } else {
                System.out.println("No se pueden vender " + tipoEdificio + " en " + nombreCasilla + ".");
            }
        } else {
            System.out.println("No se pueden vender " + tipoEdificio + " en " + nombreCasilla + ". Esta propiedad no pertenece a " + jugadorAhora.getNombre() + ".");
        }
    }

    // Método que muestra la lista de comandos disponibles.
    private void mostrarAyuda() {
        System.out.println("Comandos disponibles:");
        System.out.println("{");
        System.out.println("    jugador");
        System.out.println("    describir [jugador/avatar/casilla] [nombre/ID]");
        System.out.println("    lanzar dados");
        System.out.println("    comprar [nombre_casilla]");
        System.out.println("    salir carcel");
        System.out.println("    listar [jugadores/avatares/enventa/hipotecados/edificios/edificios grupo [color_grupo]]");
        System.out.println("    acabar turno");
        System.out.println("    exit");
        System.out.println("    ver tablero");
        System.out.println("    mover [numero_casillas]");
        System.out.println("    hipotecar [nombre_casilla]");
        System.out.println("    deshipotecar [nombre_casilla]");
        System.out.println("    estadisticas [jugador/nada]");
        System.out.println("    construir");
        System.out.println("    vender [casas/hoteles/piscinas/pistasdeporte] [nombre_casilla] [cantidad]");
        System.out.println("    bancarrota");
        System.out.println("    cambiar movimiento");
        System.out.println("}");
        System.out.println();
    }

    // Método que verifica si todos los jugadores han completado al menos 4 vueltas.
    public boolean todosHanCompletadoCuatroVueltas() {
        for (Jugador jugador : jugadores) {
            // Verificamos si las vueltas del jugador NO son divisibles entre 4
            if (jugador.getVueltas() % 4 != 0 || jugador.getVueltas() == 0) {
                return false;  // Si algún jugador no ha dado vueltas divisibles entre 4, retornamos false
            }
        }
        return true;  // Si todos los jugadores han dado un número de vueltas divisible entre 4, retornamos true
    }

    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea mayor a 4
    public void movercocheavanzadomayor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();Scanner scanner=new Scanner(System.in);
        int nuevaPosicion = posicionActual + valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion > 39 && posicionActual <= 39) {
            jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
            System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
            jugador.sumarFortuna(SUMA_VUELTA);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;  // Añadir el valor de la vuelta a la fortuna del jugador
            jugador.vecesvueltas++;
            // Verificar si todos los jugadores han completado al menos 4 vueltas
            if (todosHanCompletadoCuatroVueltas()) {
                tablero.incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
            }
        }
        // Ajustar la nueva posición en el tablero si excede el número de casillas
        if(nuevaPosicion<0){nuevaPosicion=nuevaPosicion+40;}
        nuevaPosicion = nuevaPosicion % 40;

        // Actualizar la posición del avatar
        avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

        Casilla casillaActual = avatar.getLugar();


        // Evaluar la casilla en la que cayó
        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
        if (!jugador.getCocheExtra() && !jugador.casollegaralcuartoturnosextracocheavanzado){
        System.out.println("Coche modo avanzado: sacaste más de 4, así que tienes máximo 3 turnos");
        System.out.println("Atención!!!: solo se puede realizar una compra de propiedades, servicio o transporte a lo largo de estos 4 turnos, pero se puede edificar libremente");
        System.out.println("Atención!!!: si se llega al último turno extra (hay 3 como máximo), en ese turno sí cuentan los dados dobles, pero si en ese turno sale doble, si la siguiente tirada también sale doble NO se vuelve a tirar");
        jugador.setCocheExtra(true);}

    }
    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea menor a 4
    public void movercocheavanzadomenor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();
        int nuevaPosicion = posicionActual - valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion<0 && posicionActual>0) {
            jugador.disminuirVueltas();  // Aumentar el contador de vueltas del jugador
            System.out.println("¡Has retrocedido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
            if(jugador.getFortuna()>=SUMA_VUELTA){
            jugador.sumarFortuna(-SUMA_VUELTA); }
            else{sinDinero(jugador,banca);}// Añadir el valor de la vuelta a la fortuna del jugador
            jugador.vecesvueltas=jugador.vecesvueltas-1;jugador.pasarporcasillasalida=jugador.pasarporcasillasalida-SUMA_VUELTA;
            // Verificar si todos los jugadores han completado al menos 4 vueltas

        }
        // Ajustar la nueva posición en el tablero si excede el número de casillas
        if(nuevaPosicion<0){nuevaPosicion=nuevaPosicion+40;}
        nuevaPosicion = nuevaPosicion % 40;

        // Actualizar la posición del avatar
        avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

        Casilla casillaActual = avatar.getLugar();

        // Evaluar la casilla en la que cayó
        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
        if(!jugador.getCocheExtra()){
        System.out.println("Coche modo avanzado: sacaste menos de 4, así que pierdes los siguientes dos turnos");
        jugador.setCocheProhibido(true);jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;}
        else if(jugador.getCocheExtra()){
            System.out.println("Sacaste menos de 4, perdiste los turnos extra");
            jugador.setCasosacarmenosdecuatroenunturnoextracocheavanzado(true);
            jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;
        }

    }
/*Método para obtener la estadística de jugador con más lanzamientos de dado*/
    public String maxvecesdado(){
        ArrayList<Integer> ppp=new ArrayList<>();
        for (int i=0;i<jugadores.size();i++){
            ppp.add(jugadores.get(i).vecesdados);
        }int z=0;
        for (int i=0;i<=ppp.size()-2;i++){
            if(ppp.get(z)>=ppp.get(i+1)){}
            else{z=i+1;}
        }
        return jugadores.get(z).getNombre();
    }

    /*Método para obtener la estadística de jugador con más vueltas*/
    public String maxvueltas(){
        ArrayList<Integer> ppp=new ArrayList<>();
        for (int i=0;i<jugadores.size();i++){
            ppp.add(jugadores.get(i).vecesvueltas);
        }int z=0;
        for (int i=0;i<=ppp.size()-2;i++){
            if(ppp.get(z)>=ppp.get(i+1)){}
            else{z=i+1;}
        }
        return jugadores.get(z).getNombre();
    }

    /*Método para obtener la estadística de casilla más frecuentada*/
    public String maxcasilla(){
        ArrayList<ArrayList<Casilla>> casillas=Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp=new ArrayList<>();

        for (int posicion=0;posicion<40;posicion++){

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }int z=0;
        for (int i=0;i<=ppp.size()-2;i++){
            if(ppp.get(z).vecescasilla>=ppp.get(i+1).vecescasilla){}
            else{z=i+1;}
        }
        return ppp.get(z).getNombre();
    }

    public String casillarentable(){
        ArrayList<ArrayList<Casilla>> casillas=Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp=new ArrayList<>();

        for (int posicion=0;posicion<40;posicion++){

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }int z=0;
        for (int i=0;i<=ppp.size()-2;i++){
            if((ppp.get(z).casillahagenerado-ppp.get(z).casillahacostado)>=(ppp.get(i+1).casillahagenerado-ppp.get(i+1).casillahacostado)){}
            else{z=i+1;}
        }
        return ppp.get(z).getNombre();
    }
    public String gruporentable(){
        HashMap<String, Grupo> grupos=new HashMap<>();
        grupos=tablero.getGrupos();
        ArrayList<Float> rentabilidadgrupos =new ArrayList<>();
        for (Map.Entry<String, Grupo> entry : grupos.entrySet()){
            rentabilidadgrupos.add(entry.getValue().rentabilidadgrupo());
        }

        int z=0;
        for(int i=0;i<=rentabilidadgrupos.size()-2;i++){
            if(rentabilidadgrupos.get(z)>=rentabilidadgrupos.get(i+1)){}
            else{z=i+1;}
        }
        if(z==0){return "negro";}
        else if(z==1){return "cian";}
        else if(z==2){return "magenta";}
        else if(z==3){return "amarillo";}
        else if(z==4){return "rojo";}
        else if(z==5){return "marron";}
        else if(z==6){return "verde";}
        else{return "azul";}

    }

    public String jugadorcabeza() {
        ArrayList<Float> patrimonio =new ArrayList<>();
        for(int i=0;i<=jugadores.size()-1;i++){
            patrimonio.add(jugadores.get(i).getFortuna()+jugadores.get(i).getValorpropiedades());
        }
        int z=0;
        for (int i=0;i<=patrimonio.size()-2;i++){
            if(patrimonio.get(z)>=patrimonio.get(i+1)){}
            else{z=i+1;}
        }return jugadores.get(z).getNombre();
    }
}