package monlau;

import java.util.List;
import java.util.Scanner;
import monlau.dao.ProductoDAO;
import monlau.dao.ProductoDAOImpl;
import monlau.model.Producto;

public class ProductoManager {

    private static final ProductoDAO productoDAO = new ProductoDAOImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextLine().trim().toUpperCase();

            switch (opcion) {
                case "1" -> insertarProducto();
                case "2" -> buscarProductoPorId();
                case "3" -> actualizarProducto();
                case "4" -> eliminarProducto();
                case "5" -> listarTodosProductos();
                case "6" -> buscarProductoPorNombre();
                case "7" -> buscarProductoPorRangoPrecio();
                case "Z" -> System.out.println("Hasta pronto!");
                default -> System.out.println("Opcion no valida");
            }

            if (!opcion.equals("Z")) {
                System.out.println("\nPulse ENTER para continuar...");
                scanner.nextLine();
            }

        } while (!opcion.equals("Z"));

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== GESTOR DE PRODUCTOS =====");
        System.out.println("1. Insertar nuevo producto");
        System.out.println("2. Buscar producto por ID");
        System.out.println("3. Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Listar todos los productos");
        System.out.println("6. Buscar productos por nombre");
        System.out.println("7. Buscar productos por rango de precio");
        System.out.println("Z. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void insertarProducto() {
        System.out.println("\n--- INSERTAR NUEVO PRODUCTO ---");

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        Producto producto = new Producto(id, nombre, precio);

        if (productoDAO.insert(producto)) {
            System.out.println("Producto insertado correctamente.");
        } else {
            System.out.println("Error al insertar el producto.");
        }
    }

    private static void buscarProductoPorId() {
        System.out.println("\n--- BUSCAR PRODUCTO POR ID ---");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Producto producto = productoDAO.read(id);
        if (producto != null) {
            System.out.println("Producto encontrado: " + producto);
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void actualizarProducto() {
        System.out.println("\n--- ACTUALIZAR PRODUCTO ---");
        System.out.print("ID del producto: ");
        int id = Integer.parseInt(scanner.nextLine());

        Producto producto = productoDAO.read(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto actual: " + producto);

        System.out.print("Nuevo nombre (ENTER para mantener): ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isBlank()) {
            producto.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo precio (ENTER para mantener): ");
        String nuevoPrecioStr = scanner.nextLine();
        if (!nuevoPrecioStr.isBlank()) {
            producto.setPrecio(Double.parseDouble(nuevoPrecioStr));
        }

        if (productoDAO.update(producto)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar el producto.");
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Producto producto = productoDAO.read(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto a eliminar: " + producto);
        System.out.print("Seguro que desea eliminarlo? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            if (productoDAO.delete(producto)) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el producto.");
            }
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

    private static void listarTodosProductos() {
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        List<Producto> productos = productoDAO.getAll();

        if (productos.isEmpty()) {
            System.out.println("No hay productos.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarProductoPorNombre() {
        System.out.println("\n--- BUSCAR PRODUCTOS POR NOMBRE ---");
        System.out.print("Nombre o parte del nombre: ");
        String nombre = scanner.nextLine();

        List<Producto> productos = productoDAO.findByName(nombre);
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarProductoPorRangoPrecio() {
        System.out.println("\n--- BUSCAR PRODUCTOS POR PRECIO ---");

        System.out.print("Precio minimo: ");
        double min = Double.parseDouble(scanner.nextLine());

        System.out.print("Precio maximo: ");
        double max = Double.parseDouble(scanner.nextLine());

        List<Producto> productos = productoDAO.findByPriceRange(min, max);
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en ese rango.");
        } else {
            productos.forEach(System.out::println);
        }
    }

}
