package com.example.menu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.menu.entidades.Producto;

import java.util.ArrayList;

public class DbProducto extends DbHelper{

    Context context;

    public DbProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertProducto(String nombre){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);

            id = db.insert(TABLE_PRODUCTO, null, values);
        }catch(Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Producto> mostrarProducto(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Producto> listaProducto = new ArrayList<>();
        Producto producto;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO, null);

        if(cursorProducto.moveToFirst()){
            do{
                producto = new Producto();
                producto.setId(cursorProducto.getInt(0));
                producto.setNombre(cursorProducto.getString(1));
                listaProducto.add(producto);
            } while (cursorProducto.moveToNext());
        }

        cursorProducto.close();

        return listaProducto;
    }

    public Producto verProducto(int idProducto){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Producto producto = null;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " WHERE idProducto = " + idProducto + " LIMIT 1", null);

        if(cursorProducto.moveToFirst()){
            producto = new Producto();
            producto.setId(cursorProducto.getInt(0));
            producto.setNombre(cursorProducto.getString(1));
        }

        cursorProducto.close();

        return producto;
    }

    public boolean editarProducto(int idProducto, String nombre){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET nombre = '"+ nombre +"' WHERE idProducto = '"+ idProducto +"'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
            correcto = false;
        }finally{
            db.close();
        }

        return correcto;
    }

    public boolean eliminarProducto(int idProducto){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCTO + " WHERE idProducto = '"+ idProducto +"'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
            correcto = false;
        }finally{
            db.close();
        }

        return correcto;
    }
}
