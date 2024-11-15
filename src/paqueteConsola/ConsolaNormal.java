package paqueteConsola;

import java.util.Scanner;

public class ConsolaNormal implements Consola {
    private Scanner scanner;

    public ConsolaNormal() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String leer() {
        return scanner.nextLine();
    }

    @Override
    public int leerInt() {
        String input = scanner.nextLine();
        int numero = Integer.parseInt(input);
        return numero;
    }
}