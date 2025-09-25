package monlau.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import monlau.model.Producto;

/**
 * Implementación de ProductoDAO que utiliza JDBC para conectarse a MySQL
 */
public class ProductoDAOImpl implements ProductoDAO {

    // Constantes de conexión
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/practica_jdbc?useSSL=false&serverTimezone=UTC";
    private static final String DB_USR = "root";
    private static final String DB_PWD = "";
    
    // Sentencias SQL
    private static final String SQL_INSERT = "INSERT INTO producto (id, nombre, precio) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE producto SET nombre = ?, precio = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?";
    private static final String SQL_READ = "SELECT * FROM producto WHERE id = ?";
    private static final String SQL_READ_ALL = "SELECT * FROM producto ORDER BY id";
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM producto WHERE nombre LIKE ? ORDER BY nombre";
    private static final String SQL_FIND_BY_PRICE = "SELECT * FROM producto WHERE precio BETWEEN ? AND ? ORDER BY precio";

    /**
     * Carga el driver JDBC
     */
    private void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR: No se pudo cargar el driver MySQL JDBC");
            ex.printStackTrace();
            throw new RuntimeException("Error al cargar el driver JDBC", ex);
        }
    }

    /**
     * Obtiene una conexión a la base de datos
     * @return conexión a la base de datos
     * @throws SQLException si hay un error al conectar
     */
    private Connection getConnection() throws SQLException {
        registerDriver();
        return DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
    }

    @Override
    public boolean insert(Producto producto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        
        try {
            conn = getConnection();
            
            // Iniciar transacción
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(SQL_INSERT);
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            
            int filasAfectadas = pstmt.executeUpdate();
            resultado = (filasAfectadas > 0);
            
            // Confirmar transacción
            conn.commit();
            
            if (resultado) {
                System.out.println("Producto insertado correctamente: " + producto);
            }
            
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    // Revertir transacción en caso de error
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("Error al insertar producto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        
        return resultado;
    }

    @Override
    public boolean update(Producto producto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        
        try {
            conn = getConnection();
            
            // Iniciar transacción
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            resultado = (filasAfectadas > 0);
            
            // Confirmar transacción
            conn.commit();
            
            if (resultado) {
                System.out.println("Producto actualizado correctamente: " + producto);
            } else {
                System.out.println("No se encontró producto con ID: " + producto.getId());
            }
            
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    // Revertir transacción en caso de error
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("Error al actualizar producto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        
        return resultado;
    }

    @Override
    public boolean delete(Producto producto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;
        
        try {
            conn = getConnection();
            
            // Iniciar transacción
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setInt(1, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            resultado = (filasAfectadas > 0);
            
            // Confirmar transacción
            conn.commit();
            
            if (resultado) {
                System.out.println("Producto eliminado correctamente con ID: " + producto.getId());
            } else {
                System.out.println("No se encontró producto con ID: " + producto.getId());
            }
            
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    // Revertir transacción en caso de error
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("Error al eliminar producto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
        
        return resultado;
    }

    @Override
    public Producto read(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Producto producto = null;
        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(SQL_READ);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                producto = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")
                );
            }
            
        } catch (SQLException ex) {
            System.err.println("Error al leer producto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return producto;
    }
    
    @Override
    public List<Producto> getAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(SQL_READ_ALL);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")
                ));
            }
            
        } catch (SQLException ex) {
            System.err.println("Error al listar productos: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> findByName(String nombre) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(SQL_FIND_BY_NAME);
            pstmt.setString(1, "%" + nombre + "%");
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")
                ));
            }
            
        } catch (SQLException ex) {
            System.err.println("Error al buscar productos por nombre: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> findByPriceRange(double min, double max) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(SQL_FIND_BY_PRICE);
            pstmt.setDouble(1, min);
            pstmt.setDouble(2, max);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")
                ));
            }
            
        } catch (SQLException ex) {
            System.err.println("Error al buscar productos por rango de precio: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return productos;
    }
    
    /**
     * Cierra los recursos JDBC
     * @param conn conexión
     * @param stmt statement
     * @param rs resultset
     */
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.setAutoCommit(true);
                }
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar recursos JDBC: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
