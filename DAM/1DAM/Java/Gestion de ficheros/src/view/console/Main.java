import model.Funciones;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option;
        
        do {
            showMenu();
            option = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (option) {
                case 1:
                    crearCarpeta(sc);
                    break;
                case 2:
                    crearArchivo(sc);
                    break;
                case 3:
                    verArchivosEnCarpeta(sc);
                    break;
                case 4:
                    verContenidoArchivo(sc);
                    break;
                case 5:
                    sobrescribirArchivo(sc);
                    break;
                case 6:
                    borrarArchivo(sc);
                    break;
                case 7:
                    contarCaracteres(sc);
                    break;
                case 8:
                    contarPalabras(sc);
                    break;
                case 9:
                    reemplazarPalabras(sc);
                    break;
                case 0:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (option != 0);

        sc.close();
    }

    // Muestra el menú
    private static void showMenu() {
        System.out.println("1. Crear carpeta");
        System.out.println("2. Crear archivo");
        System.out.println("3. Ver archivos en carpeta");
        System.out.println("4. Ver contenido de un archivo");
        System.out.println("5. Sobreescribir archivo");
        System.out.println("6. Borrar archivo");
        System.out.println("7. Contar caracteres");
        System.out.println("8. Contar palabras");
        System.out.println("9. Reemplazar palabras");
        System.out.println("0. Salir");
    }

    // Métodos de cada opción
    private static void crearCarpeta(Scanner sc) {
        System.out.println("Introduce el nombre de la carpeta");
        String path = sc.nextLine();
        Funciones.createFolder(path);
    }

    private static void crearArchivo(Scanner sc) {
        System.out.println("Introduce el nombre de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        System.out.println("Introduce el contenido");
        String content = sc.nextLine();
        try {
            Funciones.createFile(path, fileName, content);
        } catch (IOException e) {
            System.out.println("Error al crear el archivo");
        }
    }

    private static void verArchivosEnCarpeta(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        String[] files = Funciones.showListFiles(path);
        if (files != null) {
            for (String file : files) {
                System.out.println(file);
            }
        } else {
            System.out.println("No es una carpeta válida");
        }
    }

    private static void verContenidoArchivo(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        try {
            String fileContent = Funciones.showFile(path, fileName);
            System.out.println(fileContent);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }
    }

    private static void sobrescribirArchivo(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        System.out.println("Introduce el nuevo contenido");
        String content = sc.nextLine();
        try {
            if (Funciones.overWriteFile(path, fileName, content)) {
                System.out.println("Archivo sobrescrito");
            } else {
                System.out.println("El archivo no existe");
            }
        } catch (IOException e) {
            System.out.println("Error al sobrescribir el archivo");
        }
    }

    private static void borrarArchivo(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        Funciones.deleteFile(path, fileName);
    }

    private static void contarCaracteres(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        try {
            int chars = Funciones.countChars(path, fileName);
            System.out.println("Caracteres: " + chars);
        } catch (IOException e) {
            System.out.println("Error al contar caracteres");
        }
    }

    private static void contarPalabras(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        try {
            int words = Funciones.countWords(path, fileName);
            System.out.println("Palabras: " + words);
        } catch (IOException e) {
            System.out.println("Error al contar palabras");
        }
    }

    private static void reemplazarPalabras(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta");
        String path = sc.nextLine();
        System.out.println("Introduce el nombre del archivo");
        String fileName = sc.nextLine();
        System.out.println("Palabra a reemplazar");
        String oldWord = sc.nextLine();
        System.out.println("Nueva palabra");
        String newWord = sc.nextLine();
        try {
            String result = Funciones.swapWords(path, fileName, oldWord, newWord);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Error al reemplazar palabras");
        }
    }
}
