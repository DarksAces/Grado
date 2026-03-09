package practicas_examen;

/**
 * Ejercicio 6: Hilos Daemon vs Hilos de Usuario.
 * 
 * ¿Qué pasará cuando el hilo main termine después de imprimir "Main: He
 * terminado"?
 * 1. ¿El hilo secundario seguirá imprimiendo números para siempre?
 * 2. ¿Se cerrará el programa inmediatamente?
 * 3. ¿Cambiaría algo si comentamos la línea setDaemon(true)?
 */
public class ExamenHilos1_Daemon {
    public static void main(String[] args) {
        Thread hiloInfinito = new Thread(() -> {
            int cuenta = 1;
            while (true) {
                try {
                    System.out.println("Hilo secundario: Trabajando... " + cuenta++);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        hiloInfinito.setDaemon(true); // <--- OJO A ESTO
        hiloInfinito.start();

        try {
            System.out.println("Main: Voy a dormir 1.5 segundos...");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
        }

        System.out.println("Main: He terminado mi trabajo. Adios.");
    }
}
