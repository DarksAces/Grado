
import java.util.Scanner;

public class Practica10 {

    // Definir constantes
    private static final int TABLERO = 5;
    private static final char VACIO = ' ';
    private static final char BARCO = 'B';
    private static final char GOLPE = '*';
    private static final char FALLO = 'x';
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        char[][] tableroUsuario = new char[TABLERO][TABLERO];
        char[][] tableroBarcos = new char[TABLERO][TABLERO];
        inicializarTableros(tableroUsuario, tableroBarcos);
        asignarBarco(tableroBarcos);
        jugar(tableroUsuario, tableroBarcos);
    }

    static void inicializarTableros(char[][]... tableros) {
        for (char[][] tablero : tableros) {
            for (int i = 0; i < TABLERO; i++) {
                for (int j = 0; j < TABLERO; j++) {
                    tablero[i][j] = VACIO;
                }
            }
        }
    }

    static void asignarBarco(char[][] tablero) {
        int fila, columna;
        boolean posicionValida;

        do {
            fila = (int) (Math.random() * (TABLERO - 2)); // TABLERO-2 asegura que no se salga del límite vertical
            columna = (int) (Math.random() * (TABLERO - 2)); // TABLERO-2 asegura que no se salga del límite horizontal
            posicionValida = (fila != 0 && columna != 0); // Asegura que no esté en fila 1 (index 0) o columna A (index 0)
        } while (!posicionValida);

        // Asignar barco de 2x2
        tablero[fila][columna] = BARCO;
        tablero[fila][columna + 1] = BARCO;
        tablero[fila + 1][columna] = BARCO;
        tablero[fila + 1][columna + 1] = BARCO;
    }

    static void imprimirTablero(char[][] tablero) {
        System.out.println("  A B C D E");
        for (int i = 0; i < TABLERO; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < TABLERO; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void jugar(char[][] usuario, char[][] barcos) {

        while (true) {
            imprimirTablero(usuario);
            System.out.print("Introduce tu jugada (ej. 4B): ");
            String jugada = sc.nextLine().toUpperCase();
            int fila = jugada.charAt(0) - '1';
            int col = jugada.charAt(1) - 'A';

            if (barcos[fila][col] == BARCO) {
                System.out.println("Impacto!");
                usuario[fila][col] = GOLPE;
                barcos[fila][col] = VACIO;
            } else {
                System.out.println("Agua!");
                usuario[fila][col] = FALLO;
            }

            if (todosHundidos(barcos)) {
                System.out.println("Has hundido todos los barcos enemigos!");
                break;
            }
        }
        sc.close();
    }

    static boolean todosHundidos(char[][] barcos) {
        for (char[] fila : barcos) {
            for (char c : fila) {
                if (c == BARCO) {
                    return false;
                }
            }
        }
        return true;
        
    }
}
