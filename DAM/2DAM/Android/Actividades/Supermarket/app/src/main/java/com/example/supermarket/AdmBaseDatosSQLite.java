package com.example.supermarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class AdmBaseDatosSQLite extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "SupermercadoDB";
    private static final int VERSION_BD = 1;
    private static final String TABLA_FRUTAS = "frutas";

    public AdmBaseDatosSQLite(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creamos la tabla con columnas: id, nombre, recurso imagen, precio, cantidad
        db.execSQL("CREATE TABLE " + TABLA_FRUTAS + " (id INTEGER PRIMARY KEY, nombre TEXT, imagen INTEGER, precio REAL, cantidad INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_FRUTAS);
        onCreate(db);
    }

    // --- MÉTODOS DE NEGOCIO (REQUISITOS DEL TRABAJO) ---

    /**
     * REQUISITO: Sincronización.
     * Borra los datos antiguos y carga los nuevos del servidor (simulado).
     * Esto cumple con: "se tendrán que reemplazar cada vez".
     */
    public void sincronizarFrutas(ArrayList<Fruta> listaDelServidor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Usamos transacciones para asegurar integridad y velocidad
        try {
            // 1. Limpiar caché (borrar todo)
            db.execSQL("DELETE FROM " + TABLA_FRUTAS);

            // 2. Insertar datos nuevos
            for (Fruta f : listaDelServidor) {
                ContentValues values = new ContentValues();
                values.put("id", f.id);
                values.put("nombre", f.nombre);
                values.put("imagen", f.imagenResId);
                values.put("precio", f.precio);
                values.put("cantidad", 0); // Al sincronizar, el carrito empieza vacío

                db.insert(TABLA_FRUTAS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Devuelve la lista de frutas guardada en SQLite.
     * Útil para cuando no hay internet o para refrescar la vista.
     */
    public ArrayList<Fruta> obtenerFrutas() {
        ArrayList<Fruta> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nombre, imagen, precio, cantidad FROM " + TABLA_FRUTAS, null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new Fruta(
                        cursor.getInt(0),  // id
                        cursor.getString(1), // nombre
                        cursor.getInt(2),  // imagen
                        cursor.getDouble(3), // precio
                        cursor.getInt(4)   // cantidad
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    /**
     * Método auxiliar para reiniciar cantidades a 0 (Botón Reset del menú)
     */
    public void resetearCantidades() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLA_FRUTAS + " SET cantidad = 0");
        db.close();
    }
}