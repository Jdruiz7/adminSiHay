package com.example.menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.menu.db.DbProducto;
import com.example.menu.entidades.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerProductoActivity extends AppCompatActivity {

    EditText txtNombre;
    Button btnGuarda, fabEditar, fabEliminar;

    Producto producto;
    int idProducto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        txtNombre = findViewById(R.id.txtNombre);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                idProducto = Integer.parseInt(null);
            }else {
                idProducto = extras.getInt("IDPRODUCTO");
            }
        }else{
            idProducto = (int) savedInstanceState.getSerializable("IDPRODUCTO");
        }

        DbProducto dbProducto = new DbProducto(VerProductoActivity.this);
        producto = dbProducto.verProducto(idProducto);

        if(producto != null){
            txtNombre.setText(producto.getNombre());
            btnGuarda.setVisibility(View.INVISIBLE);
            txtNombre.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerProductoActivity.this, EditarProductoActivity.class);
                intent.putExtra("IDPRODUCTO", idProducto);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerProductoActivity.this);
                builder.setMessage("¿Desea Eliminar este producto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbProducto.eliminarProducto(idProducto)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}