package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.db.DbProducto;
import com.example.menu.entidades.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarProductoActivity extends AppCompatActivity {

    EditText txtNombre;
    Button btnGuarda, fabEditar, fabEliminar;
    boolean correcto = false;
    Producto producto;
    int idProducto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        txtNombre = findViewById(R.id.txtNombre);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

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

        DbProducto dbProducto = new DbProducto(EditarProductoActivity.this);
        producto = dbProducto.verProducto(idProducto);

        if(producto != null){
            txtNombre.setText(producto.getNombre());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("")){
                    correcto = dbProducto.editarProducto(idProducto, txtNombre.getText().toString());

                    if(correcto){
                        Toast.makeText(EditarProductoActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    }else {
                        Toast.makeText(EditarProductoActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditarProductoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerProductoActivity.class);
        intent.putExtra("IDPRODUCTO", idProducto);
        startActivity(intent);
    }
}
