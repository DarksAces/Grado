package com.example.supermarket

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object ProductContract {
    object ProductEntry : BaseColumns {
        const val TABLE_NAME = "product"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_IMAGE = "image"
        const val COLUMN_NAME_PRICE = "price"
    }
}

private const val SQL_CREATE_ENTRIES = "CREATE TABLE ${ProductContract.ProductEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${ProductContract.ProductEntry.COLUMN_NAME_NAME} TEXT," +
        "${ProductContract.ProductEntry.COLUMN_NAME_IMAGE} INTEGER," +
        "${ProductContract.ProductEntry.COLUMN_NAME_PRICE} REAL)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ProductContract.ProductEntry.TABLE_NAME}"

class ProductDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Supermarket.db"
    }
}