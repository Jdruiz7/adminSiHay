package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.menu.adaptadores.ListaProductoAdapter;
import com.example.menu.db.DbHelper;
import com.example.menu.db.DbProducto;
import com.example.menu.entidades.Producto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaProducto;
    ArrayList<Producto> listaArrayProducto;
    Button btnCrearProducto;
    ListaProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrearProducto = findViewById(R.id.btnCrearProducto);
        listaProducto = findViewById(R.id.listaProducto);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaProducto.setLayoutManager(new LinearLayoutManager(this));

        DbProducto dbProducto = new DbProducto(MainActivity.this);
        listaArrayProducto = new ArrayList<Producto>();

        adapter = new ListaProductoAdapter(dbProducto.mostrarProducto());
        listaProducto.setAdapter(adapter);

        /*
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(db != null){
                    Toast.makeText(MainActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        btnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoProducto();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }


    private void nuevoProducto(){
        Intent intent = new Intent(this, InsertProductoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}