package monlau.dao;

import java.util.List;
import monlau.model.Producto;

/**
 * Interfaz que define las operaciones CRUD para Producto
 */
public interface ProductoDAO {
    /**
     * Inserta un nuevo producto en la base de datos
     * @param producto el producto a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insert(Producto producto);
    
    /**
     * Actualiza un producto existente en la base de datos
     * @param producto el producto con los nuevos datos
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(Producto producto);
    
    /**
     * Elimina un producto de la base de datos
     * @param producto el producto a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(Producto producto);
    
    /**
     * Busca un producto por su ID
     * @param id el ID del producto a buscar
     * @return el producto encontrado o null si no existe
     */
    public Producto read(Integer id);
    
    /**
     * Obtiene todos los productos de la base de datos
     * @return lista de todos los productos
     */
    public List<Producto> getAll();
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     * @param nombre parte del nombre a buscar
     * @return lista de productos que coinciden con la búsqueda
     */
    public List<Producto> findByName(String nombre);
    
    /**
     * Busca productos por rango de precio
     * @param min precio mínimo
     * @param max precio máximo
     * @return lista de productos dentro del rango de precio
     */
    public List<Producto> findByPriceRange(double min, double max);
}
