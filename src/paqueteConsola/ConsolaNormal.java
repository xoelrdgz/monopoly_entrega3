package paqueteConsola;

import java.util.Scanner;

public class ConsolaNormal implements Consola {

    @Override
    public void imprimir (String mensaje){
        System.out.println(mensaje);
    }

    @Override
    public String leer (String mensaje){
        Scanner scanner = new Scanner(System.in);

        mensaje = scanner.nextLine();

        scanner.close();

        return mensaje;
    }

}
